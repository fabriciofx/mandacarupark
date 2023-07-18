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
package com.github.fabriciofx.mandacarupark.web.page;

import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.media.MapMedia;
import com.github.fabriciofx.mandacarupark.saidas.SaidasHtml;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
import com.github.fabriciofx.mandacarupark.web.HttpPage;
import org.takes.Request;
import org.takes.Response;
import org.takes.rq.RqHref;
import org.takes.rs.RsHtml;
import java.io.IOException;

@SuppressWarnings("unchecked")
public final class SaidasPage implements HttpPage {
    private final Estacionamento estacionamento;

    public SaidasPage(final Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
    }

    @Override
    public Response act(final Request req) throws IOException {
        final Saidas saidas = this.estacionamento.sobre(new MapMedia())
            .select("saidas");
        final int number = Integer.parseInt(
            new RqHref.Smart(req).single("page", "1")
        );
        final int limit = Integer.parseInt(
            new RqHref.Smart(req).single("limit", "1")
        );
        final Template main = new HtmlTemplate(
            new ResourceAsStream("webapp/saidas.tpl")
        ).with(
            "header",
            new HtmlTemplate(
                new ResourceAsStream("webapp/header.tpl")
            ).asString()
        ).with(
            "pagination",
            new HtmlTemplate(
                new Pagination<>(
                    saidas.pages(limit),
                    number,
                    limit,
                    "http://localhost:8080/saidas"
                )
            ).asString()
        );
        return new RsHtml(
            new SaidasHtml(
                saidas,
                limit,
                number - 1,
                main
            ).html()
        );
    }
}
