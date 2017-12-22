package ca.indigogames.ubloxagps.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class MemoryStream implements IStream {
    private byte[] mInputBuffer;
    private ByteArrayInputStream mInputStream;
    private ByteArrayOutputStream mOutputStream;

    public MemoryStream() {
        mOutputStream = new ByteArrayOutputStream();
    }

    public MemoryStream(byte[] buffer) {
        mInputBuffer = buffer;
        mInputStream = new ByteArrayInputStream(buffer);
    }

    @Override
    public int read(byte[] buffer, int offset, int length) {
        return mInputStream.read(buffer, offset, length);
    }

    @Override
    public void write(byte[] buffer, int offset, int length) {
        mOutputStream.write(buffer, offset, length);
    }

    public byte[] toArray() {
        if (mOutputStream != null)
            return mOutputStream.toByteArray();

        return mInputBuffer;
    }
}
