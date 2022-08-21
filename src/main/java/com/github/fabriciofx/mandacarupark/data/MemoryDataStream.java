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
    public boolean readBoolean() {
        return this.readByte() != 0;
    }

    @Override
    public byte readByte() {
        final byte a = this.buffer[this.position];
        this.position++;
        return a;
    }

    @Override
    public int readUnsignedByte() {
        final byte a = this.buffer[this.position];
        this.position++;
        return (byte) (a & 0xff);
    }

    @Override
    public short readShort() {
        final byte a = this.buffer[this.position];
        final byte b = this.buffer[this.position + 1];
        this.position = this.position + 2;
        return (short) ((a << 8) | (b & 0xff));
    }

    @Override
    public int readUnsignedShort() {
        final byte a = this.buffer[this.position];
        final byte b = this.buffer[this.position + 1];
        this.position = this.position + 2;
        return (((a & 0xff) << 8) | (b & 0xff));
    }

    @Override
    public char readChar() {
        final byte a = this.buffer[this.position];
        final byte b = this.buffer[this.position + 1];
        this.position = this.position + 2;
        return (char) ((a << 8) | (b & 0xff));
    }

    @Override
    public int readInt() {
        final byte a = this.buffer[this.position];
        final byte b = this.buffer[this.position + 1];
        final byte c = this.buffer[this.position + 2];
        final byte d = this.buffer[this.position + 3];
        this.position = this.position + 4;
        return (
            ((a & 0xff) << 24) |
                ((b & 0xff) << 16) |
                ((c & 0xff) << 8) |
                (d & 0xff)
        );
    }

    @Override
    public long readLong() {
        final byte a = this.buffer[this.position];
        final byte b = this.buffer[this.position + 1];
        final byte c = this.buffer[this.position + 2];
        final byte d = this.buffer[this.position + 3];
        final byte e = this.buffer[this.position + 4];
        final byte f = this.buffer[this.position + 5];
        final byte g = this.buffer[this.position + 6];
        final byte h = this.buffer[this.position + 7];
        this.position = this.position + 8;
        return (
            ((long) (a & 0xff) << 56) |
                ((long) (b & 0xff) << 48) |
                ((long) (c & 0xff) << 40) |
                ((long) (d & 0xff) << 32) |
                ((long) (e & 0xff) << 24) |
                ((long) (f & 0xff) << 16) |
                ((long) (g & 0xff) << 8) |
                ((long) (h & 0xff))
        );
    }

    @Override
    public float readFloat() {
        return Float.intBitsToFloat(this.readInt());
    }

    @Override
    public double readDouble() {
        return Double.longBitsToDouble(this.readLong());
    }

    @Override
    public String readString() {
        final int utflen = this.readUnsignedShort();
        final char[] chars = new char[utflen];
        int chr, char2, char3;
        int count = 0;
        int charsCount = 0;
        while (count < utflen) {
            chr = (int) this.buffer[count] & 0xff;
            if (chr > 127) {
                break;
            }
            count++;
            chars[charsCount++] = (char) chr;
        }
        while (count < utflen) {
            chr = (int) this.buffer[count] & 0xff;
            switch (chr >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    /* 0xxxxxxx*/
                    count++;
                    chars[charsCount++] = (char) chr;
                    break;
                case 12:
                case 13:
                    /* 110x xxxx   10xx xxxx*/
                    count += 2;
                    if (count > utflen) {
                        throw new RuntimeException(
                            "malformed input: partial character at end");
                    }
                    char2 = (int) this.buffer[count - 1];
                    if ((char2 & 0xC0) != 0x80) {
                        throw new RuntimeException(
                            "malformed input around byte " + count);
                    }
                    chars[charsCount++] = (char) (
                        ((chr & 0x1F) << 6) |
                            (char2 & 0x3F)
                    );
                    break;
                case 14:
                    /* 1110 xxxx  10xx xxxx  10xx xxxx */
                    count += 3;
                    if (count > utflen) {
                        throw new RuntimeException(
                            "malformed input: partial character at end");
                    }
                    char2 = (int) this.buffer[count - 2];
                    char3 = (int) this.buffer[count - 1];
                    if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80)) {
                        throw new RuntimeException(
                            "malformed input around byte " + (count - 1));
                    }
                    chars[charsCount++] = (char) (
                        ((chr & 0x0F) << 12) |
                            ((char2 & 0x3F) << 6) |
                            ((char3 & 0x3F) << 0)
                    );
                    break;
                default:
                    /* 10xx xxxx,  1111 xxxx */
                    throw new RuntimeException(
                        "malformed input around byte " + count);
            }
        }
        // The number of chars produced may be less than utflen
        return new String(chars, 0, charsCount);
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
    public void writeBoolean(final boolean value) {
        if (value) {
            this.buffer[this.position] = (byte) 1;
        } else {
            this.buffer[this.position] = (byte) 0;
        }
        this.position++;
    }

    @Override
    public void writeByte(final int value) {
        this.buffer[this.position] = (byte) value;
        this.position++;
    }

    @Override
    public void writeShort(final int value) {
        this.buffer[this.position] = (byte) (0xff & (value >> 8));
        this.buffer[this.position + 1] = (byte) (0xff & value);
        this.position = this.position + 2;
    }

    @Override
    public void writeChar(final int value) {
        this.buffer[this.position] = (byte) (0xff & (value >> 8));
        this.buffer[this.position + 1] = (byte) (0xff & value);
        this.position = this.position + 2;
    }

    @Override
    public void writeInt(final int value) {
        this.buffer[this.position] = (byte) (0xff & (value >> 24));
        this.buffer[this.position + 1] = (byte) (0xff & (value >> 16));
        this.buffer[this.position + 2] = (byte) (0xff & (value >> 8));
        this.buffer[this.position + 3] = (byte) (0xff & value);
        this.position = this.position + 4;
    }

    @Override
    public void writeLong(final long value) {
        this.buffer[this.position] = (byte) (0xff & (value >> 56));
        this.buffer[this.position + 1] = (byte) (0xff & (value >> 48));
        this.buffer[this.position + 2] = (byte) (0xff & (value >> 40));
        this.buffer[this.position + 3] = (byte) (0xff & value >> 32);
        this.buffer[this.position + 4] = (byte) (0xff & (value >> 24));
        this.buffer[this.position + 5] = (byte) (0xff & (value >> 16));
        this.buffer[this.position + 6] = (byte) (0xff & (value >> 8));
        this.buffer[this.position + 7] = (byte) (0xff & value);
        this.position = this.position + 8;
    }

    @Override
    public void writeFloat(final float value) {
        this.writeInt(Float.floatToIntBits(value));
    }

    @Override
    public void writeDouble(final double value) {
        this.writeLong(Double.doubleToLongBits(value));
    }

    @Override
    public void writeString(final String value) {
        final int strlen = value.length();
        int utflen = 0;
        int count = 0;
        /* use charAt instead of copying String to char array */
        for (int idx = 0; idx < strlen; idx++) {
            final int chr = value.charAt(idx);
            if ((chr >= 0x0001) && (chr <= 0x007F)) {
                utflen++;
            } else if (chr > 0x07FF) {
                utflen = utflen + 3;
            } else {
                utflen = utflen + 2;
            }
        }
        if (utflen > 65535) {
            throw new RuntimeException(
                "encoded string too long: " + utflen + " bytes");
        }
        final byte[] bytes = new byte[utflen + 2];
        bytes[count++] = (byte) ((utflen >>> 8) & 0xFF);
        bytes[count++] = (byte) ((utflen >>> 0) & 0xFF);
        int idx = 0;
        for (idx = 0; idx < strlen; idx++) {
            int chr = value.charAt(idx);
            if (!((chr >= 0x0001) && (chr <= 0x007F))) {
                break;
            }
            bytes[count++] = (byte) chr;
        }
        for (; idx < strlen; idx++) {
            final int chr = value.charAt(idx);
            if ((chr >= 0x0001) && (chr <= 0x007F)) {
                bytes[count++] = (byte) chr;
            } else if (chr > 0x07FF) {
                bytes[count++] = (byte) (0xE0 | ((chr >> 12) & 0x0F));
                bytes[count++] = (byte) (0x80 | ((chr >> 6) & 0x3F));
                bytes[count++] = (byte) (0x80 | ((chr >> 0) & 0x3F));
            } else {
                bytes[count++] = (byte) (0xC0 | ((chr >> 6) & 0x1F));
                bytes[count++] = (byte) (0x80 | ((chr >> 0) & 0x3F));
            }
        }
        this.write(bytes, 0, utflen + 2);
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
    public void seek(final int position) {
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
