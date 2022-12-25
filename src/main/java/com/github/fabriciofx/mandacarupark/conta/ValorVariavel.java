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
package com.github.fabriciofx.mandacarupark.conta;

import com.github.fabriciofx.mandacarupark.Conta;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import java.math.BigDecimal;

public final class ValorVariavel implements Conta {
    private final Dinheiro quatroHoras;
    private final Dinheiro horaAdicional;

    public ValorVariavel(
        final Dinheiro quatroHoras,
        final Dinheiro horaAdicional
    ) {
        this.quatroHoras = quatroHoras;
        this.horaAdicional = horaAdicional;
    }

    @Override
    public boolean avalie(final Periodo periodo) {
        return true;
    }

    @Override
    public Dinheiro valor(final Periodo periodo) {
        long tempo = periodo.minutos() - 240;
        long horas = 4;
        while (tempo > 0) {
            tempo = tempo - 60;
            horas++;
        }
        final BigDecimal excedente = new BigDecimal(horas - 4);
        return new DinheiroOf(
            this.horaAdicional
                .quantia()
                .multiply(excedente)
                .add(this.quatroHoras.quantia())
        );
    }
}
