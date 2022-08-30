/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.mandacarupark.crc;

public class Combine implements Crc {
    /* ECMA-182 */
    private final static long POLY = 0xc96c5795d7870f42L;
    /* Dimension of GF(2) vectors (length of CRC) */
    private final static int GF2_DIM = 64;
    /* Current CRC value. */
    private final Crc crc;

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

    /*
     * Return the CRC-64 of two sequential blocks, where summ1 is the CRC-64
     * of the first block, summ2 is the CRC-64 of the second block, and len2
     * is the length of the second block.
     */
    public Combine(Crc64 summ1, Crc64 summ2, long length2) {
        this.crc = () -> {
            long value = 0L;
            long len2 = length2;
            // degenerate case.
            if (len2 == 0) {
                value = summ1.value();
                return value;
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
            value = crc1;
            return value;
        };
    }

    /**
     * Get long representation of current CRC64 value.
     **/
    @Override
    public long value() {
        return this.crc.value();
    }
}
