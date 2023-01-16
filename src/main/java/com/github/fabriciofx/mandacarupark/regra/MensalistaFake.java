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
package com.github.fabriciofx.mandacarupark.regra;

import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Regra;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import java.util.HashMap;
import java.util.Map;

public final class MensalistaFake implements Regra {
    private final Map<Id, Periodo> mensalistas;
    private final Dinheiro valor;

    public MensalistaFake(
        final Dinheiro valor,
        final Map.Entry<Id, Periodo>... mensalistas
    ) {
        this.valor = valor;
        this.mensalistas = new HashMap<>();
        for (final Map.Entry<Id, Periodo> mensalista : mensalistas) {
            this.mensalistas.put(mensalista.getKey(), mensalista.getValue());
        }
    }

    @Override
    public boolean avalie(final Id id, final Periodo periodo) {
        return this.mensalistas.keySet().contains(id);
    }

    @Override
    public Dinheiro valor(final Id id, final Periodo periodo) {
        final Dinheiro val;
        if (this.mensalistas.get(id).contem(periodo)) {
            val = this.valor;
        } else {
            val = new DinheiroOf("0.00");
        }
        return val;
    }
}
