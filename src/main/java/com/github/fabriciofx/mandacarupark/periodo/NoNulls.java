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
package com.github.fabriciofx.mandacarupark.periodo;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Periodo;
import java.time.DayOfWeek;

public final class NoNulls implements Periodo {
    private final Periodo origin;

    public NoNulls(final Periodo periodo) {
        this.origin = periodo;
    }

    @Override
    public long minutos() {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de um período válido"
            );
        }
        return this.origin.minutos();
    }

    @Override
    public boolean contem(final DataHora dataHora) {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de um período válido"
            );
        }
        if (dataHora == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de uma dataHora válida"
            );
        }
        return this.origin.contem(dataHora);
    }

    @Override
    public DataHora inicio() {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de um período válido"
            );
        }
        final DataHora inicio = this.origin.inicio();
        if (inicio == null) {
            throw new IllegalStateException(
                "NULL ao invés de um inicio válido"
            );
        }
        return inicio;
    }

    @Override
    public DataHora termino() {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de um período válido"
            );
        }
        final DataHora termino = this.origin.termino();
        if (termino == null) {
            throw new IllegalStateException(
                "NULL ao invés de um termino válido"
            );
        }
        return termino;
    }

    @Override
    public boolean diaDaSemana(final DayOfWeek dia) {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de um período válido"
            );
        }
        return this.origin.diaDaSemana(dia);
    }
}
