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
package com.github.fabriciofx.mandacarupark.fake;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Saidas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocacoesFake implements Locacoes {
    private final Entradas entradas;
    private final Saidas saidas;
    private final Pagamentos pagamentos;
    private final Periodo periodo;

    public LocacoesFake(
        final Entradas entradas,
        final Saidas saidas,
        final Pagamentos pagamentos,
        final Periodo periodo
    ) {
        this.entradas = entradas;
        this.saidas = saidas;
        this.pagamentos = pagamentos;
        this.periodo = periodo;
    }

    @Override
    public Iterator<Locacao> iterator() {
        final List<Locacao> items = new ArrayList<>();
        for (Entrada entrada : this.entradas) {
            final DataHora dataHora = entrada.sobre().value("dataHora");
            if (this.periodo.contem(dataHora.dateTime())) {
                final Saida saida = this.saidas.procura(entrada.id());
                final Pagamento pagamento = this.pagamentos.procura(
                    entrada.id()
                );
                items.add(
                    new LocacaoFake(
                        entrada.id(),
                        entrada.sobre().value("placa"),
                        entrada.sobre().value("dataHora"),
                        saida.sobre().value("dataHora"),
                        new Dinheiro(pagamento.sobre().get("valor"))
                    )
                );
            }
        }
        return items.iterator();
    }
}
