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
package com.github.fabriciofx.mandacarupark.cli;

import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.console.ConsoleUnix;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.server.ServerH2;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.entradas.EntradasFake;
import com.github.fabriciofx.mandacarupark.estacionamento.EstacionamentoFake;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosFake;
import com.github.fabriciofx.mandacarupark.regras.RegrasFake;
import com.github.fabriciofx.mandacarupark.saidas.SaidasFake;
import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

public class TestOpcaoEntrada {
    @Test
    public void entrada() throws Exception {
        try (
            final Server server = new ServerH2(
                new NoAuth(
                    new H2Memory(
                        new RandomName()
                    )
                ),
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            new OpcaoEntrada(
                "1 - Entrada",
                new ConsoleUnix(
                    new ByteArrayInputStream("ABC1234\n\r".getBytes()),
                    output
                ),
                new EstacionamentoFake(
                    new EntradasFake(
                        new PagamentosFake()
                    ),
                    new SaidasFake(),
                    new PagamentosFake(),
                    new RegrasFake()
                )
            ).run();
            Assert.assertTrue(
                Pattern.compile(
                    "Placa: Ticket id: [0-9a-f]{8}\\b-[0-9a-f]{4}\\b-[0-9a-f" +
                        "]{4}\\b-[0-9a-f]{4}\\b-[0-9a-f]{12} em " +
                        "[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}"
                ).matcher(output.toString()).find()
            );
        }
    }
}
