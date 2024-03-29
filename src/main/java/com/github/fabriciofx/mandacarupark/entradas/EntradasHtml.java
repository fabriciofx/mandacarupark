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
import com.github.fabriciofx.mandacarupark.Html;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.entrada.EntradaHtml;
import com.github.fabriciofx.mandacarupark.db.pagination.Pages;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EntradasHtml implements Entradas, Html {
    private final Entradas origin;
    private final int limit;
    private final int number;
    private final Template template;

    public EntradasHtml(
        final Entradas entradas,
        final int limit,
        final int number,
        final Template template
    ) {
        this.origin = entradas;
        this.limit = limit;
        this.number = number;
        this.template = template;
    }

    @Override
    public Entrada entrada(
        final Id id,
        final Placa placa,
        final DataHora dataHora
    ) {
        return this.origin.entrada(id, placa, dataHora);
    }

    @Override
    public List<Entrada> procura(final Id id) {
        return this.origin.procura(id);
    }

    @Override
    public Pages<Entrada> pages(final int limit) {
        return this.origin.pages(limit);
    }

    @Override
    public String html() {
        final String regex = "\\$\\{es\\.begin}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{es\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(this.template.asString());
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Entrada entrada : this.origin.pages(this.limit).page(this.number).content()) {
                Template page = this.template.create(matcher.group(1));
                page = this.template.create(new EntradaHtml(entrada, page).html());
                sb.append(page.asString());
            }
        }
        return this.template.asString().replaceAll(
            regex,
            Matcher.quoteReplacement(sb.toString())
        );
    }
}
