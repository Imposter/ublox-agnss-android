package ca.indigogames.ubloxagps.ublox;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

import ca.indigogames.ubloxagps.io.BinaryStream;
import ca.indigogames.ubloxagps.io.IStream;
import ca.indigogames.ubloxagps.utility.Random;

public class UbloxGPS {
    public interface ReceiveCallback {
        void onUbxMessage(Message message);
        void onNmeaMessage(Message message);
    }

    public interface SendCallback<TResponse extends Message> {
        void onResponse(TResponse message);
    }

    private class UbxMessage {
        protected int classId;
        protected int methodId;
    }

    private static final UUID TASK_UUID = Random.randomUUID();
    private static final short UBX_PROTO_HEADER_B1 = 0xB5;
    private static final short UBX_PROTO_HEADER_B2 = 0x62;

    private IStream mStream;
    private BinaryStream mBinaryStream;
    private Queue<UbxMessage> mMessages;

    public UbloxGPS(IStream stream) {
        mStream = stream;
        mMessages = new PriorityQueue<>();
        mBinaryStream = new BinaryStream(mStream);
    }

    public void handle() throws Exception {
        // Read first byte
        short b = mBinaryStream.readUInt8();
        if (b == UBX_PROTO_HEADER_B1) {
            // Read second byte
            b = mBinaryStream.readUInt8();
            if (b == UBX_PROTO_HEADER_B2) {
                // Read message info
                short classId = mBinaryStream.readUInt8();
                short methodId = mBinaryStream.readUInt8();

                // Read payload length
                short length = mBinaryStream.readInt16();

                // Read payload
                byte[] payload = null;
                if (length > 0)
                    payload = mBinaryStream.readBlob(length);

                // Read checksum
                short checksumA = mBinaryStream.readUInt8();
                short checksumB = mBinaryStream.readUInt8();

                // TODO: Verify checksum
            } else {
                throw new Exception("Unexpected character, expected " + UBX_PROTO_HEADER_B2);
            }
        } else {
            // TODO: NMEA, read entire line
        }

        // TODO: Don't return ACK messages to callback

        // TODO: ...
    }

    public <TResponse extends Message>
    void sendMessage(Message message, SendCallback<TResponse> callback) {
        // TODO: Send message, wait for response
    }
}
