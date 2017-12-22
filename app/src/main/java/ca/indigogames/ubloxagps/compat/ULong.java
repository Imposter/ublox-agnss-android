package ca.indigogames.ubloxagps.compat;

import android.support.annotation.NonNull;

import java.math.BigInteger;

public final class ULong extends Number implements Comparable<ULong> {
    public static final int BYTES = 8;
    public static final BigInteger MAX_VALUE = new BigInteger("18446744073709551615");
    public static final BigInteger MIN_VALUE = BigInteger.ZERO;
    public static final int SIZE = 64;
    public static final Class<ULong> TYPE = ULong.class;

    public static final ULong MIN = valueOf(MIN_VALUE);
    public static final ULong MAX = valueOf(MAX_VALUE);

    private BigInteger mValue;

    public ULong(long value) {
        mValue = MAX_VALUE.and(BigInteger.valueOf(value));
    }

    public ULong(BigInteger unsignedInteger) throws NumberFormatException {
        mValue = unsignedInteger;
        checkRange();
    }

    public ULong(String s) throws NumberFormatException {
        mValue = new BigInteger(s);
        checkRange();
    }

    public ULong(String s, int radix) throws NumberFormatException {
        mValue = new BigInteger(s, radix);
        checkRange();
    }

    public static String toString(ULong s) {
        return s.mValue.toString();
    }

    public static ULong parseUShort(String s, int radix) throws NumberFormatException {
        return new ULong(s, radix);
    }

    public static ULong parseUShort(String s) throws NumberFormatException {
        return new ULong(s);
    }

    public static ULong valueOf(String s, int radix) throws NumberFormatException {
        return parseUShort(s, radix);
    }

    public static ULong valueOf(String s) throws NumberFormatException {
        return new ULong(s);
    }

    public static ULong valueOf(long s) throws NumberFormatException {
        return new ULong(s);
    }

    public static ULong valueOf(BigInteger i) throws NumberFormatException {
        return new ULong(i);
    }

    public static ULong decode(String nm) throws NumberFormatException {
        return new ULong(nm);
    }

    private void checkRange() throws NumberFormatException {
        if (mValue.compareTo(MIN_VALUE) < 0 || mValue.compareTo(MAX_VALUE) > 0)
            throw new NumberFormatException("Value out of range: " + mValue);
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

    public int hashCode() {
        return mValue.hashCode();
    }

    public static int hashCode(ULong value) {
        return value.mValue.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof ULong && ((ULong)obj).mValue.equals(mValue);

    }

    public int compareTo(@NonNull ULong anotherUShort) {
        return mValue.compareTo(anotherUShort.mValue);
    }

    public static int compare(ULong x, ULong y) {
        return x.mValue.compareTo(y.mValue);
    }

    public static long toSignedLong(ULong x) {
        return x.mValue.longValue();
    }
}
