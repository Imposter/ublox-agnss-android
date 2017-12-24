package ca.indigogames.ubloxagps.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TimedStream implements ITimedStream {
    public static int NO_TIMEOUT = -1;
    public static int DEFAULT_TIMEOUT = 100; // ms

    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private int mTimeout;

    public TimedStream(InputStream inputStream, OutputStream outputStream, int timeout) {
        mInputStream = inputStream;
        mOutputStream = outputStream;
        mTimeout = timeout;
    }

    public TimedStream(InputStream inputStream, OutputStream outputStream) {
        mInputStream = inputStream;
        mOutputStream = outputStream;
        mTimeout = NO_TIMEOUT;
    }

    public int getTimeout() {
        return mTimeout;
    }

    public void setTimeout(int timeout) {
        mTimeout = timeout;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        long startTime = System.currentTimeMillis();

        int readBytes = 0;
        while (true)
        {
            if (readBytes == length)
                break;

            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime > mTimeout)
                break;

            readBytes += mInputStream.read(buffer, offset + readBytes, length - readBytes);
        }

        return readBytes;
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {
        mOutputStream.write(buffer, offset, length);
    }
}
