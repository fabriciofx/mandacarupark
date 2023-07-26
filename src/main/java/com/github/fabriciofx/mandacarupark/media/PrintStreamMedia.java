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
package com.github.fabriciofx.mandacarupark.media;

import com.github.fabriciofx.mandacarupark.Media;
import java.io.PrintStream;

public final class PrintStreamMedia implements Media {
    private final PrintStream stream;
    private final Media media;

    public PrintStreamMedia() {
        this(System.out, new MemMedia());
    }

    public PrintStreamMedia(final PrintStream stream, final Media media) {
        this.stream = stream;
        this.media = media;
    }

    @Override
    public <T> T select(final String query) {
        return this.media.select(query);
    }

    @Override
    public Media begin(final String name) {
        return this;
    }

    @Override
    public Media end(final String name) {
        return this;
    }

    @Override
    public Media with(final String key, final Object value) {
        this.stream.printf("%s: %s\n", key, value.toString());
        return new PrintStreamMedia(this.stream, this.media.with(key, value));
    }
}
