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
package com.github.fabriciofx.mandacarupark.periodo;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Periodo;
import java.time.DayOfWeek;

public final class Restricao implements Periodo {
    private final Periodo origin;

    public Restricao(final Periodo periodo) {
        this.origin = periodo;
    }

    @Override
    public long minutos() {
        this.inicioAntesTermino(this.origin);
        return this.origin.minutos();
    }

    @Override
    public boolean contem(final DataHora dataHora) {
        this.inicioAntesTermino(this.origin);
        return this.origin.contem(dataHora);
    }

    @Override
    public boolean contem(final Periodo periodo) {
        this.inicioAntesTermino(this.origin);
        return this.origin.contem(periodo);
    }

    @Override
    public DataHora inicio() {
        this.inicioAntesTermino(this.origin);
        return this.origin.inicio();
    }

    @Override
    public DataHora termino() {
        this.inicioAntesTermino(this.origin);
        return this.origin.termino();
    }

    @Override
    public boolean diaDaSemana(final DayOfWeek dia) {
        this.inicioAntesTermino(this.origin);
        return this.origin.diaDaSemana(dia);
    }

    private void inicioAntesTermino(final Periodo periodo) {
        if (periodo.inicio().dateTime().isAfter(periodo.termino().dateTime())) {
            throw new RuntimeException(
                "Periodo: o tempo de ínicio não pode ser depois do término"
            );
        }
    }
}
