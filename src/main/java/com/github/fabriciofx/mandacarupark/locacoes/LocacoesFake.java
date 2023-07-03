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
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.locacao.LocacaoFake;
import com.github.fabriciofx.mandacarupark.media.MapMedia;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LocacoesFake implements Locacoes {
    private final Entradas entradas;
    private final Saidas saidas;
    private final Pagamentos pagamentos;

    public LocacoesFake(final Estacionamento estacionamento) {
        this(
            estacionamento.sobre(new MapMedia()).get("entradas"),
            estacionamento.sobre(new MapMedia()).get("saidas"),
            estacionamento.sobre(new MapMedia()).get("pagamentos")
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
    public List<Locacao> procura(final Periodo periodo) {
        final List<Locacao> itens = new ArrayList<>();
        for (Entrada entrada : this.entradas) {
            final Media media = entrada.sobre(new MapMedia());
            final DataHora dataHora = media.get("dataHora");
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
                            media.get("placa"),
                            media.get("dataHora"),
                            saida.sobre(new MapMedia()).get("dataHora"),
                            pagamento.get(0).sobre(new MapMedia()).get("valor")
                        )
                    );
                }
            }
        }
        return itens;
    }

    @Override
    public Template print(final Template template, final Periodo periodo) {
        final String regex = "\\$\\{ls\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ls\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(new String(template.bytes()));
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Locacao locacao : this.procura(periodo)) {
                Template page = new HtmlTemplate(matcher.group(1));
                page = new HtmlTemplate(locacao.print(page));
                sb.append(new String(page.bytes()));
            }
        }
        return new HtmlTemplate(
            new String(template.bytes()).replaceAll(
                regex,
                Matcher.quoteReplacement(sb.toString())
            )
        );
    }
}
