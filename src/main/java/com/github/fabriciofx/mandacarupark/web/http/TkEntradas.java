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

import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.entradas.EntradasSql;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsHtml;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class TkEntradas implements Take {
    private final Session session;

    public TkEntradas(final Session session) {
        this.session = session;
    }

    @Override
    public Response act(final Request req) throws IOException {
        final InputStream input = TkEntradas.class.getClassLoader()
            .getResourceAsStream("webapp/entradas.tpl");
        String content = new String(
            input.readAllBytes(),
            StandardCharsets.UTF_8
        );
        final Entradas entradas = new EntradasSql(session);
        final StringBuilder sb = new StringBuilder();
        for (final Entrada entrada : entradas) {
            sb.append(
                new Sprintf(
                    "<tr>\n<td>%s</td>\n<td>%s</td>\n<td>%s</td>\n</tr>\n",
                    entrada.id().toString(),
                    entrada.sobre().get("placa").toString(),
                    entrada.sobre().get("dataHora").toString()
                ).asString()
            );
        }
        content = content.replaceAll("\\$\\{entradas}", sb.toString());
        final InputStream body = new ByteArrayInputStream(content.getBytes());
        return new RsHtml(body);
    }
}
