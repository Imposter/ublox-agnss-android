package ca.indigogames.ubloxagps.compat;

import android.support.annotation.NonNull;

public final class UByte extends Number implements Comparable<UByte> {
    public static final int BYTES = 1;
    public static final short MAX_VALUE = 0xFF;
    public static final short MIN_VALUE = 0;
    public static final int SIZE = 8;
    public static final Class<UByte> TYPE = UByte.class;

    public static final UByte MIN = valueOf(MIN_VALUE);
    public static final UByte MAX = valueOf(MAX_VALUE);

    private short mValue;

    public UByte(byte value) {
        mValue = (short)(value & MAX_VALUE);
    }

    public UByte(short unsignedByte) throws NumberFormatException {
        mValue = unsignedByte;
        checkRange();
    }

    public UByte(String s) throws NumberFormatException {
        mValue = Short.parseShort(s);
        checkRange();
    }

    public UByte(String s, int radix) throws NumberFormatException {
        mValue = Short.parseShort(s, radix);
        checkRange();
    }

    public static String toString(UByte s) {
        return Short.toString(s.mValue);
    }

    public static UByte parseUByte(String s, int radix) throws NumberFormatException {
        return new UByte(s, radix);
    }

    public static UByte parseUByte(String s) throws NumberFormatException {
        return new UByte(s);
    }

    public static UByte valueOf(String s, int radix) throws NumberFormatException {
        return parseUByte(s, radix);
    }

    public static UByte valueOf(String s) throws NumberFormatException {
        return new UByte(s);
    }

    public static UByte valueOf(byte s) throws NumberFormatException {
        return new UByte(s);
    }

    public static UByte valueOf(short i) throws NumberFormatException {
        return new UByte(i);
    }

    public static UByte decode(String nm) throws NumberFormatException {
        return new UByte(nm);
    }

    private void checkRange() throws NumberFormatException {
        if (mValue < MIN_VALUE || mValue > MAX_VALUE)
            throw new NumberFormatException("Value out of range: " + mValue);
    }

    public byte byteValue() {
        return (byte)mValue;
    }

    public short shortValue() {
        return mValue;
    }

    public int intValue() {
        return mValue;
    }

    public long longValue() {
        return mValue;
    }

    public float floatValue() {
        return mValue;
    }

    public double doubleValue() {
        return mValue;
    }

    public String toString() {
        return Short.toString(mValue);
    }

    public int hashCode() {
        return Short.valueOf(mValue).hashCode();
    }

    public static int hashCode(UByte value) {
        return Short.valueOf(value.mValue).hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof UByte && ((UByte)obj).mValue == mValue;

    }

    public int compareTo(@NonNull UByte anotherUByte) {
        return Short.valueOf(mValue).compareTo(anotherUByte.mValue);
    }

    public static int compare(UByte x, UByte y) {
        return Short.compare(x.mValue, y.mValue);
    }

    public static byte toSignedByte(UByte x) {
        return (byte)x.mValue;
    }
}