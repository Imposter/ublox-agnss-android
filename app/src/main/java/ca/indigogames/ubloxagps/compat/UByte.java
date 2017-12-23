package ca.indigogames.ubloxagps.compat;

import android.support.annotation.NonNull;

public final class UByte extends Number implements Comparable<UByte> {
    public static final int BYTES = 1;
    public static final short MIN_VALUE = Unsigned.UNSIGNED_BYTE_MIN;
    public static final short MAX_VALUE = Unsigned.UNSIGNED_BYTE_MAX;
    public static final int SIZE = 16;
    public static final Class<UByte> TYPE = UByte.class;

    public static final UByte MIN = valueOf(MIN_VALUE);
    public static final UByte MAX = valueOf(MAX_VALUE);

    private short mValue;

    public UByte(byte value) {
        mValue = Unsigned.toUnsigned(value);
    }

    public UByte(short unsignedInteger) throws NumberFormatException {
        mValue = unsignedInteger;
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public UByte(String s) throws NumberFormatException {
        mValue = Short.parseShort(s);
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public UByte(String s, int radix) throws NumberFormatException {
        mValue = Short.parseShort(s, radix);
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public static UByte valueOf(String s, int radix) throws NumberFormatException {
        return new UByte(s, radix);
    }

    public static UByte valueOf(String s) throws NumberFormatException {
        return new UByte(s);
    }

    public static UByte valueOf(byte signedValue) throws NumberFormatException {
        return new UByte(signedValue);
    }

    public static UByte valueOf(short unsignedValue) throws NumberFormatException {
        return new UByte(unsignedValue);
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
        return Short.toString(mValue);
    }

    public boolean equals(Object obj) {
        return obj instanceof UByte && ((UByte)obj).mValue == mValue;
    }

    public int compareTo(@NonNull UByte other) {
        return Short.valueOf(mValue).compareTo(other.mValue);
    }

    public UByte add(UByte value) {
        return new UByte(Unsigned.add(mValue, value.mValue));
    }

    public UByte add(short value) {
        return new UByte(Unsigned.add(mValue, value));
    }

    public UByte subtract(UByte value) {
        return new UByte(Unsigned.subtract(mValue, value.mValue));
    }

    public UByte subtract(short value) {
        return new UByte(Unsigned.subtract(mValue, value));
    }

    public UByte multiply(UByte value) {
        return new UByte(Unsigned.multiply(mValue, value.mValue));
    }

    public UByte multiply(short value) {
        return new UByte(Unsigned.multiply(mValue, value));
    }

    public UByte divide(UByte value) {
        return new UByte(Unsigned.divide(mValue, value.mValue));
    }

    public UByte divide(short value) {
        return new UByte(Unsigned.divide(mValue, value));
    }

    public UByte mod(UByte value) {
        return new UByte(Unsigned.mod(mValue, value.mValue));
    }

    public UByte mod(short value) {
        return new UByte(Unsigned.mod(mValue, value));
    }

    public UByte and(UByte value) {
        return new UByte(Unsigned.and(mValue, value.mValue));
    }

    public UByte and(short value) {
        return new UByte(Unsigned.and(mValue, value));
    }

    public UByte or(UByte value) {
        return new UByte(Unsigned.or(mValue, value.mValue));
    }

    public UByte or(short value) {
        return new UByte(Unsigned.or(mValue, value));
    }

    public UByte xor(UByte value) {
        return new UByte(Unsigned.xor(mValue, value.mValue));
    }

    public UByte xor(short value) {
        return new UByte(Unsigned.xor(mValue, value));
    }

    public UByte shiftRight(UByte value) {
        return new UByte(Unsigned.shiftRight(mValue, value.mValue));
    }

    public UByte shiftRight(short value) {
        return new UByte(Unsigned.shiftRight(mValue, value));
    }

    public UByte shiftLeft(UByte value) {
        return new UByte(Unsigned.shiftLeft(mValue, value.mValue));
    }

    public UByte shiftLeft(short value) {
        return new UByte(Unsigned.shiftLeft(mValue, value));
    }
}