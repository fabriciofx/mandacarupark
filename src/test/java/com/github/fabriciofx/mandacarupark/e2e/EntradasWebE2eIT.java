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
package com.github.fabriciofx.mandacarupark.e2e;

import com.github.fabriciofx.mandacarupark.Chrome;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.server.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
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
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import com.github.fabriciofx.mandacarupark.RandomPort;
import com.github.fabriciofx.mandacarupark.server.WebServer;
import com.github.fabriciofx.mandacarupark.server.WebServerProcess;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public final class EntradasWebE2eIT {
    @Test
    public void entradas() throws Exception {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
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
        final CountDownLatch latch = new CountDownLatch(1);
        final Server h2 = new ServerH2(
            session,
            new ScriptSql("mandacarupark.sql")
        );
        final int port = new RandomPort().intValue();
        final Server web = new WebServer(
            new WebServerProcess(estacionamento, port, latch)
        );
        try {
            h2.start();
            web.start();
            latch.await();
            final WebDriver driver = new Chrome();
            driver.get(
                new Sprintf(
                    "http://localhost:%d/entradas",
                    port
                ).asString()
            );
            final List<WebElement> elements = driver.findElements(
                By.tagName("table")
            );
            final StringBuilder sb = new StringBuilder();
            for (final WebElement element : elements) {
                sb.append(element.getText());
            }
            Assert.assertEquals(
                "00eb2ff4-ac01-4fbe-bf08-b9f75c7216d8 NPT5913 02/10/2022 22:48:57 " +
                "1017ec42-f8bf-4f3b-ae48-3791cdca38be IOP1234 02/10/2022 22:54:04 " +
                "0382a94e-8e42-4904-818f-e2b9041873af FAB1234 02/10/2022 22:58:23",
                sb.toString()
            );
            driver.quit();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            web.stop();
            h2.stop();
        }
    }
}
