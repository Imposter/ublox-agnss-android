package ca.indigogames.ubloxagps.ublox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

import ca.indigogames.ubloxagps.compat.UByte;
import ca.indigogames.ubloxagps.io.BinaryStream;
import ca.indigogames.ubloxagps.io.ITimedStream;
import ca.indigogames.ubloxagps.io.MemoryStream;
import ca.indigogames.ubloxagps.ublox.messages.NmeaHandler;
import ca.indigogames.ubloxagps.ublox.messages.UbxHandler;
import ca.indigogames.ubloxagps.ublox.messages.nmea.GpsLatLong;
import ca.indigogames.ubloxagps.ublox.messages.ubx.AidInitialize;
import ca.indigogames.ubloxagps.utility.Random;

public class UbloxGps {
    public interface ReceiveCallback {
        void onUbxMessage(UByte classId, UByte messageId, Object data);

        void onNmeaMessage(String prefixId, String messageId, Object data);
    }

    // TODO: Implement UBX send/recv callback
    public interface SendCallback<TResponse> {
        void onResponse(TResponse message);
    }

    // Stores messages which were sent, in order
    private static class UbxMessageLog {
        protected UByte classId;
        protected UByte methodId;
    }

    private static class UbxChecksum {
        protected UByte a;
        protected UByte b;

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof UbxChecksum))
                throw new IllegalArgumentException("Invalid argument type");

            UbxChecksum other = (UbxChecksum) obj;
            return a.equals(other.a) && b.equals(other.b);
        }
    }

    private static final UUID TASK_UUID = Random.randomUUID();

    private static final int DEFAULT_TIMEOUT = 2500; // ms
    private static final UByte UBX_PROTO_HEADER_B1 = UByte.valueOf((byte) 0xB5);
    private static final UByte UBX_PROTO_HEADER_B2 = UByte.valueOf((byte) 0x62);

    private static final UByte NMEA_PROTO_HEADER = UByte.valueOf((byte) '$');

    private ITimedStream mStream;
    private BinaryStream mBinaryStream;
    private ReceiveCallback mReceiveCallback;
    private Queue<UbxMessageLog> mMessages;

    private List<UbxHandler> mUbxHandlers;
    private List<NmeaHandler> mNmeaHandlers;

    public UbloxGps(ITimedStream stream) {
        mStream = stream;
        mMessages = new PriorityQueue<>();
        mBinaryStream = new BinaryStream(mStream);

        // Set stream timeout
        mStream.setTimeout(DEFAULT_TIMEOUT);

        // Create UBX handlers
        mUbxHandlers = new ArrayList<>();
        mUbxHandlers.add(new AidInitialize.Handler());

        // Create NMEA handlers
        mNmeaHandlers = new ArrayList<>();
        mNmeaHandlers.add(new GpsLatLong.Handler());
    }

    public void setReceiveCallback(ReceiveCallback receiveCallback) {
        mReceiveCallback = receiveCallback;
    }

    public void handle() throws Exception {
        // Read first byte
        UByte b = mBinaryStream.readUInt8();
        if (b.equals(UBX_PROTO_HEADER_B1)) {
            // Read second byte
            b = mBinaryStream.readUInt8();
            if (b.equals(UBX_PROTO_HEADER_B2)) {
                // Read message info
                UByte classId = mBinaryStream.readUInt8();
                UByte methodId = mBinaryStream.readUInt8();

                // Read payload length
                Short length = mBinaryStream.readInt16();

                // Read payload
                byte[] payload = null;
                if (length > 0)
                    payload = mBinaryStream.readBlob(length);

                // Read checksum
                UbxChecksum checksum = new UbxChecksum();
                checksum.a = mBinaryStream.readUInt8();
                checksum.b = mBinaryStream.readUInt8();

                // Verify checksum
                if (payload != null) {
                    UbxChecksum verifyChecksum = ubxCalculateChecksum(classId, methodId, payload);
                    if (!checksum.equals(verifyChecksum))
                        throw new RuntimeException("UBX checksum failed");
                }

                // Create binary stream for payload
                MemoryStream payloadMemoryStream = new MemoryStream(payload);
                BinaryStream payloadStream = new BinaryStream(payloadMemoryStream);

                // Handle packet
                // TODO: Check if this message was in the queue of sent messages, so we can return response
                // TODO: or just as a receive message
                if (mReceiveCallback != null) {
                    // Find handler
                    UbxHandler handler = null;
                    for (UbxHandler h : mUbxHandlers) {
                        if (h.getClassId().equals(classId) && h.getMethodId().equals(methodId)) {
                            handler = h;
                            break;
                        }
                    }

                    if (handler == null) {
                        throw new UnsupportedOperationException("Class/method not supported");
                    }

                    // Handle
                    Object messageData = handler.deserialize(payloadStream);

                    mReceiveCallback.onUbxMessage(classId, methodId, payloadStream);
                }
            } else {
                throw new Exception("Unexpected character, expected " + UBX_PROTO_HEADER_B2);
            }
        } else if (b.equals(NMEA_PROTO_HEADER)) {
            // Read entire line
            StringBuilder builder = new StringBuilder();
            do {
                builder.append((char) b.byteValue());
                b = mBinaryStream.readUInt8();
            } while (b.byteValue() != (byte) '\n');

            String line = builder.toString().trim();

            // Get message prefix
            String prefix = line.substring(1, 3);
            String message = line.substring(3, 6);

            // Get checksum
            String checksumString = line.substring(line.indexOf('*') + 1);

            // Calculate checksum
            String verifyChecksum = nmeaCalculateChecksum(line);
            if (!checksumString.equals(verifyChecksum))
                throw new RuntimeException("NMEA checksum failed");

            // Remove checksum from line
            line = line.substring(0, line.indexOf('*'));

            // Get argument fields
            String[] lineSplit = line.split(",");
            List<String> data = new ArrayList<>();
            data.addAll(Arrays.asList(lineSplit).subList(1, lineSplit.length));

            // Only handle if callback is present
            if (mReceiveCallback != null) {
                // Find handler
                NmeaHandler handler = null;
                for (NmeaHandler h : mNmeaHandlers) {
                    if (h.getPrefixId().equals(prefix) && h.getMessageId().equals(message)) {
                        handler = h;
                        break;
                    }
                }

                if (handler == null) {
                    throw new UnsupportedOperationException("Prefix/message not supported");
                }

                // Handle
                Object messageData = handler.deserialize(data);

                // Trigger callback
                mReceiveCallback.onNmeaMessage(prefix, message, messageData);
            }
        }

        // TODO: Don't return ACK messages to callback

        // TODO: ...
    }

    // NOTE: This is only for UBX?
    //public <TResponse extends UbxMessage>
    //void sendUbxMessage(UbxMessage message, SendCallback<TResponse> callback) {
    // TODO: Send message, wait for response
    //}

    private static UbxChecksum ubxCalculateChecksum(UByte classId, UByte methodId, byte[] buffer) {
        // Calculate for class and method IDs
        UbxChecksum checksum = new UbxChecksum();
        checksum.a = classId.add(methodId);
        checksum.b = classId.add(checksum.a);

        // Update checksum with payload length
        byte lengthHigh = (byte) (buffer.length >> 8);
        byte lengthLow = (byte) (buffer.length & 0xFF);
        checksum.a = checksum.a.add(lengthHigh);
        checksum.b = checksum.b.add(checksum.a);
        checksum.a = checksum.a.add(lengthLow);
        checksum.b = checksum.b.add(checksum.a);

        // Update checksum with payload
        for (byte b : buffer) {
            checksum.a = checksum.a.add(b);
            checksum.b = checksum.b.add(checksum.a);
        }

        return checksum;
    }

    private static String nmeaCalculateChecksum(String line) {
        int crc = 0;
        int start = 0;
        int end = line.length();

        if (line.startsWith("$")) {
            start = 1;
            end = line.lastIndexOf('*');
        }

        for (int i = start; i < end; i++) {
            crc ^= (byte) line.charAt(i);
        }

        return String.format("%02X", crc);
    }
}
