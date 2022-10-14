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
package com.github.fabriciofx.mandacarupark.contas;

import com.github.fabriciofx.mandacarupark.Conta;
import com.github.fabriciofx.mandacarupark.Contas;
import com.github.fabriciofx.mandacarupark.Periodo;
import java.util.Arrays;
import java.util.List;

public final class ContasOf implements Contas {
    private final List<Conta> itens;

    public ContasOf(final Conta... itens) {
        this(Arrays.asList(itens));
    }

    public ContasOf(final List<Conta> itens) {
        this.itens = itens;
    }

    @Override
    public Conta conta(final Periodo periodo, final Conta def) {
        for (final Conta item : this.itens) {
            if (item.avalie(periodo)) {
                return item;
            }
        }
        return def;
    }
}
