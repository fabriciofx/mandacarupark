/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022-2023 Fabrício Barros Cabral
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
package com.github.fabriciofx.mandacarupark;

import java.security.SecureRandom;
import java.util.Random;

public final class RandomPort extends Number {
    private static final long serialVersionUID = -1544510274704233661L;
    private final Random random;
    private final int min;
    private final int max;

    public RandomPort() {
        this(1025, 65535);
    }

    public RandomPort(final int min, final int max) {
        this.random = new SecureRandom();
        this.min = min;
        this.max = max;
    }

    @Override
    public int intValue() {
        return this.random.nextInt(this.min, this.max);
    }

    @Override
    public long longValue() {
        throw new UnsupportedOperationException("#longValue");
    }

    @Override
    public float floatValue() {
        throw new UnsupportedOperationException("#floatValue");
    }

    @Override
    public double doubleValue() {
        throw new UnsupportedOperationException("#doubleValue");
    }
}
