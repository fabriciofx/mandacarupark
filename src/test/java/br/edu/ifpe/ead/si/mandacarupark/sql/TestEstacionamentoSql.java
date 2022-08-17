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
package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Contas;
import br.edu.ifpe.ead.si.mandacarupark.DataHora;
import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.conta.DomingoGratis;
import br.edu.ifpe.ead.si.mandacarupark.conta.Tolerancia;
import br.edu.ifpe.ead.si.mandacarupark.conta.ValorFixo;
import br.edu.ifpe.ead.si.mandacarupark.db.RandomName;
import br.edu.ifpe.ead.si.mandacarupark.db.ScriptSql;
import br.edu.ifpe.ead.si.mandacarupark.db.Server;
import br.edu.ifpe.ead.si.mandacarupark.db.ServerH2;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public class TestEstacionamentoSql {
    @Test
    public void entrada() throws Exception {
        try (
            final Server server = new ServerH2(
                new RandomName(),
                new ScriptSql("mandacarupark.sql")
            ).start()
        ) {
            final Session session = server.session();
            final Entradas entradas = new EntradasSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                new SaidasSql(session),
                new PagamentosSql(session),
                new Contas(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new Dinheiro("5.00"))
                )
            );
            final Placa placa = new Placa("ABC1234");
            final LocalDateTime dateTime = LocalDateTime.of(2022, 8, 2, 10, 30);
            final Ticket ticket = estacionamento.entrada(
                placa,
                new DataHora(dateTime)
            );
            final Entrada entrada = entradas.procura(ticket.id());
            Assert.assertEquals(entrada.placa().toString(), "ABC1234");
        }
    }

    @Test
    public void locacao() throws Exception {
        try (
            final Server server = new ServerH2(
                new RandomName(),
                new ScriptSql("mandacarupark.sql")
            ).start()
        ) {
            final Session session = server.session();
            final Entradas entradas = new EntradasSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                new SaidasSql(session),
                new PagamentosSql(session),
                new Contas(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new Dinheiro("5.00"))
                )
            );
            final Placa placa = new Placa("ABC1234");
            final LocalDateTime dateTime = LocalDateTime.of(2022, 8, 2, 10, 30);
            final Ticket ticket = estacionamento.entrada(
                placa,
                new DataHora(dateTime)
            );
            final Ticket ticketValidado = estacionamento.pagamento(
                ticket,
                new DataHora(dateTime.plusMinutes(60))
            );
            estacionamento.saida(
                ticketValidado,
                placa,
                new DataHora(dateTime.plusMinutes(70))
            );
            Assert.assertTrue(ticketValidado.validado());
            Assert.assertEquals(ticketValidado.valor(), new Dinheiro("5.00"));
        }
    }

    @Test
    public void tolerancia() throws Exception {
        try (
            final Server server = new ServerH2(
                new RandomName(),
                new ScriptSql("mandacarupark.sql")
            ).start()
        ) {
            final Session session = server.session();
            final Entradas entradas = new EntradasSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                new SaidasSql(session),
                new PagamentosSql(session),
                new Contas(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new Dinheiro("5.00"))
                )
            );
            final Placa placa = new Placa("ABC1234");
            final LocalDateTime dateTime = LocalDateTime.of(2022, 8, 2, 10, 30);
            final Ticket ticket = estacionamento.entrada(
                placa,
                new DataHora(dateTime)
            );
            final Ticket ticketValidado = estacionamento.pagamento(
                ticket,
                new DataHora(dateTime.plusMinutes(20))
            );
            estacionamento.saida(
                ticketValidado,
                placa,
                new DataHora(dateTime.plusMinutes(25))
            );
            Assert.assertTrue(ticketValidado.validado());
            Assert.assertEquals(ticketValidado.valor(), new Dinheiro("0.00"));
        }
    }

    @Test
    public void domingoGratis() throws Exception {
        try (
            final Server server = new ServerH2(
                new RandomName(),
                new ScriptSql("mandacarupark.sql")
            ).start()
        ) {
            final Session session = server.session();
            final Entradas entradas = new EntradasSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                new SaidasSql(session),
                new PagamentosSql(session),
                new Contas(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new Dinheiro("5.00"))
                )
            );
            final Placa placa = new Placa("ABC1234");
            final LocalDateTime dateTime = LocalDateTime.of(
                2022, 7, 31, 10, 30
            );
            final Ticket ticket = estacionamento.entrada(
                placa,
                new DataHora(dateTime)
            );
            final Ticket ticketValidado = estacionamento.pagamento(
                ticket,
                new DataHora(dateTime.plusMinutes(60))
            );
            estacionamento.saida(
                ticketValidado,
                placa,
                new DataHora(dateTime.plusMinutes(70))
            );
            Assert.assertTrue(ticketValidado.validado());
            Assert.assertEquals(ticketValidado.valor(), new Dinheiro("0.00"));
        }
    }

    @Test()
    public void sairSemValidarTicket() throws Exception {
        Assert.assertThrows(
            "Ticket não validado!",
            RuntimeException.class,
            () -> {
                try (
                    final Server server = new ServerH2(
                        new RandomName(),
                        new ScriptSql("mandacarupark.sql")
                    ).start()
                ) {
                    final Session session = server.session();
                    final Estacionamento estacionamento = new EstacionamentoSql(
                        session,
                        new EntradasSql(session),
                        new SaidasSql(session),
                        new PagamentosSql(session),
                        new Contas(
                            new DomingoGratis(),
                            new Tolerancia(),
                            new ValorFixo(new Dinheiro("5.00"))
                        )
                    );
                    final Placa placa = new Placa("ABC1234");
                    final LocalDateTime agora = LocalDateTime.now();
                    final Ticket ticket = estacionamento.entrada(
                        placa,
                        new DataHora(agora)
                    );
                    estacionamento.saida(
                        ticket,
                        placa,
                        new DataHora(agora.plusMinutes(70))
                    );
                    ticket.validado();
                }
            }
        );
    }
}
