package ca.indigogames.ubloxagps.compat;

import android.support.annotation.NonNull;

public final class UInteger extends Number implements Comparable<UInteger> {
    public static final int BYTES = 4;
    public static final long MIN_VALUE = Unsigned.UNSIGNED_INT_MIN;
    public static final long MAX_VALUE = Unsigned.UNSIGNED_INT_MAX;
    public static final int SIZE = 32;
    public static final Class<UInteger> TYPE = UInteger.class;

    public static final UInteger MIN = valueOf(MIN_VALUE);
    public static final UInteger MAX = valueOf(MAX_VALUE);

    private long mValue;

    public UInteger(int value) {
        mValue = Unsigned.toUnsigned(value);
    }

    public UInteger(long unsignedValue) throws NumberFormatException {
        mValue = unsignedValue;
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public UInteger(String s) throws NumberFormatException {
        mValue = Long.parseLong(s);
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public UInteger(String s, int radix) throws NumberFormatException {
        mValue = Long.parseLong(s, radix);
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public static UInteger valueOf(String s, int radix) throws NumberFormatException {
        return new UInteger(s, radix);
    }

    public static UInteger valueOf(String s) throws NumberFormatException {
        return new UInteger(s);
    }

    public static UInteger valueOf(int signedValue) throws NumberFormatException {
        return new UInteger(signedValue);
    }

    public static UInteger valueOf(long unsignedValue) throws NumberFormatException {
        return new UInteger(unsignedValue);
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

    public boolean equals(Object obj) {
        return obj instanceof UInteger && ((UInteger)obj).mValue == mValue;
    }

    public int compareTo(@NonNull UInteger other) {
        return Long.valueOf(mValue).compareTo(other.mValue);
    }

    public UInteger add(UInteger value) {
        return new UInteger(Unsigned.add(mValue, value.mValue));
    }

    public UInteger add(long value) {
        return new UInteger(Unsigned.add(mValue, value));
    }

    public UInteger subtract(UInteger value) {
        return new UInteger(Unsigned.subtract(mValue, value.mValue));
    }

    public UInteger subtract(long value) {
        return new UInteger(Unsigned.subtract(mValue, value));
    }

    public UInteger multiply(UInteger value) {
        return new UInteger(Unsigned.multiply(mValue, value.mValue));
    }

    public UInteger multiply(long value) {
        return new UInteger(Unsigned.multiply(mValue, value));
    }

    public UInteger divide(UInteger value) {
        return new UInteger(Unsigned.divide(mValue, value.mValue));
    }

    public UInteger divide(long value) {
        return new UInteger(Unsigned.divide(mValue, value));
    }

    public UInteger mod(UInteger value) {
        return new UInteger(Unsigned.mod(mValue, value.mValue));
    }

    public UInteger mod(long value) {
        return new UInteger(Unsigned.mod(mValue, value));
    }

    public UInteger and(UInteger value) {
        return new UInteger(Unsigned.and(mValue, value.mValue));
    }

    public UInteger and(long value) {
        return new UInteger(Unsigned.and(mValue, value));
    }

    public UInteger or(UInteger value) {
        return new UInteger(Unsigned.or(mValue, value.mValue));
    }

    public UInteger or(long value) {
        return new UInteger(Unsigned.or(mValue, value));
    }

    public UInteger xor(UInteger value) {
        return new UInteger(Unsigned.xor(mValue, value.mValue));
    }

    public UInteger xor(long value) {
        return new UInteger(Unsigned.xor(mValue, value));
    }

    public UInteger shiftRight(UInteger value) {
        return new UInteger(Unsigned.shiftRight(mValue, value.mValue));
    }

    public UInteger shiftRight(long value) {
        return new UInteger(Unsigned.shiftRight(mValue, value));
    }

    public UInteger shiftLeft(UInteger value) {
        return new UInteger(Unsigned.shiftLeft(mValue, value.mValue));
    }

    public UInteger shiftLeft(long value) {
        return new UInteger(Unsigned.shiftLeft(mValue, value));
    }
}