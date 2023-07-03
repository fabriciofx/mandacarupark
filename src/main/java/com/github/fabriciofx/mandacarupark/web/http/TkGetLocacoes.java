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
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.media.MapMedia;
import com.github.fabriciofx.mandacarupark.periodo.PeriodoOf;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.form.RqFormBase;
import org.takes.rs.RsHtml;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class TkGetLocacoes implements Take {
    private final Estacionamento estacionamento;

    public TkGetLocacoes(final Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
    }

    @Override
    public Response act(final Request req) throws IOException {
        final Template header = new HtmlTemplate(
            new ResourceAsStream("webapp/header.tpl")
        );
        final Template main = new HtmlTemplate(
            new ResourceAsStream("webapp/locacoes.tpl")
        ).with("header", header);
        final RqFormBase form = new RqFormBase(req);
        final String inicio, termino;
        if (form.names().iterator().hasNext()) {
            inicio = form.param("inicio").iterator().next();
            termino = form.param("termino").iterator().next();
        } else {
            inicio = "01/01/2022 00:00:00";
            termino = "31/01/2023 23:59:59";
        }
        final Locacoes locacoes = this.estacionamento.sobre(new MapMedia())
            .get("locacoes");
        final InputStream body = new ByteArrayInputStream(
            locacoes.print(
                main,
                new PeriodoOf(
                    new DataHoraOf(inicio),
                    new DataHoraOf(termino)
                )
            ).bytes()
        );
        return new RsHtml(body);
    }
}
