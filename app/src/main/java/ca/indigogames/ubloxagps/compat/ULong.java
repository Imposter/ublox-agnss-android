package ca.indigogames.ubloxagps.compat;

import android.support.annotation.NonNull;

import java.math.BigInteger;

public final class ULong extends Number implements Comparable<ULong> {
    public static final int BYTES = 8;
    public static final BigInteger MIN_VALUE = Unsigned.UNSIGNED_LONG_MIN;
    public static final BigInteger MAX_VALUE = Unsigned.UNSIGNED_LONG_MAX;
    public static final int SIZE = 32;
    public static final Class<ULong> TYPE = ULong.class;

    public static final ULong MIN = valueOf(MIN_VALUE);
    public static final ULong MAX = valueOf(MAX_VALUE);

    private BigInteger mValue;

    public ULong(long value) {
        mValue = Unsigned.toUnsigned(value);
    }

    public ULong(BigInteger unsignedValue) throws NumberFormatException {
        mValue = unsignedValue;
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public ULong(String s) throws NumberFormatException {
        mValue = new BigInteger(s);
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public ULong(String s, int radix) throws NumberFormatException {
        mValue = new BigInteger(s, radix);
        Unsigned.wrap(mValue, MIN_VALUE, MAX_VALUE);
    }

    public static ULong valueOf(String s, int radix) throws NumberFormatException {
        return new ULong(s, radix);
    }

    public static ULong valueOf(String s) throws NumberFormatException {
        return new ULong(s);
    }

    public static ULong valueOf(long signedValue) throws NumberFormatException {
        return new ULong(signedValue);
    }

    public static ULong valueOf(BigInteger unsignedValue) throws NumberFormatException {
        return new ULong(unsignedValue);
    }

    public byte byteValue() {
        return mValue.byteValue();
    }

    public short shortValue() {
        return mValue.shortValue();
    }

    public int intValue() {
        return mValue.intValue();
    }

    public long longValue() {
        return mValue.longValue();
    }

    public float floatValue() {
        return mValue.floatValue();
    }

    public double doubleValue() {
        return mValue.doubleValue();
    }

    public String toString() {
        return mValue.toString();
    }

    public boolean equals(Object obj) {
        return obj instanceof ULong && ((ULong)obj).mValue.equals(mValue);
    }

    public int compareTo(@NonNull ULong other) {
        return mValue.compareTo(other.mValue);
    }

    public ULong add(ULong value) {
        return new ULong(Unsigned.add(mValue, value.mValue));
    }

    public ULong add(BigInteger value) {
        return new ULong(Unsigned.add(mValue, value));
    }

    public ULong subtract(ULong value) {
        return new ULong(Unsigned.subtract(mValue, value.mValue));
    }

    public ULong subtract(BigInteger value) {
        return new ULong(Unsigned.subtract(mValue, value));
    }

    public ULong multiply(ULong value) {
        return new ULong(Unsigned.multiply(mValue, value.mValue));
    }

    public ULong multiply(BigInteger value) {
        return new ULong(Unsigned.multiply(mValue, value));
    }

    public ULong divide(ULong value) {
        return new ULong(Unsigned.divide(mValue, value.mValue));
    }

    public ULong divide(BigInteger value) {
        return new ULong(Unsigned.divide(mValue, value));
    }

    public ULong mod(ULong value) {
        return new ULong(Unsigned.mod(mValue, value.mValue));
    }

    public ULong mod(BigInteger value) {
        return new ULong(Unsigned.mod(mValue, value));
    }

    public ULong and(ULong value) {
        return new ULong(Unsigned.and(mValue, value.mValue));
    }

    public ULong and(BigInteger value) {
        return new ULong(Unsigned.and(mValue, value));
    }

    public ULong or(ULong value) {
        return new ULong(Unsigned.or(mValue, value.mValue));
    }

    public ULong or(BigInteger value) {
        return new ULong(Unsigned.or(mValue, value));
    }

    public ULong xor(ULong value) {
        return new ULong(Unsigned.xor(mValue, value.mValue));
    }

    public ULong xor(BigInteger value) {
        return new ULong(Unsigned.xor(mValue, value));
    }

    public ULong shiftRight(ULong value) {
        return new ULong(Unsigned.shiftRight(mValue, value.mValue));
    }

    public ULong shiftRight(BigInteger value) {
        return new ULong(Unsigned.shiftRight(mValue, value));
    }

    public ULong shiftLeft(ULong value) {
        return new ULong(Unsigned.shiftLeft(mValue, value.mValue));
    }

    public ULong shiftLeft(BigInteger value) {
        return new ULong(Unsigned.shiftLeft(mValue, value));
    }
}