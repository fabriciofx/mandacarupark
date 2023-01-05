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
package com.github.fabriciofx.mandacarupark.saidas;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Page;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.saida.SaidaFake;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SaidasFake implements Saidas {
    private final Map<Id, Saida> itens;

    public SaidasFake(final Saida... saidas) {
        this.itens = new HashMap<>();
        for (final Saida saida : saidas) {
            this.itens.put(saida.id(), saida);
        }
    }

    public SaidasFake() {
        this(new HashMap<>());
    }

    public SaidasFake(final Map<Id, Saida> itens) {
        this.itens = itens;
    }

    @Override
    public Saida saida(
        final Ticket ticket,
        final Placa placa,
        final DataHora dataHora
    ) {
        final Saida evento = new SaidaFake(ticket.id(), placa, dataHora);
        this.itens.put(evento.id(), evento);
        return evento;
    }

    @Override
    public Saida procura(final Id id) {
        return this.itens.get(id);
    }

    @Override
    public String print(final Page page, final String prefix) {
        final String regex = new Sprintf(
            "\\$\\{%s\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{%s\\.end}",
            prefix,
            prefix
        ).asString();
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(page.asString());
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Saida saida : this) {
                Page pg = new Page(matcher.group(1));
                pg = new Page(saida.print(pg, "s"));
                sb.append(pg.asString());
            }
        }
        return page.asString().replaceAll(regex, sb.toString());
    }

    @Override
    public Iterator<Saida> iterator() {
        return this.itens.values().iterator();
    }
}
