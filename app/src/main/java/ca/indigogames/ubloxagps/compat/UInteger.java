package ca.indigogames.ubloxagps.compat;

import android.support.annotation.NonNull;

public final class UInteger extends Number implements Comparable<UInteger> {
    public static final int BYTES = 4;
    public static final long MAX_VALUE = 0xFFFFFFFFL;
    public static final long MIN_VALUE = 0;
    public static final int SIZE = 32;
    public static final Class<UInteger> TYPE = UInteger.class;

    public static final UInteger MIN = valueOf(MIN_VALUE);
    public static final UInteger MAX = valueOf(MAX_VALUE);

    private long mValue;

    public UInteger(int value) {
        mValue = value & MAX_VALUE;
    }

    public UInteger(long unsignedInteger) throws NumberFormatException {
        mValue = unsignedInteger;
        checkRange();
    }

    public UInteger(String s) throws NumberFormatException {
        mValue = Long.parseLong(s);
        checkRange();
    }

    public UInteger(String s, int radix) throws NumberFormatException {
        mValue = Long.parseLong(s, radix);
        checkRange();
    }

    public static String toString(UInteger s) {
        return Long.toString(s.mValue);
    }

    public static UInteger parseUShort(String s, int radix) throws NumberFormatException {
        return new UInteger(s, radix);
    }

    public static UInteger parseUShort(String s) throws NumberFormatException {
        return new UInteger(s);
    }

    public static UInteger valueOf(String s, int radix) throws NumberFormatException {
        return parseUShort(s, radix);
    }

    public static UInteger valueOf(String s) throws NumberFormatException {
        return new UInteger(s);
    }

    public static UInteger valueOf(int s) throws NumberFormatException {
        return new UInteger(s);
    }

    public static UInteger valueOf(long i) throws NumberFormatException {
        return new UInteger(i);
    }

    public static UInteger decode(String nm) throws NumberFormatException {
        return new UInteger(nm);
    }

    private void checkRange() throws NumberFormatException {
        if (mValue < MIN_VALUE || mValue > MAX_VALUE)
            throw new NumberFormatException("Value out of range: " + mValue);
    }

    public byte byteValue() {
        return (byte)mValue;
    }

    public short shortValue() {
        return (short)mValue;
    }

    public int intValue() {
        return (int)mValue;
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
        return Long.toString(mValue);
    }

    public int hashCode() {
        return Long.valueOf(mValue).hashCode();
    }

    public static int hashCode(UInteger value) {
        return Long.valueOf(value.mValue).hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof UInteger && ((UInteger)obj).mValue == mValue;

    }

    public int compareTo(@NonNull UInteger anotherUShort) {
        return Long.valueOf(mValue).compareTo(anotherUShort.mValue);
    }

    public static int compare(UInteger x, UInteger y) {
        return Long.compare(x.mValue, y.mValue);
    }

    public static int toSignedInteger(UInteger x) {
        return (int)x.mValue;
    }
}
