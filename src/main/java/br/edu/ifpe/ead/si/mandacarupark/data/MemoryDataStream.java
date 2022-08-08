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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class MemoryDataStream implements DataStream {
    private final byte[] data;

    public MemoryDataStream() {
        this(256);
    }

    public MemoryDataStream(int length) {
        this(new byte[length]);
    }

    public MemoryDataStream(final byte[] data) {
        this.data = data;
    }

    @Override
    public int read(byte[] bytes, int offset, int length) {
        if (bytes == null) {
            throw new NullPointerException("bytes[] is null!");
        }
        if (offset < 0) {
            throw new IndexOutOfBoundsException("offset is negative!");
        }
        if (length < 0) {
            throw new IndexOutOfBoundsException("length is negative!");
        }
        if (length > bytes.length - offset) {
            throw new IndexOutOfBoundsException(
                "length > bytes.length - offset!"
            );
        }
        int read = 0;
        while (read < length && read < this.data.length) {
            bytes[offset + read] = this.data[read];
            read++;
        }
        return read;
    }

    @Override
    public int write(byte[] bytes, int offset, int length) {
        if (bytes == null) {
            throw new NullPointerException("bytes[] is null!");
        }
        if (offset < 0) {
            throw new IndexOutOfBoundsException("offset is negative!");
        }
        if (length < 0) {
            throw new IndexOutOfBoundsException("length is negative!");
        }
        if (offset + length > this.data.length) {
            throw new IndexOutOfBoundsException(
                "offset + length > data length!"
            );
        }
        int written = 0;
        while (written < length) {
            this.data[written] = bytes[offset + written];
            written++;
        }
        return written;
    }

    @Override
    public int size() {
        return this.data.length;
    }

    @Override
    public InputStream input() {
        return new ByteArrayInputStream(this.data);
    }

    @Override
    public OutputStream output() {
        final OutputStream stream = new ByteArrayOutputStream(this.data.length);
        try {
            stream.write(this.data);
            stream.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return stream;
    }
}
