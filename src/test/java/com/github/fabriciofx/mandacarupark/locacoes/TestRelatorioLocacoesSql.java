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
package com.github.fabriciofx.mandacarupark.locacoes;

import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.server.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.periodo.PeriodoOf;
import com.jcabi.matchers.XhtmlMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import java.time.LocalDateTime;

public final class TestRelatorioLocacoesSql {
    @Test
    public void relatorio() throws Exception {
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
            MatcherAssert.assertThat(
                XhtmlMatchers.xhtml(
                    new RelatorioXml(
                        new LocacoesSql(session),
                        new PeriodoOf(
                            LocalDateTime.of(2022, 7, 21, 12, 1),
                            LocalDateTime.of(2022, 7, 21, 17, 12)
                        )
                    ).conteudo()
                ),
                XhtmlMatchers.hasXPaths(
                    "/locacoes/locacao/placa[text()='ABC1234']",
                    "/locacoes/locacao/entrada[text()='21/07/2022 12:01:15']",
                    "/locacoes/locacao/saida[text()='21/07/2022 17:11:12']",
                    "/locacoes/locacao/valor[text()='R$ 5,00']",
                    "/locacoes/locacao/placa[text()='DEF5678']",
                    "/locacoes/locacao/entrada[text()='21/07/2022 12:05:38']",
                    "/locacoes/locacao/saida[text()='21/07/2022 14:06:31']",
                    "/locacoes/locacao/valor[text()='R$ 5,00']",
                    "/locacoes/locacao/placa[text()='GHI9012']",
                    "/locacoes/locacao/entrada[text()='21/07/2022 12:06:51']",
                    "/locacoes/locacao/saida[text()='21/07/2022 13:11:23']",
                    "/locacoes/locacao/valor[text()='R$ 5,00']",
                    "/locacoes/total[text()='R$ 15,00']"
                )
            );
        }
    }
}
