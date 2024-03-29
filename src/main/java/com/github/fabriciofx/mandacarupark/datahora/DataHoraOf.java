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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public final class DataHoraOf implements DataHora {
    private final LocalDateTime dateTime;

    public DataHoraOf() {
        this(LocalDateTime.now());
    }

    public DataHoraOf(final String dataHora) {
        this(
            LocalDateTime.parse(
                dataHora.replace('T', ' '),
                new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss.SSSSSSSSS"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss.SSSSSSSS"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss.SSSSSSS"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss.SSSSSS"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss.SSSSS"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss.SSSS"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss.SSS"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss.SS"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss.S"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm:ss"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "uuuu-MM-dd HH:mm"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ofPattern(
                            "dd/MM/uuuu HH:mm:ss"
                        )
                    ).appendOptional(
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME
                    ).toFormatter()
            )
        );
    }

    public DataHoraOf(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public LocalDateTime dateTime() {
        return this.dateTime;
    }

    @Override
    public String data() {
        final DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "dd/MM/uuuu"
        );
        return this.dateTime.format(formato);
    }

    @Override
    public String hora() {
        final DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "HH:mm:ss"
        );
        return this.dateTime.format(formato);
    }

    @Override
    public String toString() {
        final DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "dd/MM/uuuu HH:mm:ss"
        );
        return this.dateTime.format(formato);
    }
}
