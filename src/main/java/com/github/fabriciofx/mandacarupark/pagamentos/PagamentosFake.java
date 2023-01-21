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
package com.github.fabriciofx.mandacarupark.pagamentos;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.media.PageTemplate;
import com.github.fabriciofx.mandacarupark.pagamento.PagamentoFake;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PagamentosFake implements Pagamentos {
    private final Map<Id, Pagamento> itens;

    public PagamentosFake(final Pagamento... pagamentos) {
        this.itens = new HashMap<>();
        for (final Pagamento pagamento : pagamentos) {
            this.itens.put(pagamento.id(), pagamento);
        }
    }

    public PagamentosFake() {
        this(new HashMap<>());
    }

    public PagamentosFake(final Map<Id, Pagamento> itens) {
        this.itens = itens;
    }

    @Override
    public Pagamento pagamento(
        final Ticket ticket,
        final DataHora dataHora,
        final Dinheiro valor
    ) {
        final Pagamento evento = new PagamentoFake(
            ticket.id(),
            dataHora,
            valor
        );
        this.itens.put(ticket.id(), evento);
        return evento;
    }

    @Override
    public List<Pagamento> procura(final Id id) {
        final List<Pagamento> list = new ArrayList<>(0);
        if (this.itens.containsKey(id)) {
            list.add(this.itens.get(id));
        }
        return list;
    }

    @Override
    public int quantidade() {
        return this.itens.size();
    }

    @Override
    public Media print(final Media media) {
        final String regex = "\\$\\{ps\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ps\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(new String(media.bytes()));
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Pagamento pagamento : this) {
                Media page = new PageTemplate(matcher.group(1));
                page = new PageTemplate(pagamento.print(page));
                sb.append(new String(page.bytes()));
            }
        }
        return new PageTemplate(
            new String(media.bytes()).replaceAll(
                regex,
                Matcher.quoteReplacement(sb.toString())
            )
        );
    }

    @Override
    public Iterator<Pagamento> iterator() {
        return this.itens.values().iterator();
    }
}
