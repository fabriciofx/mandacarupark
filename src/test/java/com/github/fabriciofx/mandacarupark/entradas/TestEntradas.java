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
package com.github.fabriciofx.mandacarupark.entradas;

import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Page;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.entrada.EntradaFake;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosFake;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.jcabi.matchers.XhtmlMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class TestEntradas {
    @Test
    public void printFake() {
        final String html = "<html><body><table><thead>" +
            "<td>Id</td><td>Placa</td><td>Data/Hora</td>" +
            "</thead><tbody>" +
            "<tr><td>${e0.id}</td><td>${e0.placa}</td><td>${e0.dataHora}</td></tr>" +
            "<tr><td>${e1.id}</td><td>${e1.placa}</td><td>${e1.dataHora}</td></tr>" +
            "<tr><td>${e2.id}</td><td>${e2.placa}</td><td>${e2.dataHora}</td></tr>" +
            "</tbody></table></body></html>";
        final Entradas entradas = new EntradasFake(
            new PagamentosFake(),
            new EntradaFake(
                new PagamentosFake(),
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
                new PlacaOf("ABC1234"),
                new DataHoraOf("2022-07-21 12:01:15")
            ),
            new EntradaFake(
                new PagamentosFake(),
                new Uuid("07d8e078-5b47-4fb5-9fb4-dd2c80dd8036"),
                new PlacaOf("DEF5678"),
                new DataHoraOf("2022-07-21 12:05:38")
            ),
            new EntradaFake(
                new PagamentosFake(),
                new Uuid("4c32b3dd-8636-43c0-9786-4804ca2b73f5"),
                new PlacaOf("GHI9012"),
                new DataHoraOf("2022-07-21 12:06:51")
            )
        );
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                entradas.print(new Page(html), "e")
            ),
            XhtmlMatchers.hasXPaths(
                "/html/body/table/tbody/tr/td[text()='8c878e6f-ee13-4a37-a208-7510c2638944']",
                "/html/body/table/tbody/tr/td[text()='ABC1234']",
                "/html/body/table/tbody/tr/td[text()='2022-07-21 12:01:15']",
                "/html/body/table/tbody/tr/td[text()='07d8e078-5b47-4fb5-9fb4-dd2c80dd8036']",
                "/html/body/table/tbody/tr/td[text()='DEF5678']",
                "/html/body/table/tbody/tr/td[text()='2022-07-21 12:05:38']",
                "/html/body/table/tbody/tr/td[text()='4c32b3dd-8636-43c0-9786-4804ca2b73f5']",
                "/html/body/table/tbody/tr/td[text()='GHI9012']",
                "/html/body/table/tbody/tr/td[text()='2022-07-21 12:06:51']"
            )
        );
    }
}
