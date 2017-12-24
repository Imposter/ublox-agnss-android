package ca.indigogames.ubloxagps.io;

import java.io.IOException;

public interface IStream {
    int read(byte[] buffer, int offset, int length) throws IOException;
    void write(byte[] buffer, int offset, int length) throws IOException;
}
