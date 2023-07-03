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
package com.github.fabriciofx.mandacarupark.saida;

import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.media.MapMedia;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.server.ServerH2;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
import com.jcabi.matchers.XhtmlMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;

public final class TestSaida {
    @Test
    public void sobreFake() {
        final Saida saida = new SaidaFake(
            new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
            new PlacaOf("ABC1234"),
            new DataHoraOf("21/07/2022 17:11:12")
        );
        Assert.assertEquals(
            "8c878e6f-ee13-4a37-a208-7510c2638944",
            saida.sobre(new MapMedia()).get("id").toString()
        );
        Assert.assertEquals(
            "ABC1234",
            saida.sobre(new MapMedia()).get("placa").toString()
        );
        Assert.assertEquals(
            "21/07/2022 17:11:12",
            saida.sobre(new MapMedia()).get("dataHora").toString()
        );
    }

    @Test
    public void printFake() {
        final String html = "<html><body><table><thead><td>Id</td>" +
            "<td>Placa</td><td>Data/Hora</td></thead><tbody>" +
            "<td>${id}</td><td>${placa}</td><td>${dataHora}</td>" +
            "</tbody></table></body></html>";
        final Saida saida = new SaidaFake(
            new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
            new PlacaOf("ABC1234"),
            new DataHoraOf("21/07/2022 17:11:12")
        );
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                saida.print(new HtmlTemplate(html))
            ),
            XhtmlMatchers.hasXPaths(
                "/html/body/table/tbody/td[text()='8c878e6f-ee13-4a37-a208-7510c2638944']",
                "/html/body/table/tbody/td[text()='ABC1234']",
                "/html/body/table/tbody/td[text()='21/07/2022 17:11:12']"
            )
        );
    }

    @Test
    public void sobreSql() throws Exception {
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
            final Saida saida = new SaidaSql(
                session,
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944")
            );
            Assert.assertEquals(
                "8c878e6f-ee13-4a37-a208-7510c2638944",
                saida.sobre(new MapMedia()).get("id").toString()
            );
            Assert.assertEquals(
                "ABC1234",
                saida.sobre(new MapMedia()).get("placa").toString()
            );
            Assert.assertEquals(
                "21/07/2022 17:11:12",
                saida.sobre(new MapMedia()).get("dataHora").toString()
            );
        }
    }

    @Test
    public void printSql() throws Exception {
        final String html = "<html><body><table><thead><td>Id</td>" +
            "<td>Placa</td><td>Data/Hora</td></thead><tbody>" +
            "<td>${id}</td><td>${placa}</td><td>${dataHora}</td>" +
            "</tbody></table></body></html>";
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
            final Saida saida = new SaidaSql(
                session,
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944")
            );
            MatcherAssert.assertThat(
                XhtmlMatchers.xhtml(
                    saida.print(new HtmlTemplate(html))
                ),
                XhtmlMatchers.hasXPaths(
                    "/html/body/table/tbody/td[text()='8c878e6f-ee13-4a37-a208-7510c2638944']",
                    "/html/body/table/tbody/td[text()='ABC1234']",
                    "/html/body/table/tbody/td[text()='21/07/2022 17:11:12']"
                )
            );
        }
    }
}
