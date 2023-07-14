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

import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.media.MapMedia;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import com.github.fabriciofx.mandacarupark.web.HttpPage;
import org.takes.Request;
import org.takes.Response;
import org.takes.facets.forward.RsForward;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsHtml;
import java.io.IOException;
import java.util.List;

public final class SaidaPage implements HttpPage {
    private final Estacionamento estacionamento;

    public SaidaPage(final Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
    }

    @Override
    public Response act(final Request req) throws IOException {
        final RqFormSmart form = new RqFormSmart(req);
        final Placa placa = new PlacaOf(form.single("placa"));
        final Id id = new Uuid(form.single("ticket"));
        final Entradas entradas = this.estacionamento.sobre(new MapMedia())
            .select("entradas");
        final List<Entrada> entrada = entradas.procura(id);
        if (entrada.isEmpty()) {
            throw new RuntimeException(
                new Sprintf(
                    "entrada do ticket %s não encontrada",
                    id.numero()
                ).asString()
            );
        }
        final Ticket ticket = entrada.get(0).ticket();
        try {
            this.estacionamento.saida(ticket, placa, new DataHoraOf());
        } catch (final Exception ex) {
            return new RsHtml(
                new HtmlTemplate(
                    new ResourceAsStream("webapp/erro.html")
                ).with("error", ex.getMessage()).toString()
            );
        }
        return new RsForward("/saidas");
    }
}
