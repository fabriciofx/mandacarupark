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
package com.github.fabriciofx.mandacarupark.entradas;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Page;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.entrada.EntradaFake;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EntradasFake implements Entradas {
    private final Map<Id, Entrada> itens;
    private final Pagamentos pagamentos;

    public EntradasFake(final Pagamentos pagamentos) {
        this(pagamentos, new HashMap<>());
    }

    public EntradasFake(
        final Pagamentos pagamentos,
        final Entrada... entradas
    ) {
        this.pagamentos = pagamentos;
        this.itens = new HashMap<>();
        for (final Entrada entrada : entradas) {
            this.itens.put(entrada.id(), entrada);
        }
    }

    public EntradasFake(
        final Pagamentos pagamentos,
        final Map<Id, Entrada> itens
    ) {
        this.pagamentos = pagamentos;
        this.itens = itens;
    }

    @Override
    public Entrada entrada(
        final Id id,
        final Placa placa,
        final DataHora dataHora
    ) {
        final Entrada entrada = new EntradaFake(
            this.pagamentos,
            id,
            placa,
            dataHora
        );
        this.itens.put(entrada.id(), entrada);
        return entrada;
    }

    @Override
    public Entrada procura(final Id id) {
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
            for (final Entrada entrada : this) {
                Page pg = new Page(matcher.group(1));
                pg = new Page(entrada.print(pg, "e"));
                sb.append(pg.asString());
            }
        }
        return page.asString().replaceAll(regex, sb.toString());
    }

    @Override
    public Iterator<Entrada> iterator() {
        return this.itens.values().iterator();
    }
}
