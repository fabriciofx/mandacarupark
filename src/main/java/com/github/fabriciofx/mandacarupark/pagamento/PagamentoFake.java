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
package com.github.fabriciofx.mandacarupark.pagamento;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Pagamento;

public final class PagamentoFake implements Pagamento {
    private final Id id;
    private final DataHora dataHora;
    private final Dinheiro valor;

    public PagamentoFake(
        final Id id,
        final DataHora dataHora,
        final Dinheiro valor
    ) {
        this.id = id;
        this.dataHora = dataHora;
        this.valor = valor;
    }

    @Override
    public Id id() {
        return this.id;
    }

    @Override
    public Media sobre(final Media media) {
        return media.begin("pagamento")
            .with("id", this.id)
            .with("dataHora", this.dataHora)
            .with("valor", this.valor)
            .end("pagamento");
    }
}
