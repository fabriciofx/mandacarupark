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
package com.github.fabriciofx.mandacarupark.web.http;

import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.locacoes.LocacoesSql;
import com.github.fabriciofx.mandacarupark.periodo.PeriodoOf;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsHtml;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

public final class TkLocacoes implements Take {
    private final Session session;

    public TkLocacoes(final Session session) {
        this.session = session;
    }

    @Override
    public Response act(final Request req) throws IOException {
        final InputStream input = TkLocacoes.class.getClassLoader()
            .getResourceAsStream("webapp/locacoes.tpl");
        String content = new String(
            input.readAllBytes(),
            StandardCharsets.UTF_8
        );
        final Locacoes locacoes = new LocacoesSql(
            session,
            new PeriodoOf(
                new DataHoraOf("01/01/2022 08:00:00"),
                new DataHoraOf("05/01/2023 05:39:00")
            )
        );
        final StringBuilder sb = new StringBuilder();
        for (final Locacao locacao : locacoes) {
            sb.append(
                new Sprintf(
                    "<tr>\n<td>%s</td>\n<td>%s</td>\n<td>%s</td>\n<td>%s</td>\n</tr>\n",
                    locacao.sobre().get("placa").toString(),
                    locacao.sobre().get("entrada").toString(),
                    locacao.sobre().get("saida").toString(),
                    locacao.sobre().get("valor").toString()
                ).asString()
            );
        }
        content = content.replaceAll(
            "\\$\\{locacoes}",
            Matcher.quoteReplacement(sb.toString())
        );
        final InputStream body = new ByteArrayInputStream(content.getBytes());
        return new RsHtml(body);
    }
}
