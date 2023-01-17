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
package com.github.fabriciofx.mandacarupark.regra;

import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Regra;
import com.github.fabriciofx.mandacarupark.Regras;
import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.db.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.periodo.PeriodoOf;
import com.github.fabriciofx.mandacarupark.regras.RegrasOf;
import org.junit.Assert;
import org.junit.Test;

public final class TestMensalistaSql {
    @Test
    public void avalie() throws Exception {
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
            final Regra regra = new MensalistaSql(
                session,
                new DinheiroOf("50.00")
            );
            Assert.assertTrue(
                regra.avalie(
                    new Uuid("e4c1f93a-a00d-4ae2-b3f9-2bdc2cbe23e7"),
                    new PeriodoOf(
                        new DataHoraOf("05/01/2023 10:21:36"),
                        new DataHoraOf("05/01/2023 12:27:14")
                    )
                )
            );
        }
    }

    @Test
    public void avalieComOutrasRegras() throws Exception {
        final Id id = new Uuid("e4c1f93a-a00d-4ae2-b3f9-2bdc2cbe23e7");
        final Periodo periodo = new PeriodoOf(
            new DataHoraOf("05/01/2023 10:21:36"),
            new DataHoraOf("05/01/2023 12:27:14")
        );
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
            final Regras regras = new RegrasOf(
                new Tolerancia(),
                new DomingoGratis(),
                new MensalistaSql(
                    session,
                    new DinheiroOf("50.00")
                ),
                new ValorFixo(new DinheiroOf("8.00"))
            );
            Assert.assertEquals(
                new DinheiroOf("50.00"),
                regras.regra(id, periodo, new Cortesia()).valor(id, periodo)
            );
        }
    }
}
