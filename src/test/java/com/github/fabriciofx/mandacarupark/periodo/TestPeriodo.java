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
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import org.junit.Assert;
import org.junit.Test;

public final class TestPeriodo {
    @Test
    public void contemAgora() {
        final DataHora agora = new DataHoraOf();
        Assert.assertTrue(new PeriodoOf(agora, agora).contem(agora));
    }

    @Test
    public void contem() {
        final DataHora agora = new DataHoraOf();
        final DataHora agoraMais5Minutos = new DataHoraOf(
            agora.dateTime().plusMinutes(5)
        );
        Assert.assertTrue(
            new PeriodoOf(agora, agoraMais5Minutos).contem(agora)
        );
    }

    @Test
    public void inicioAntesTermino() {
        Assert.assertThrows(
            "Periodo: o tempo de ínicio não pode ser depois do término",
            RuntimeException.class,
            () -> {
                final DataHora agora = new DataHoraOf();
                final DataHora agoraMais1Minuto = new DataHoraOf(
                    agora.dateTime().plusMinutes(1)
                );
                new Restricao(
                    new PeriodoOf(agoraMais1Minuto, agora)
                ).minutos();
            }
        );
    }
}
