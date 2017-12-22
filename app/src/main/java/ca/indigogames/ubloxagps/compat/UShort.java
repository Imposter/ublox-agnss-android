package ca.indigogames.ubloxagps.compat;

import android.support.annotation.NonNull;

public final class UShort extends Number implements Comparable<UShort> {
    public static final int BYTES = 2;
    public static final int MAX_VALUE = 0xFFFF;
    public static final int MIN_VALUE = 0;
    public static final int SIZE = 16;
    public static final Class<UShort> TYPE = UShort.class;

    public static final UShort MIN = valueOf(MIN_VALUE);
    public static final UShort MAX = valueOf(MAX_VALUE);

    private int mValue;

    public UShort(short value) {
        mValue = value & MAX_VALUE;
    }

    public UShort(int unsignedShort) throws NumberFormatException {
        mValue = unsignedShort;
        checkRange();
    }

    public UShort(String s) throws NumberFormatException {
        mValue = Integer.parseInt(s);
        checkRange();
    }

    public UShort(String s, int radix) throws NumberFormatException {
        mValue = Integer.parseInt(s, radix);
        checkRange();
    }

    public static String toString(UShort s) {
        return Integer.toString(s.mValue);
    }

    public static UShort parseUShort(String s, int radix) throws NumberFormatException {
        return new UShort(s, radix);
    }

    public static UShort parseUShort(String s) throws NumberFormatException {
        return new UShort(s);
    }

    public static UShort valueOf(String s, int radix) throws NumberFormatException {
        return parseUShort(s, radix);
    }

    public static UShort valueOf(String s) throws NumberFormatException {
        return new UShort(s);
    }

    public static UShort valueOf(short s) throws NumberFormatException {
        return new UShort(s);
    }

    public static UShort valueOf(int i) throws NumberFormatException {
        return new UShort(i);
    }

    public static UShort decode(String nm) throws NumberFormatException {
        return new UShort(nm);
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
        return Integer.toString(mValue);
    }

    public int hashCode() {
        return Integer.valueOf(mValue).hashCode();
    }

    public static int hashCode(UShort value) {
        return Integer.valueOf(value.mValue).hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof UShort && ((UShort)obj).mValue == mValue;

    }

    public int compareTo(@NonNull UShort anotherUShort) {
        return Integer.valueOf(mValue).compareTo(anotherUShort.mValue);
    }

    public static int compare(UShort x, UShort y) {
        return Integer.compare(x.mValue, y.mValue);
    }

    public static short toSignedShort(UShort x) {
        return (short)x.mValue;
    }
}
