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
package com.github.fabriciofx.mandacarupark.locacoes;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.locacao.LocacaoFake;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class LocacoesFake implements Locacoes {
    private final Entradas entradas;
    private final Saidas saidas;
    private final Pagamentos pagamentos;

    public LocacoesFake(final Estacionamento estacionamento) {
        this(
            estacionamento.sobre().get("entradas"),
            estacionamento.sobre().get("saidas"),
            estacionamento.sobre().get("pagamentos")
        );
    }

    public LocacoesFake(
        final Entradas entradas,
        final Saidas saidas,
        final Pagamentos pagamentos
    ) {
        this.entradas = entradas;
        this.saidas = saidas;
        this.pagamentos = pagamentos;
    }

    @Override
    public Iterable<Locacao> locacoes(final Periodo periodo) {
        final List<Locacao> itens = new ArrayList<>();
        for (Entrada entrada : this.entradas) {
            final DataHora dataHora = entrada.sobre().get("dataHora");
            if (periodo.contem(dataHora)) {
                final Saida saida = this.saidas.procura(entrada.id());
                final List<Pagamento> pagamento = this.pagamentos.procura(
                    entrada.id()
                );
                itens.add(
                    new LocacaoFake(
                        entrada.id(),
                        entrada.sobre().get("placa"),
                        entrada.sobre().get("dataHora"),
                        saida.sobre().get("dataHora"),
                        pagamento.get(0).sobre().get("valor")
                    )
                );
            }
        }
        return itens;
    }
}
