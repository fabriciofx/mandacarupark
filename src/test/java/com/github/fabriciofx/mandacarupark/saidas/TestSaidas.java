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
package com.github.fabriciofx.mandacarupark.saidas;

import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.saida.SaidaFake;
import com.github.fabriciofx.mandacarupark.saida.SaidaHtml;
import com.github.fabriciofx.mandacarupark.server.ServerH2;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
import com.jcabi.matchers.XhtmlMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

@SuppressWarnings("unchecked")
public final class TestSaidas {
    @Test
    public void printFake() {
        final String html = "<html><body><table><thead>" +
            "<td>Id</td><td>Placa</td><td>Data/Hora</td>" +
            "</thead><tbody>${ss.begin}" +
            "<tr><td>${id}</td><td>${placa}</td><td>${dataHora}</td></tr>" +
            "${ss.end}</tbody></table></body></html>";
        final Saidas saidas = new SaidasFake(
            new SaidaFake(
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
                new PlacaOf("ABC1234"),
                new DataHoraOf("2022-07-21 12:01:15")
            ),
            new SaidaFake(
                new Uuid("07d8e078-5b47-4fb5-9fb4-dd2c80dd8036"),
                new PlacaOf("DEF5678"),
                new DataHoraOf("2022-07-21 12:05:38")
            ),
            new SaidaFake(
                new Uuid("4c32b3dd-8636-43c0-9786-4804ca2b73f5"),
                new PlacaOf("GHI9012"),
                new DataHoraOf("2022-07-21 12:06:51")
            )
        );
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                new SaidasHtml(saidas, 3, 0, new HtmlTemplate(html)).html()
            ),
            XhtmlMatchers.hasXPaths(
                "/html/body/table/tbody/tr/td[text()='8c878e6f-ee13-4a37-a208-7510c2638944']",
                "/html/body/table/tbody/tr/td[text()='ABC1234']",
                "/html/body/table/tbody/tr/td[text()='21/07/2022 12:01:15']",
                "/html/body/table/tbody/tr/td[text()='07d8e078-5b47-4fb5-9fb4-dd2c80dd8036']",
                "/html/body/table/tbody/tr/td[text()='DEF5678']",
                "/html/body/table/tbody/tr/td[text()='21/07/2022 12:05:38']",
                "/html/body/table/tbody/tr/td[text()='4c32b3dd-8636-43c0-9786-4804ca2b73f5']",
                "/html/body/table/tbody/tr/td[text()='GHI9012']",
                "/html/body/table/tbody/tr/td[text()='21/07/2022 12:06:51']"
            )
        );
    }

    @Test
    public void printSql() throws Exception {
        final String html = "<html><body><table><thead>" +
            "<td>Id</td><td>Placa</td><td>Data/Hora</td>" +
            "</thead><tbody>${ss.begin}" +
            "<tr><td>${id}</td><td>${placa}</td><td>${dataHora}</td></tr>" +
            "${ss.end}</tbody></table></body></html>";
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
            final Saidas saidas = new SaidasSql(session);
            MatcherAssert.assertThat(
                XhtmlMatchers.xhtml(
                    new SaidasHtml(saidas, 3, 0, new HtmlTemplate(html)).html()
                ),
                XhtmlMatchers.hasXPaths(
                    "/html/body/table/tbody/tr/td[text()='4c32b3dd-8636-43c0-9786-4804ca2b73f5']",
                    "/html/body/table/tbody/tr/td[text()='GHI9012']",
                    "/html/body/table/tbody/tr/td[text()='21/07/2022 13:11:23']",
                    "/html/body/table/tbody/tr/td[text()='07d8e078-5b47-4fb5-9fb4-dd2c80dd8036']",
                    "/html/body/table/tbody/tr/td[text()='DEF5678']",
                    "/html/body/table/tbody/tr/td[text()='21/07/2022 14:06:31']",
                    "/html/body/table/tbody/tr/td[text()='8c878e6f-ee13-4a37-a208-7510c2638944']",
                    "/html/body/table/tbody/tr/td[text()='ABC1234']",
                    "/html/body/table/tbody/tr/td[text()='21/07/2022 17:11:12']"
                )
            );
        }
    }

    @Test
    public void fakePages() {
        final String html = "<html><body><table><thead>" +
            "<td>Id</td><td>Placa</td><td>Data/Hora</td>" +
            "</thead><tbody>"+
            "<tr><td>${id}</td><td>${placa}</td><td>${dataHora}</td></tr>" +
            "</tbody></table></body></html>";
        final Saidas saidas = new SaidasFake(
            new SaidaFake(
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
                new PlacaOf("ABC1234"),
                new DataHoraOf("2022-07-21 12:01:15")
            ),
            new SaidaFake(
                new Uuid("07d8e078-5b47-4fb5-9fb4-dd2c80dd8036"),
                new PlacaOf("DEF5678"),
                new DataHoraOf("2022-07-21 12:05:38")
            ),
            new SaidaFake(
                new Uuid("4c32b3dd-8636-43c0-9786-4804ca2b73f5"),
                new PlacaOf("GHI9012"),
                new DataHoraOf("2022-07-21 12:06:51")
            )
        );
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                new SaidaHtml(
                    saidas.pages(1).page(0).content().get(0),
                    new HtmlTemplate(html)
                ).html()
            ),
            XhtmlMatchers.hasXPaths(
                "/html/body/table/tbody/tr/td[text()='07d8e078-5b47-4fb5-9fb4-dd2c80dd8036']",
                "/html/body/table/tbody/tr/td[text()='DEF5678']",
                "/html/body/table/tbody/tr/td[text()='21/07/2022 12:05:38']"
            )
        );
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                new SaidaHtml(
                    saidas.pages(1).page(1).content().get(0),
                    new HtmlTemplate(html)
                ).html()
            ),
            XhtmlMatchers.hasXPaths(
                "/html/body/table/tbody/tr/td[text()='8c878e6f-ee13-4a37-a208-7510c2638944']",
                "/html/body/table/tbody/tr/td[text()='ABC1234']",
                "/html/body/table/tbody/tr/td[text()='21/07/2022 12:01:15']"
            )
        );
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                new SaidaHtml(
                    saidas.pages(1).page(2).content().get(0),
                    new HtmlTemplate(html)
                ).html()
            ),
            XhtmlMatchers.hasXPaths(
                "/html/body/table/tbody/tr/td[text()='4c32b3dd-8636-43c0-9786-4804ca2b73f5']",
                "/html/body/table/tbody/tr/td[text()='GHI9012']",
                "/html/body/table/tbody/tr/td[text()='21/07/2022 12:06:51']"
            )
        );
    }

    @Test
    public void sqlPages() throws Exception {
        final String html = "<html><body><table><thead>" +
            "<td>Id</td><td>Placa</td><td>Data/Hora</td>" +
            "</thead><tbody>"+
            "<tr><td>${id}</td><td>${placa}</td><td>${dataHora}</td></tr>" +
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
            final Saidas saidas = new SaidasSql(session);
            MatcherAssert.assertThat(
                XhtmlMatchers.xhtml(
                    new SaidaHtml(
                        saidas.pages(1).page(0).content().get(0),
                        new HtmlTemplate(html)
                    ).html()
                ),
                XhtmlMatchers.hasXPaths(
                    "/html/body/table/tbody/tr/td[text()='4c32b3dd-8636-43c0-9786-4804ca2b73f5']",
                    "/html/body/table/tbody/tr/td[text()='GHI9012']",
                    "/html/body/table/tbody/tr/td[text()='21/07/2022 13:11:23']"
                )
            );
            MatcherAssert.assertThat(
                XhtmlMatchers.xhtml(
                    new SaidaHtml(
                        saidas.pages(1).page(1).content().get(0),
                        new HtmlTemplate(html)
                    ).html()
                ),
                XhtmlMatchers.hasXPaths(
                    "/html/body/table/tbody/tr/td[text()='07d8e078-5b47-4fb5-9fb4-dd2c80dd8036']",
                    "/html/body/table/tbody/tr/td[text()='DEF5678']",
                    "/html/body/table/tbody/tr/td[text()='21/07/2022 14:06:31']"
                )
            );
            MatcherAssert.assertThat(
                XhtmlMatchers.xhtml(
                    new SaidaHtml(
                        saidas.pages(1).page(2).content().get(0),
                        new HtmlTemplate(html)
                    ).html()
                ),
                XhtmlMatchers.hasXPaths(
                    "/html/body/table/tbody/tr/td[text()='8c878e6f-ee13-4a37-a208-7510c2638944']",
                    "/html/body/table/tbody/tr/td[text()='ABC1234']",
                    "/html/body/table/tbody/tr/td[text()='21/07/2022 17:11:12']"
                )
            );
        }
    }
}