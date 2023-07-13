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

import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Html;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.locacao.LocacaoHtml;
import com.github.fabriciofx.mandacarupark.periodo.PeriodoOf;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LocacoesHtml implements Locacoes, Html {
    private final Locacoes locacoes;
    private final Template template;

    public LocacoesHtml(final Locacoes locacoes, final Template template) {
        this.locacoes = locacoes;
        this.template = template;
    }

    @Override
    public List<Locacao> procura(final Periodo periodo) {
        return this.locacoes.procura(periodo);
    }

    @Override
    public Media sobre(final Media media) {
        return this.locacoes.sobre(media);
    }

    @Override
    public String html() {
        final String regex = "\\$\\{ls\\.begin}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ls\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(this.template.asString());
        final StringBuilder sb = new StringBuilder();
        final Periodo periodo = new PeriodoOf(
            new DataHoraOf("01/01/2022 00:00:00"),
            new DataHoraOf("31/12/2023 23:59:59")
        );
        while (matcher.find()) {
            for (final Locacao locacao : this.locacoes.procura(periodo)) {
                Template page = this.template.create(matcher.group(1));
                page = this.template.create(
                    new LocacaoHtml(locacao, page).html()
                );
                sb.append(new String(page.bytes()));
            }
        }
        return this.template.asString().replaceAll(regex, sb.toString());
    }
}
