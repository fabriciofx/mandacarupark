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
package com.github.fabriciofx.mandacarupark.entrada;

import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Page;
import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.db.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosFake;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import org.junit.Assert;
import org.junit.Test;

public final class TestEntrada {
    @Test
    public void printFake() {
        final String html = "<html><body><table><thead><td>Id</td>" +
            "<td>Placa</td><td>Data/Hora</td></thead><tbody><td>${e.id}</td>" +
            "<td>${e.placa}</td><td>${e.dataHora}</td></tbody></body></html>";
        final Entrada entrada = new EntradaFake(
            new PagamentosFake(),
            new Uuid("8d48d242-7500-4235-ae7a-d30378e544f2"),
            new PlacaOf("ABC1234"),
            new DataHoraOf("29/12/2022 22:54:19")
        );
        Assert.assertEquals(
            "<html><body><table><thead><td>Id</td><td>Placa</td>" +
                "<td>Data/Hora</td></thead><tbody>" +
                "<td>8d48d242-7500-4235-ae7a-d30378e544f2</td>" +
                "<td>ABC1234</td>" +
                "<td>2022-12-29 22:54:19</td>" +
                "</tbody></body></html>",
            entrada.print(new Page(html), "e")
        );
    }

    @Test
    public void printSql() throws Exception {
        final String html = "<html><body><table><thead><td>Id</td>" +
            "<td>Placa</td><td>Data/Hora</td></thead><tbody><td>${e.id}</td>" +
            "<td>${e.placa}</td><td>${e.dataHora}</td></tbody></body></html>";
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Entrada entrada = new EntradaSql(
                session,
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944")
            );
            Assert.assertEquals(
                "<html><body><table><thead><td>Id</td><td>Placa</td>" +
                    "<td>Data/Hora</td></thead><tbody>" +
                    "<td>8c878e6f-ee13-4a37-a208-7510c2638944</td>" +
                    "<td>ABC1234</td>" +
                    "<td>2022-07-21 12:01:15</td>" +
                    "</tbody></body></html>",
                entrada.print(new Page(html), "e")
            );
        }
    }
}
