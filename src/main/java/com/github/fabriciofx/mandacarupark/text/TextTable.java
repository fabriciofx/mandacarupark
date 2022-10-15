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
package com.github.fabriciofx.mandacarupark.text;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class TextTable implements Text {
    private final String[][] content;

    public TextTable(final String[][] content) {
        this.content = content;
    }

    @Override
    public String asString() {
        final String border = Arrays.stream(this.content[0])
            // border row between rows
            .map(str -> "-".repeat(str.length()))
            .collect(Collectors.joining("+", "+", "+\n"));
        return Arrays.stream(this.content)
            // table row with borders between cells
            .map(
                row -> Arrays.stream(row)
                .collect(Collectors.joining("|", "|", "|\n"))
            )
            .collect(Collectors.joining(border, border, border));
    }
}
