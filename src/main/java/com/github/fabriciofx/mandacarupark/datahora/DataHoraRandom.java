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
package com.github.fabriciofx.mandacarupark.datahora;

import com.github.fabriciofx.mandacarupark.DataHora;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

public final class DataHoraRandom implements DataHora {
    private final DataHora origin;

    public DataHoraRandom(
        final LocalDateTime inicio,
        final LocalDateTime termino
    ) {
        this(new DataHoraOf(inicio), new DataHoraOf(termino));
    }

    public DataHoraRandom(final DataHora inicio, final DataHora termino) {
        this.origin = new DataHora() {
            @Override
            public LocalDateTime dateTime() {
                final long min = inicio.dateTime().toEpochSecond(
                    ZoneOffset.UTC
                );
                final long max = termino.dateTime().toEpochSecond(
                    ZoneOffset.UTC
                ) + 1;
                final long random = ThreadLocalRandom.current().nextLong(
                    min, max
                );
                return LocalDateTime.ofEpochSecond(random, 0, ZoneOffset.UTC);
            }

            @Override
            public String data() {
                return new DataHoraOf(this.dateTime()).data();
            }

            @Override
            public String hora() {
                return new DataHoraOf(this.dateTime()).hora();
            }

            @Override
            public String toString() {
                return new DataHoraOf(this.dateTime()).toString();
            }
        };
    }

    @Override
    public LocalDateTime dateTime() {
        return this.origin.dateTime();
    }

    @Override
    public String data() {
        return this.origin.data();
    }

    @Override
    public String hora() {
        return this.origin.hora();
    }

    @Override
    public String toString() {
        return this.origin.toString();
    }
}
