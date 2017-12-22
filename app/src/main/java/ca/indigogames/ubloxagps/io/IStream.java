package ca.indigogames.ubloxagps.io;

public interface IStream {
    int read(byte[] buffer, int offset, int length);
    void write(byte[] buffer, int offset, int length);
}
