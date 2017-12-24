package ca.indigogames.ubloxagps.compat;

import java.math.BigInteger;

public final class Unsigned {
    public static final short UNSIGNED_BYTE_MIN = 0;
    public static final short UNSIGNED_BYTE_MAX = 0xFF;
    public static final int UNSIGNED_SHORT_MIN = 0;
    public static final int UNSIGNED_SHORT_MAX = 0xFFFF;
    public static final long UNSIGNED_INT_MIN = 0;
    public static final long UNSIGNED_INT_MAX = 0xFFFFFFFFL;
    public static final BigInteger UNSIGNED_LONG_MIN = BigInteger.ZERO;
    public static final BigInteger UNSIGNED_LONG_MAX = new BigInteger("18446744073709551615");

    public static short toUnsigned(byte b) {
        return (short)(b & UNSIGNED_BYTE_MAX);
    }

    public static int toUnsigned(short b) {
        return b & UNSIGNED_SHORT_MAX;
    }

    public static long toUnsigned(int b) {
        return b & UNSIGNED_INT_MAX;
    }

    public static BigInteger toUnsigned(long b) {
        return BigInteger.valueOf(b).and(UNSIGNED_LONG_MAX);
    }

    // Unsigned Bytes
    public static short add(short a, short b) {
        return wrap((short)(a + b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    public static short subtract(short a, short b) {
        return wrap((short)(a - b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    public static short multiply(short a, short b) {
        return wrap((short)(a * b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    public static short divide(short a, short b) {
        return wrap((short)(a / b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    public static short mod(short a, short b) {
        return wrap((short)(a % b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    public static short and(short a, short b) {
        return wrap((short)(a & b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    public static short or(short a, short b) {
        return wrap((short)(a | b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    public static short xor(short a, short b) {
        return wrap((short)(a ^ b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    public static short shiftRight(short a, short b) {
        return wrap((short)(a >> b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    public static short shiftLeft(short a, short b) {
        return wrap((short)(a << b), UNSIGNED_BYTE_MIN, UNSIGNED_BYTE_MAX);
    }

    // Unsigned Shorts
    public static int add(int a, int b) {
        return wrap(a + b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    public static int subtract(int a, int b) {
        return wrap(a - b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    public static int multiply(int a, int b) {
        return wrap(a * b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    public static int divide(int a, int b) {
        return wrap(a / b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    public static int mod(int a, int b) {
        return wrap(a % b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    public static int and(int a, int b) {
        return wrap(a & b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    public static int or(int a, int b) {
        return wrap(a | b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    public static int xor(int a, int b) {
        return wrap(a ^ b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    public static int shiftRight(int a, int b) {
        return wrap(a >> b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    public static int shiftLeft(int a, int b) {
        return wrap(a << b, UNSIGNED_SHORT_MIN, UNSIGNED_SHORT_MAX);
    }

    // Unsigned Integers
    public static long add(long a, long b) {
        return wrap(a + b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    public static long subtract(long a, long b) {
        return wrap(a - b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    public static long multiply(long a, long b) {
        return wrap(a * b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    public static long divide(long a, long b) {
        return wrap(a / b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    public static long mod(long a, long b) {
        return wrap(a % b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    public static long and(long a, long b) {
        return wrap(a & b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    public static long or(long a, long b) {
        return wrap(a | b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    public static long xor(long a, long b) {
        return wrap(a ^ b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    public static long shiftRight(long a, long b) {
        return wrap(a >> b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    public static long shiftLeft(long a, long b) {
        return wrap(a << b, UNSIGNED_INT_MIN, UNSIGNED_INT_MAX);
    }

    // Unsigned Longs
    public static BigInteger add(BigInteger a, BigInteger b) {
        return wrap(a.add(b), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static BigInteger subtract(BigInteger a, BigInteger b) {
        return wrap(a.subtract(b), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static BigInteger multiply(BigInteger a, BigInteger b) {
        return wrap(a.multiply(b), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static BigInteger divide(BigInteger a, BigInteger b) {
        return wrap(a.divide(b), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static BigInteger mod(BigInteger a, BigInteger b) {
        return wrap(a.mod(b), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static BigInteger and(BigInteger a, BigInteger b) {
        return wrap(a.and(b), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static BigInteger or(BigInteger a, BigInteger b) {
        return wrap(a.or(b), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static BigInteger xor(BigInteger a, BigInteger b) {
        return wrap(a.xor(b), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static BigInteger shiftRight(BigInteger a, BigInteger b) {
        return wrap(a.shiftRight(b.intValue()), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static BigInteger shiftLeft(BigInteger a, BigInteger b) {
        return wrap(a.shiftLeft(b.intValue()), UNSIGNED_LONG_MIN, UNSIGNED_LONG_MAX);
    }

    public static <TNumber extends Number & Comparable<TNumber>>
    TNumber wrap(TNumber val, TNumber min, TNumber max) {
        // Parse from string since we might be using longs
        BigInteger num = new BigInteger(val.toString());
        BigInteger minNum = new BigInteger(min.toString());
        BigInteger maxNum = new BigInteger(max.toString());

        while (num.compareTo(minNum) < 0) {
            num = num.add(maxNum);
        }

        while (num.compareTo(maxNum) > 0) {
            num = num.subtract(maxNum);
        }

        return (TNumber)num;
    }
}
