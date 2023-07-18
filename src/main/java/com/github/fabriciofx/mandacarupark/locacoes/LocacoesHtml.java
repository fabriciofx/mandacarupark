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

import com.github.fabriciofx.mandacarupark.Html;
import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.db.pagination.Pages;
import com.github.fabriciofx.mandacarupark.locacao.LocacaoHtml;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LocacoesHtml implements Locacoes, Html {
    private final Locacoes origin;
    private final int limit;
    private final int number;
    private final Periodo periodo;
    private final Template template;

    public LocacoesHtml(
        final Locacoes locacoes,
        final int limit,
        final int number,
        final Periodo periodo,
        final Template template
    ) {
        this.origin = locacoes;
        this.limit = limit;
        this.number = number;
        this.periodo = periodo;
        this.template = template;
    }

    @Override
    public Pages<Locacao> pages(final int limit, final Periodo periodo) {
        return this.origin.pages(limit, periodo);
    }

    @Override
    public String html() {
        final String regex = "\\$\\{ls\\.begin}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ls\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(this.template.asString());
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Locacao locacao : this.origin.pages(this.limit, this.periodo).page(this.number).content()) {
                Template page = this.template.create(matcher.group(1));
                page = this.template.create(new LocacaoHtml(locacao, page).html());
                sb.append(page.asString());
            }
        }
        return this.template.asString().replaceAll(
            regex,
            Matcher.quoteReplacement(sb.toString())
        );
    }
}
