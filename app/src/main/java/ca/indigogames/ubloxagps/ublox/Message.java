package ca.indigogames.ubloxagps.ublox;

import ca.indigogames.ubloxagps.io.BinaryStream;

public interface Message {
    int getClassId();
    int getMethodId();

    void read(BinaryStream payloadStream) throws Exception;
    void write(BinaryStream payloadStream) throws Exception;
}
