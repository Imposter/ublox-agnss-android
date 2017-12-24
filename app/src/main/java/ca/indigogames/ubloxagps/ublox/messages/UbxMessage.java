package ca.indigogames.ubloxagps.ublox.messages;

import ca.indigogames.ubloxagps.io.BinaryStream;

public interface UbxMessage {
    int getClassId();
    int getMethodId();

    void deserialize(BinaryStream payloadStream) throws Exception;
    void serialize(BinaryStream payloadStream) throws Exception;
}
