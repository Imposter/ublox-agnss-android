package ca.indigogames.ubloxagps.io;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeoutException;

import ca.indigogames.ubloxagps.compat.UByte;
import ca.indigogames.ubloxagps.compat.UInteger;
import ca.indigogames.ubloxagps.compat.ULong;
import ca.indigogames.ubloxagps.compat.UShort;

public class BinaryStream {
    private IStream mStream;
    private int mPeekByte;
    private boolean mBigEndian;

    private int readBytes(byte[] buffer, int length) {
        if (mPeekByte != -1) {
            buffer[0] = (byte)mPeekByte;
            mPeekByte = -1;
            return mStream.read(buffer, 1, length - 1) + 1;
        }

        return mStream.read(buffer, 0, length);
    }

    public int peek() {
        if (mPeekByte != -1)
            return mPeekByte;

        byte[] b = new byte[1];
        if (mStream.read(b, 0, b.length) > 0) {
            mPeekByte = b[0];
            return mPeekByte;
        }

        return -1;
    }

    public int read() {
        byte[] b = new byte[1];
        return readBytes(b, b.length) < 0 ? -1 : b[0];
    }

    public void write(byte obj) {
        byte[] b = { obj };
        mStream.write(b, 0, b.length);
    }

    public boolean read(byte[] buffer, int length) {
        return mStream.read(buffer, 0, length) == length;
    }

    public void write(byte[] buffer, int length) {
        mStream.write(buffer, 0, length);
    }

    public void write(String str, String encoding) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes(encoding);
        mStream.write(bytes, 0, bytes.length);
    }

    public void write(String str) throws UnsupportedEncodingException {
        write(str, "UTF-8");
    }

    public BinaryStream(IStream stream, boolean bigEndian) {
        mStream = stream;
        mBigEndian = bigEndian;
        mPeekByte = -1;
    }

    public BinaryStream(IStream stream) {
        mStream = stream;
        mBigEndian = false;
        mPeekByte = -1;
    }

    public IStream getStream() {
        return mStream;
    }

    public boolean isBigEndian() {
        return mBigEndian;
    }

    public void setBigEndian(boolean bigEndian) {
        mBigEndian = bigEndian;
    }

    public boolean readBoolean() throws TimeoutException {
        int b = read();
        if (b < 0)
            throw new TimeoutException("Unable to read data");

        return b == 1;
    }

    public char readChar() throws TimeoutException {
        int b = read();
        if (b < 0)
            throw new TimeoutException("Unable to read data");

        return (char)b;
    }

    public Byte readInt8() throws TimeoutException {
        int b = read();
        if (b < 0)
            throw new TimeoutException("Unable to read data");

        return (byte)b;
    }

    public UByte readUInt8() throws TimeoutException {
        byte[] buffer = new byte[1];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        return UByte.valueOf(buffer[0]);
    }

    public Short readInt16() throws TimeoutException {
        byte[] buffer = new byte[2];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        return ByteBuffer.wrap(buffer).order(mBigEndian
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public UShort readUInt16() throws TimeoutException {
        byte[] buffer = new byte[2];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        short obj = ByteBuffer.wrap(buffer).order(mBigEndian
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN).getShort();
        return UShort.valueOf(obj);
    }

    public Integer readInt32() throws TimeoutException {
        byte[] buffer = new byte[4];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        return ByteBuffer.wrap(buffer).order(mBigEndian
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public UInteger readUInt32() throws TimeoutException {
        byte[] buffer = new byte[4];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        int obj = ByteBuffer.wrap(buffer).order(mBigEndian
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN).getInt();
        return UInteger.valueOf((int)obj);
    }

    public Long readInt64() throws TimeoutException {
        byte[] buffer = new byte[8];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        return ByteBuffer.wrap(buffer).order(mBigEndian
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN).getLong();
    }

    public ULong readUInt64() throws TimeoutException {
        byte[] buffer = new byte[8];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        long obj = ByteBuffer.wrap(buffer).order(mBigEndian
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN).getLong();
        return ULong.valueOf(obj);
    }

    public Float readFloat() throws TimeoutException {
        byte[] buffer = new byte[4];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        return ByteBuffer.wrap(buffer).order(mBigEndian
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public Double readDouble() throws TimeoutException {
        byte[] buffer = new byte[8];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        return ByteBuffer.wrap(buffer).order(mBigEndian
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN).getDouble();
    }

    public String readString(int length, String encoding)
            throws UnsupportedEncodingException, TimeoutException {
        // Calculate real length with the specified encoding
        byte[] tempBuffer = "A".getBytes(encoding);
        length *= tempBuffer.length;

        byte[] buffer = new byte[length];
        if (!read(buffer, buffer.length))
            throw new TimeoutException("Unable to read data");

        return new String(buffer, encoding);

    }

    public String readString(int length) throws UnsupportedEncodingException, TimeoutException {
        return readString(length, "UTF-8");
    }

    public byte[] readBlob(int length) throws TimeoutException {
        byte[] buffer = new byte[length];
        if (!read(buffer, length))
            throw new TimeoutException("Unable to read data");

        return buffer;
    }

    public void writeBoolean(boolean obj) {
        write((byte)(obj ? 1 : 0));
    }

    public void writeChar(char obj) {
        write((byte)obj);
    }

    public void writeInt8(Byte obj) {
        write(obj);
    }

    public void writeUInt8(UByte obj) {
        write(obj.byteValue());
    }

    public void writeInt16(Short obj) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(obj);
        if (!mBigEndian)
            buffer.flip();
        write(buffer.array(), 2);
    }

    public void writeUInt16(UShort obj) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(obj.shortValue());
        if (!mBigEndian)
            buffer.flip();
        write(buffer.array(), 2);
    }

    public void writeInt32(Integer obj) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(obj);
        if (!mBigEndian)
            buffer.flip();
        write(buffer.array(), 4);
    }

    public void writeUInt32(UInteger obj) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(obj.intValue());
        if (!mBigEndian)
            buffer.flip();
        write(buffer.array(), 4);
    }

    public void writeInt64(Long obj) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(obj);
        if (!mBigEndian)
            buffer.flip();
        write(buffer.array(), 8);
    }

    public void writeUInt64(UInteger obj) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(obj.longValue());
        if (!mBigEndian)
            buffer.flip();
        write(buffer.array(), 8);
    }

    public void writeFloat(Float obj) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(obj);
        if (!mBigEndian)
            buffer.flip();
        write(buffer.array(), 4);
    }

    public void writeDouble(Double obj) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putDouble(obj);
        if (!mBigEndian)
            buffer.flip();
        write(buffer.array(), 8);
    }

    public void writeString(String obj, String encoding)
            throws UnsupportedEncodingException {
        byte[] buffer = obj.getBytes(encoding);
        write(buffer, buffer.length);
    }

    public void writeBlob(byte[] obj) {
        write(obj, obj.length);
    }
}
