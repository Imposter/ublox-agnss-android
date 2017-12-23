package ca.indigogames.ubloxagps.compat;

import android.support.annotation.NonNull;

public final class UShort extends Number implements Comparable<UShort> {
    public static final int BYTES = 2;
    public static final int MIN_VALUE = Unsigned.UNSIGNED_SHORT_MIN;
    public static final int MAX_VALUE = Unsigned.UNSIGNED_SHORT_MAX;
    public static final int SIZE = 16;
    public static final Class<UShort> TYPE = UShort.class;

    public static final UShort MIN = valueOf(MIN_VALUE);
    public static final UShort MAX = valueOf(MAX_VALUE);

    private int mValue;

    public UShort(short value) {
        mValue = Unsigned.toUnsigned(value);
    }

    public UShort(int unsignedValue) throws NumberFormatException {
        mValue = unsignedValue;
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public UShort(String s) throws NumberFormatException {
        mValue = Integer.parseInt(s);
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public UShort(String s, int radix) throws NumberFormatException {
        mValue = Integer.parseInt(s, radix);
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public static UShort valueOf(String s, int radix) throws NumberFormatException {
        return new UShort(s, radix);
    }

    public static UShort valueOf(String s) throws NumberFormatException {
        return new UShort(s);
    }

    public static UShort valueOf(short signedValue) throws NumberFormatException {
        return new UShort(signedValue);
    }

    public static UShort valueOf(int unsignedValue) throws NumberFormatException {
        return new UShort(unsignedValue);
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
        return obj instanceof UShort && ((UShort)obj).mValue == mValue;
    }

    public int compareTo(@NonNull UShort other) {
        return Integer.valueOf(mValue).compareTo(other.mValue);
    }

    public UShort add(UShort value) {
        return new UShort(Unsigned.add(mValue, value.mValue));
    }

    public UShort add(int value) {
        return new UShort(Unsigned.add(mValue, value));
    }

    public UShort subtract(UShort value) {
        return new UShort(Unsigned.subtract(mValue, value.mValue));
    }

    public UShort subtract(int value) {
        return new UShort(Unsigned.subtract(mValue, value));
    }

    public UShort multiply(UShort value) {
        return new UShort(Unsigned.multiply(mValue, value.mValue));
    }

    public UShort multiply(int value) {
        return new UShort(Unsigned.multiply(mValue, value));
    }

    public UShort divide(UShort value) {
        return new UShort(Unsigned.divide(mValue, value.mValue));
    }

    public UShort divide(int value) {
        return new UShort(Unsigned.divide(mValue, value));
    }

    public UShort mod(UShort value) {
        return new UShort(Unsigned.mod(mValue, value.mValue));
    }

    public UShort mod(int value) {
        return new UShort(Unsigned.mod(mValue, value));
    }

    public UShort and(UShort value) {
        return new UShort(Unsigned.and(mValue, value.mValue));
    }

    public UShort and(int value) {
        return new UShort(Unsigned.and(mValue, value));
    }

    public UShort or(UShort value) {
        return new UShort(Unsigned.or(mValue, value.mValue));
    }

    public UShort or(int value) {
        return new UShort(Unsigned.or(mValue, value));
    }

    public UShort xor(UShort value) {
        return new UShort(Unsigned.xor(mValue, value.mValue));
    }

    public UShort xor(int value) {
        return new UShort(Unsigned.xor(mValue, value));
    }

    public UShort shiftRight(UShort value) {
        return new UShort(Unsigned.shiftRight(mValue, value.mValue));
    }

    public UShort shiftRight(int value) {
        return new UShort(Unsigned.shiftRight(mValue, value));
    }

    public UShort shiftLeft(UShort value) {
        return new UShort(Unsigned.shiftLeft(mValue, value.mValue));
    }

    public UShort shiftLeft(int value) {
        return new UShort(Unsigned.shiftLeft(mValue, value));
    }
}