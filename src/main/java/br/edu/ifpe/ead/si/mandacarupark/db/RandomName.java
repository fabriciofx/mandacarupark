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
package br.edu.ifpe.ead.si.mandacarupark.db;

import java.util.Random;

public class RandomName {
    private final int length;
    private final char[] chars;

    public RandomName() {
        this(5);
    }

    public RandomName(final int length) {
        this(
            length,
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        );
    }

    public RandomName(final int length, final char ...chars) {
        this.length = length;
        this.chars = chars;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final Random rand = new Random();
        for(int idx = 0; idx < this.length; idx++) {
            final int pos = rand.nextInt(this.chars.length);
            sb.append(this.chars[pos]);
        }
        return sb.toString();
    }
}
