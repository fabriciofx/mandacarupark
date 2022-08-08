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
package br.edu.ifpe.ead.si.mandacarupark.data;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class TestMemoryDataStream {
    @Test
    public void write() throws IOException {
        final DataStream stream = new MemoryDataStream();
        final int written = stream.write(
            "test".getBytes(StandardCharsets.UTF_8),
            0,
            4
        );
        final InputStream input = stream.input();
        final byte[] buf = new byte[10];
        input.read(buf, 0, buf.length);
        Assert.assertEquals(4, written);
        Assert.assertEquals(new String(buf, 0, written), "test");
    }

    @Test
    public void writeFromTheBeginning() throws IOException {
        final DataStream stream = new MemoryDataStream();
        final int written = stream.write(
            "test".getBytes(StandardCharsets.UTF_8),
            0,
            2
        );
        final InputStream input = stream.input();
        final byte[] buf = new byte[10];
        input.read(buf, 0, buf.length);
        Assert.assertEquals(2, written);
        Assert.assertEquals(new String(buf, 0, written), "te");
    }

    @Test
    public void writeFromTheMiddle() throws IOException {
        final DataStream stream = new MemoryDataStream();
        final int written = stream.write(
            "test".getBytes(StandardCharsets.UTF_8),
            1,
            2
        );
        final InputStream input = stream.input();
        final byte[] buf = new byte[10];
        input.read(buf, 0, buf.length);
        Assert.assertEquals(2, written);
        Assert.assertEquals(new String(buf, 0, written), "es");
    }

    @Test
    public void writeFromTheEnd() throws IOException {
        final DataStream stream = new MemoryDataStream();
        final int written = stream.write(
            "test".getBytes(StandardCharsets.UTF_8),
            2,
            2
        );
        final InputStream input = stream.input();
        final byte[] buf = new byte[10];
        input.read(buf, 0, buf.length);
        Assert.assertEquals(2, written);
        Assert.assertEquals(new String(buf, 0, written), "st");
    }

    @Test
    public void read() {
        final byte[] content = {'t', 'e', 's', 't'};
        final DataStream stream = new MemoryDataStream(content);
        final byte[] buf = new byte[10];
        final int read = stream.read(buf, 0, buf.length);
        Assert.assertEquals(4, read);
        Assert.assertEquals(new String(buf, 0, read), "test");
    }

    @Test
    public void readFromTheBeginning() {
        final byte[] content = {'t', 'e', 's', 't'};
        final DataStream stream = new MemoryDataStream(content);
        final byte[] buf = new byte[10];
        final int read = stream.read(buf, 0, 2);
        Assert.assertEquals(2, read);
        Assert.assertEquals(new String(buf, 0, read), "te");
    }
}
