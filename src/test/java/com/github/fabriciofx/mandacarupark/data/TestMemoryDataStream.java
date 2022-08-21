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
package com.github.fabriciofx.mandacarupark.data;

import org.junit.Assert;
import org.junit.Test;

public final class TestMemoryDataStream {
    @Test
    public void copy() {
        final String msg = "The quick brown fox jumps over the lazy dog";
        final DataStream input = new MemoryDataStream(new StringAsBytes(msg));
        final DataStream output = new MemoryDataStream();
        int copied = 0;
        byte[] buffer = new byte[5];
        while (true) {
            int read = input.read(buffer, 0, 5);
            if (read < 0) {
                break;
            }
            output.write(buffer, 0, read);
            copied = copied + read;
        }
        Assert.assertEquals(msg, new String(output.asBytes()));
    }

    @Test
    public void asString() {
        final String msg = "The quick brown fox jumps over the lazy dog";
        final DataStream stream = new MemoryDataStream(new StringAsBytes(msg));
        Assert.assertEquals(msg, new String(stream.asBytes()));
    }

    @Test
    public void writeIntBe() {
        final DataStream stream = new MemoryDataStream();
        final int expected = 1234567;
        stream.writeInt(expected);
        stream.seek(0);
        final int actual = stream.readInt();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeIntLe() {
        final DataStream stream = new MemoryDataStream();
        final int expected = 7654321;
        stream.writeInt(expected);
        stream.seek(0);
        final int actual = stream.readInt();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeFloatBe() {
        final DataStream stream = new MemoryDataStream();
        final float expected = 12345.678910f;
        stream.writeFloat(expected);
        stream.seek(0);
        final float actual = stream.readFloat();
        Assert.assertEquals(expected, actual, 0.00001);
    }
}
