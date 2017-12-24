package ca.indigogames.ubloxagps.io;

public interface ITimedStream extends IStream {
    void setTimeout(int timeout);
    int getTimeout();
}