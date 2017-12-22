package ca.indigogames.ubloxagps.ublox;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import ca.indigogames.ubloxagps.compat.UByte;
import ca.indigogames.ubloxagps.compat.UShort;
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
    private static final UByte UBX_PROTO_HEADER_B1 = UByte.valueOf((byte)0xB5);
    private static final UByte UBX_PROTO_HEADER_B2 = UByte.valueOf((byte)0x62);

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
        UByte b = mBinaryStream.readUInt8();
        if (b.equals(UBX_PROTO_HEADER_B1)) {
            // Read second byte
            b = mBinaryStream.readUInt8();
            if (b.equals(UBX_PROTO_HEADER_B2)) {
                // Read message info
                UByte classId = mBinaryStream.readUInt8();
                UByte methodId = mBinaryStream.readUInt8();

                // TODO: Remove compat classes and just implement them into BinaryStream

                // Read payload length
                short length = mBinaryStream.readInt16();

                // Read payload
                byte[] payload = null;
                if (length > 0)
                    payload = mBinaryStream.readBlob(length);

                // Read checksum
                //var checksumA = await stream.ReadUInt8Async();
                //var checksumB = await stream.ReadUInt8Async();
            } else {
                throw new Exception("Unexpected character, expected " + UBX_PROTO_HEADER_B2);
            }
        }

        // TODO: Don't return ACK messages to callback

        // TODO: ...
    }

    public <TResponse extends Message>
    void sendMessage(Message message, SendCallback<TResponse> callback) {
        // TODO: Send message, wait for response
    }
}
