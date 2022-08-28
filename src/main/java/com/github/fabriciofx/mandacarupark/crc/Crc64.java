package com.github.fabriciofx.mandacarupark.crc;

import java.util.Arrays;

/**
 * CRC-64 implementation with ability to combine checksums calculated over
 * different blocks of data.
 * <p>
 * https://stackoverflow.com/questions/20562546/how-to-get-crc64-distributed-calculation-use-its-linearity-property
 * https://stackoverflow.com/questions/5563502/uuid-to-unique-integer-id
 **/
public final class Crc64 {
    /* ECMA-182 */
    private final static long POLY = 0xc96c5795d7870f42L;
    /* CRC64 calculation table. */
    private final static long[] TABLE;
    /* Dimension of GF(2) vectors (length of CRC) */
    private final static int GF2_DIM = 64;
    /* Current CRC value. */
    private long value;

    private static long gf2MatrixTimes(long[] mat, long vec) {
        long sum = 0;
        int idx = 0;
        while (vec != 0) {
            if ((vec & 1) == 1) {
                sum ^= mat[idx];
            }
            vec >>>= 1;
            idx++;
        }
        return sum;
    }

    private static void gf2MatrixSquare(long[] square, long[] mat) {
        for (int num = 0; num < GF2_DIM; num++) {
            square[num] = gf2MatrixTimes(mat, mat[num]);
        }
    }

    static {
        TABLE = new long[256];
        for (int num = 0; num < 256; num++) {
            long crc = num;
            for (int idx = 0; idx < 8; idx++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ POLY;
                } else {
                    crc = (crc >>> 1);
                }
            }
            TABLE[num] = crc;
        }
    }

    public Crc64() {
        this(0L);
    }

    public Crc64(long value) {
        this.value = value;
    }

    public Crc64(byte[] bytes, int len) {
        this.value = 0;
        update(bytes, len);
    }

    /**
     * Construct new CRC64 instance from byte array.
     **/
    public static Crc64 fromBytes(byte[] bytes) {
        long value = 0;
        for (int idx = 0; idx < 4; idx++) {
            value <<= 8;
            value ^= (long) bytes[idx] & 0xFF;
        }
        return new Crc64(value);
    }

    /**
     * Get 8 byte representation of current CRC64 value.
     **/
    public byte[] bytes() {
        byte[] bytes = new byte[8];
        for (int idx = 0; idx < 8; idx++) {
            bytes[7 - idx] = (byte) (this.value >>> (idx * 8));
        }
        return bytes;
    }

    /**
     * Get long representation of current CRC64 value.
     **/
    public long value() {
        return this.value;
    }

    /**
     * Update CRC64 with new byte block.
     **/
    public void update(byte[] bytes, int len) {
        int idx = 0;
        this.value = ~this.value;
        while (len > 0) {
            this.value = TABLE[((int) (this.value ^ bytes[idx])) & 0xff]
                ^ (this.value >>> 8);
            idx++;
            len--;
        }
        this.value = ~this.value;
    }


    /*
     * Return the CRC-64 of two sequential blocks, where summ1 is the CRC-64
     * of the first block, summ2 is the CRC-64 of the second block, and len2
     * is the length of the second block.
     */
    static public Crc64 combine(Crc64 summ1, Crc64 summ2, long len2) {
        // degenerate case.
        if (len2 == 0) {
            return new Crc64(summ1.value());
        }
        long[] even = new long[GF2_DIM]; // even-power-of-two zeros operator
        long[] odd = new long[GF2_DIM]; // odd-power-of-two zeros operator
        // put operator for one zero bit in odd
        odd[0] = POLY;      // CRC-64 polynomial
        long row = 1;
        for (int num = 1; num < GF2_DIM; num++) {
            odd[num] = row;
            row <<= 1;
        }
        // put operator for two zero bits in even
        gf2MatrixSquare(even, odd);
        // put operator for four zero bits in odd
        gf2MatrixSquare(odd, even);
        // apply len2 zeros to crc1 (first square will put the operator for one
        // zero byte, eight zero bits, in even)
        long crc1 = summ1.value();
        long crc2 = summ2.value();
        do {
            // apply zeros operator for this bit of len2
            gf2MatrixSquare(even, odd);
            if ((len2 & 1) == 1) {
                crc1 = gf2MatrixTimes(even, crc1);
            }
            len2 >>>= 1;
            // if no more bits set, then done
            if (len2 == 0) {
                break;
            }
            // another iteration of the loop with odd and even swapped
            gf2MatrixSquare(odd, even);
            if ((len2 & 1) == 1) {
                crc1 = gf2MatrixTimes(odd, crc1);
            }
            len2 >>>= 1;
            // if no more bits set, then done
        } while (len2 != 0);
        // return combined crc.
        crc1 ^= crc2;
        return new Crc64(crc1);
    }

    private static void test(byte[] bytes, int len, long crcValue)
        throws Exception {
        /* Test CRC64 default calculation. */
        Crc64 crc = new Crc64(bytes, len);
        if (crc.value() != crcValue) {
            throw new Exception(
                "mismatch: " + String.format("%016x", crc.value())
                + " should be " + String.format("%016x", crcValue)
            );
        }
        /* test combine() */
        Crc64 crc1 = new Crc64(bytes, (len + 1) >>> 1);
        Crc64 crc2 = new Crc64(
            Arrays.copyOfRange(bytes, (len + 1) >>> 1, bytes.length),
            len >>> 1
        );
        crc = Crc64.combine(crc1, crc2, len >>> 1);
        if (crc.value() != crcValue) {
            throw new Exception(
                "mismatch: " + String.format("%016x", crc.value())
                + " should be " + String.format("%016x", crcValue)
            );
        }
    }

    public static void main(String[] args) throws Exception {
        final byte[] TEST1 = "123456789".getBytes();
        final int TESTLEN1 = 9;
        final long TESTCRC1 = 0x995dc9bbdf1939faL; // ECMA.
        test(TEST1, TESTLEN1, TESTCRC1);

        final byte[] TEST2 = "This is a test of the emergency broadcast system.".getBytes();
        final int TESTLEN2 = 49;
        final long TESTCRC2 = 0x27db187fc15bbc72L; // ECMA.
        test(TEST2, TESTLEN2, TESTCRC2);

        final byte[] TEST3 = "IHATEMATH".getBytes();
        final int TESTLEN3 = 9;
        final long TESTCRC3 = 0x3920e0f66b6ee0c8L; // ECMA.
        test(TEST3, TESTLEN3, TESTCRC3);
    }
}