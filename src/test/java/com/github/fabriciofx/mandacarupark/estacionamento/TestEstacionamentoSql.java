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
package com.github.fabriciofx.mandacarupark.estacionamento;

import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.media.MapMedia;
import com.github.fabriciofx.mandacarupark.server.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.entradas.EntradasSql;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosSql;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.regra.DomingoGratis;
import com.github.fabriciofx.mandacarupark.regra.Tolerancia;
import com.github.fabriciofx.mandacarupark.regra.ValorFixo;
import com.github.fabriciofx.mandacarupark.regras.RegrasOf;
import com.github.fabriciofx.mandacarupark.saidas.SaidasSql;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public final class TestEstacionamentoSql {
    @Test
    public void entrada() throws Exception {
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
            final Entradas entradas = new EntradasSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                new SaidasSql(session),
                new PagamentosSql(session),
                new RegrasOf(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new DinheiroOf("5.00"))
                )
            );
            final Placa placa = new PlacaOf("ABC1234");
            final LocalDateTime dateTime = LocalDateTime.of(2022, 8, 2, 10, 30);
            final Ticket ticket = estacionamento.entrada(
                new Uuid(),
                placa,
                new DataHoraOf(dateTime)
            );
            final Entrada entrada = entradas.procura(ticket.id());
            Assert.assertEquals(
                "ABC1234",
                entrada.sobre(new MapMedia()).select("placa").toString()
            );
        }
    }

    @Test
    public void locacao() throws Exception {
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
            final Entradas entradas = new EntradasSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                new SaidasSql(session),
                new PagamentosSql(session),
                new RegrasOf(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new DinheiroOf("5.00"))
                )
            );
            final Placa placa = new PlacaOf("ABC1234");
            final LocalDateTime dateTime = LocalDateTime.of(2022, 8, 2, 10, 30);
            final Ticket ticket = estacionamento.entrada(
                new Uuid(),
                placa,
                new DataHoraOf(dateTime)
            );
            estacionamento.pagamento(
                ticket,
                new DataHoraOf(dateTime.plusMinutes(60))
            );
            estacionamento.saida(
                ticket,
                placa,
                new DataHoraOf(dateTime.plusMinutes(70))
            );
            Assert.assertTrue(ticket.validado());
            Assert.assertEquals(
                new DinheiroOf("5.00"),
                estacionamento.valor(ticket, new DataHoraOf())
            );
        }
    }

    @Test()
    public void sairSemValidarTicket() {
        Assert.assertThrows(
            "Ticket não validado!",
            RuntimeException.class,
            () -> {
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
                    final Estacionamento estacionamento = new EstacionamentoSql(
                        session,
                        new EntradasSql(session),
                        new SaidasSql(session),
                        new PagamentosSql(session),
                        new RegrasOf(
                            new DomingoGratis(),
                            new Tolerancia(),
                            new ValorFixo(new DinheiroOf("5.00"))
                        )
                    );
                    final Placa placa = new PlacaOf("ABC1234");
                    final LocalDateTime agora = LocalDateTime.now();
                    final Ticket ticket = estacionamento.entrada(
                        new Uuid(),
                        placa,
                        new DataHoraOf(agora)
                    );
                    estacionamento.saida(
                        ticket,
                        placa,
                        new DataHoraOf(agora.plusMinutes(70))
                    );
                    ticket.validado();
                }
            }
        );
    }
}
