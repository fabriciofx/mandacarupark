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
package com.github.fabriciofx.mandacarupark.locacoes;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.db.pagination.Pages;
import com.github.fabriciofx.mandacarupark.db.pagination.pages.PagesFake;
import com.github.fabriciofx.mandacarupark.locacao.LocacaoFake;
import com.github.fabriciofx.mandacarupark.media.MemMedia;
import java.util.ArrayList;
import java.util.List;

public final class LocacoesFake implements Locacoes {
    private final Entradas entradas;
    private final Saidas saidas;
    private final Pagamentos pagamentos;

    public LocacoesFake(final Estacionamento estacionamento) {
        this(
            estacionamento.sobre(new MemMedia()).select("entradas"),
            estacionamento.sobre(new MemMedia()).select("saidas"),
            estacionamento.sobre(new MemMedia()).select("pagamentos")
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
    public Pages<Locacao> pages(int limit, final Periodo periodo) {
        final List<Locacao> itens = new ArrayList<>();
        for (final Entrada entrada : this.entradas.pages(limit).page(0).content()) {
            final Media media = entrada.sobre(new MemMedia());
            final DataHora dataHora = media.select("dataHora");
            if (periodo.contem(dataHora)) {
                final List<Saida> lista = this.saidas.procura(entrada.id());
                if (!lista.isEmpty()) {
                    final Saida saida = lista.get(0);
                    final List<Pagamento> pagamento = this.pagamentos.procura(
                        entrada.id()
                    );
                    itens.add(
                        new LocacaoFake(
                            entrada.id(),
                            media.select("placa"),
                            media.select("dataHora"),
                            saida.sobre(new MemMedia()).select("dataHora"),
                            pagamento.get(0).sobre(new MemMedia())
                                .select("valor")
                        )
                    );
                }
            }
        }
        return new PagesFake<>(limit, itens);
    }
}
