package ca.indigogames.ubloxagps.ublox.messages;

import ca.indigogames.ubloxagps.compat.UByte;
import ca.indigogames.ubloxagps.io.BinaryStream;

public interface UbxHandler<TArg> {
    UByte getClassId();
    UByte getMethodId();

    TArg deserialize(BinaryStream payloadStream) throws Exception;
    void serialize(BinaryStream payloadStream, TArg arg) throws Exception;
}