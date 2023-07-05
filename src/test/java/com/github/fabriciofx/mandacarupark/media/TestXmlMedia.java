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
package com.github.fabriciofx.mandacarupark.media;

import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.jcabi.matchers.XhtmlMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;

public final class TestXmlMedia {
    @Test
    public void xml() {
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                new XmlMedia("locacoes")
                    .begin("locacao")
                    .with("placa", new PlacaOf("ABC1234"))
                    .with("entrada", new DataHoraOf("03/07/2023 10:11:23"))
                    .with("saida", new DataHoraOf("03/07/2023 17:28:45"))
                    .with("valor", new DinheiroOf("8.00"))
                    .end("locacao")
                    .with("total", new DinheiroOf("8.00"))
                    .end("locacoes")
            ),
            XhtmlMatchers.hasXPaths(
                "/locacoes/locacao/placa[text()='ABC1234']",
                "/locacoes/locacao/entrada[text()='03/07/2023 10:11:23']",
                "/locacoes/locacao/saida[text()='03/07/2023 17:28:45']",
                "/locacoes/locacao/valor[text()='R$ 8,00']",
                "/locacoes/total[text()='R$ 8,00']"
            )
        );
    }

    @Test
    public void select() {
        Assert.assertEquals(
            "ABC1234",
            new XmlMedia("locacoes")
                .begin("locacao")
                .with("placa", new PlacaOf("ABC1234"))
                .with("entrada", new DataHoraOf("03/07/2023 10:11:23"))
                .with("saida", new DataHoraOf("03/07/2023 17:28:45"))
                .with("valor", new DinheiroOf("8.00"))
                .end("locacao")
                .with("total", new DinheiroOf("8.00"))
                .end("locacoes")
                .select("/locacoes/locacao/placa/text()")
        );
    }
}
