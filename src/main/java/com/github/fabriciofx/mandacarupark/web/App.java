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
package com.github.fabriciofx.mandacarupark.web;

import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2File;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.entradas.EntradasSql;
import com.github.fabriciofx.mandacarupark.estacionamento.EstacionamentoSql;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosSql;
import com.github.fabriciofx.mandacarupark.regra.DomingoGratis;
import com.github.fabriciofx.mandacarupark.regra.MensalistaSql;
import com.github.fabriciofx.mandacarupark.regra.Tolerancia;
import com.github.fabriciofx.mandacarupark.regra.ValorFixo;
import com.github.fabriciofx.mandacarupark.regras.RegrasOf;
import com.github.fabriciofx.mandacarupark.saidas.SaidasSql;
import com.github.fabriciofx.mandacarupark.server.ServerH2;
import com.github.fabriciofx.mandacarupark.server.Servers;
import com.github.fabriciofx.mandacarupark.server.WebServer;
import com.github.fabriciofx.mandacarupark.server.WebServerProcess;
import com.github.fabriciofx.mandacarupark.web.browser.Browsers;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

public final class App {
    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int port = 8080;
        final CountDownLatch cdl = new CountDownLatch(1);
        final Session session = new NoAuth(new H2File("mandacarupark"));
        final Estacionamento estacionamento = new EstacionamentoSql(
            session,
            new EntradasSql(session),
            new SaidasSql(session),
            new PagamentosSql(session),
            new RegrasOf(
                new Tolerancia(),
                new DomingoGratis(),
                new MensalistaSql(session, new DinheiroOf("50.00")),
                new ValorFixo(new DinheiroOf("8.00"))
            )
        );
        new Servers(
            new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            ),
            new WebServer(
                new WebServerProcess(estacionamento, port, cdl)
            )
        ).start();
        // TODO: remove temporal coupling between server and browser
        final Browser browser = new Browsers(cdl).browser();
        browser.open(new URI(String.format("http://%s:%d", host, port)));
    }
}
