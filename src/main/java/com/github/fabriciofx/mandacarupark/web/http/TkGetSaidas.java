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
package com.github.fabriciofx.mandacarupark.web.http;

import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.media.HtmlTemplate;
import com.github.fabriciofx.mandacarupark.print.SaidasPrint;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref;
import org.takes.rs.RsHtml;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("unchecked")
public final class TkGetSaidas implements Take {
    private final Estacionamento estacionamento;

    public TkGetSaidas(final Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
    }

    @Override
    public Response act(final Request req) throws IOException {
        final Saidas saidas = this.estacionamento.sobre().get("saidas");
        final int number = Integer.parseInt(
            new RqHref.Smart(req).single("page", "1")
        );
        final Media main = new HtmlTemplate(
            new ResourceAsStream("webapp/saidas.tpl")
        ).with(
            "header",
            new HtmlTemplate(new ResourceAsStream("webapp/header.tpl"))
        ).with(
            "footer",
            new HtmlTemplate(
                new Footer<>(saidas.pages(1), number, "http://localhost:8080/saidas")
            )
        );
        final InputStream body = new ByteArrayInputStream(
            new SaidasPrint<>(saidas.pages(1).page(number - 1)).print(main).bytes()
        );
        return new RsHtml(body);
    }
}
