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

public final class MemoryDataStream implements DataStream {
    private final byte[] buffer;
    private int position;

    public MemoryDataStream() {
        this(128);
    }

    public MemoryDataStream(int length) {
        this(new byte[length]);
    }

    public MemoryDataStream(final Bytes bytes) {
        this(bytes.asBytes());
    }

    public MemoryDataStream(final byte[] data) {
        this.buffer = data;
        this.position = 0;
    }

    @Override
    public int read(byte[] bytes, int offset, int length) {
        if (this.position >= this.buffer.length) {
            return -1;
        }
        final int remaining = this.buffer.length - this.position;
        if (length > remaining) {
            length = remaining;
        }
        System.arraycopy(this.buffer, this.position, bytes, offset, length);
        this.position = this.position + length;
        return length;
    }

    @Override
    public void write(byte[] bytes, int offset, int length) {
        System.arraycopy(bytes, offset, this.buffer, this.position, length);
        this.position = this.position + length;
    }

    @Override
    public int size() {
        return this.buffer.length;
    }

    @Override
    public void seek(int position) {
        if (position < this.buffer.length) {
            this.position = position;
        }
    }

    @Override
    public byte[] asBytes() {
        int length = this.position;
        if (length == 0) {
            length = this.buffer.length;
        }
        final byte[] result = new byte[length];
        System.arraycopy(this.buffer, 0, result, 0, length);
        return result;
    }

    @Override
    public String toString() {
        int length = this.position;
        if (length == 0) {
            length = this.buffer.length;
        }
        return new String(this.buffer, 0, length);
    }
}
