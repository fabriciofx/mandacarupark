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
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Calcula o tempo em minutos decorridos entre o início e o término.
 */
public final class PeriodoOf implements Periodo {
    private final DataHora inicio;
    private final DataHora termino;

    public PeriodoOf(
        final LocalDateTime inicio,
        final LocalDateTime termino
    ) {
        this(new DataHoraOf(inicio), new DataHoraOf(termino));
    }

    public PeriodoOf(final DataHora inicio, final DataHora termino) {
        this.inicio = inicio;
        this.termino = termino;
    }

    @Override
    public Duration duration() {
        return Duration.between(
            this.inicio.dateTime(),
            this.termino.dateTime()
        );
    }

    @Override
    public boolean contem(final DataHora dataHora) {
        return !this.inicio.dateTime().isAfter(dataHora.dateTime()) &&
            !this.termino.dateTime().isBefore(dataHora.dateTime());
    }

    @Override
    public boolean contem(final Periodo periodo) {
        return !this.inicio.dateTime().isAfter(periodo.inicio().dateTime()) &&
            !this.termino.dateTime().isBefore(periodo.termino().dateTime());
    }

    @Override
    public DataHora inicio() {
        return this.inicio;
    }

    @Override
    public DataHora termino() {
        return this.termino;
    }

    @Override
    public boolean diaDaSemana(final DayOfWeek dia) {
        return this.inicio.dateTime().getDayOfWeek() == dia &&
            this.termino.dateTime().getDayOfWeek() == dia;
    }
}
