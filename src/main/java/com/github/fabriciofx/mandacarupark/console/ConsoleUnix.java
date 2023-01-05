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
package com.github.fabriciofx.mandacarupark.console;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Commandline user interface (CLI).
 *
 * @since 0.0.1
 */
public final class ConsoleUnix implements Console {
    /**
     * Cli input.
     */
    private final InputStream input;
    /**
     * Cli output.
     */
    private final OutputStream output;

    /**
     * Ctor.
     */
    public ConsoleUnix() {
        this(System.in, System.out);
    }

    /**
     * Ctor.
     *
     * @param npt Cli input
     * @param tpt Cli output
     */
    public ConsoleUnix(final InputStream npt, final OutputStream tpt) {
        this.input = npt;
        this.output = tpt;
    }

    @Override
    public void clear() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void write(final String message) {
        new PrintStream(this.output).print(message);
    }

   @Override
    public String read() {
        return new Scanner(this.input).next();
    }
}
