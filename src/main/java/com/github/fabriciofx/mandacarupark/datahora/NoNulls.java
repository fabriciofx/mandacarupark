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
package com.github.fabriciofx.mandacarupark.datahora;

import com.github.fabriciofx.mandacarupark.DataHora;
import java.time.LocalDateTime;

public final class NoNulls implements DataHora {
    private final DataHora origin;

    public NoNulls(final DataHora dataHora) {
        this.origin = dataHora;
    }

    @Override
    public LocalDateTime dateTime() {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de uma dataHora válida"
            );
        }
        final LocalDateTime dateTime = this.origin.dateTime();
        if (dateTime == null) {
            throw new IllegalStateException(
                "NULL ao invés de um datetime válido"
            );
        }
        return dateTime;
    }

    @Override
    public String data() {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de uma dataHora válida"
            );
        }
        final String data = this.origin.data();
        if (data == null) {
            throw new IllegalStateException(
                "NULL ao invés de um data válida"
            );
        }
        return data;
    }

    @Override
    public String hora() {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de uma dataHora válida"
            );
        }
        final String hora = this.origin.hora();
        if (hora == null) {
            throw new IllegalStateException(
                "NULL ao invés de um datetime válido"
            );
        }
        return hora;
    }
}
