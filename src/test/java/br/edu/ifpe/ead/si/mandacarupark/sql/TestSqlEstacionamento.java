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

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.db.H2Server;
import br.edu.ifpe.ead.si.mandacarupark.db.RandomName;
import br.edu.ifpe.ead.si.mandacarupark.db.Server;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import br.edu.ifpe.ead.si.mandacarupark.db.SqlScript;
import br.edu.ifpe.ead.si.mandacarupark.preco.PrecoFixo;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.time.LocalDateTime;

public class TestSqlEstacionamento {
    @Test
    public void entrada() throws Exception {
        Server server = new H2Server(
            new RandomName().toString(),
            new SqlScript("mandacarupark.sql")
        );
        server.start();
        Session session = server.session();
        Entradas entradas = new SqlEntradas(session);
        Estacionamento estacionamento = new SqlEstacionamento(
            session,
            entradas,
            new SqlSaidas(session),
            new SqlPagamentos(session),
            new PrecoFixo(new Dinheiro("5.00"))
        );
        Placa placa = new Placa("ABC1234");
        LocalDateTime agora = LocalDateTime.now();
        Ticket ticket = estacionamento.entrada(placa, agora);
        Entrada entrada = entradas.procura(ticket.id());
        Assert.assertEquals(entrada.placa().toString(), "ABC1234");
        server.stop();
    }

    @Test
    public void locacao() throws Exception {
        Server server = new H2Server(
            new RandomName().toString(),
            new SqlScript("mandacarupark.sql")
        );
        server.start();
        Session session = server.session();
        Entradas entradas = new SqlEntradas(session);
        Estacionamento estacionamento = new SqlEstacionamento(
            session,
            entradas,
            new SqlSaidas(session),
            new SqlPagamentos(session),
            new PrecoFixo(new Dinheiro("5.00"))
        );
        Placa placa = new Placa("ABC1234");
        LocalDateTime agora = LocalDateTime.now();
        Ticket ticket = estacionamento.entrada(placa, agora);
        ticket = estacionamento.pagamento(ticket, agora.plusMinutes(60));
        estacionamento.saida(ticket, placa, agora.plusMinutes(70));
        Assert.assertTrue(ticket.validado());
        Assert.assertEquals(ticket.valor(), new Dinheiro("5.00"));
        server.stop();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test()
    public void sairSemValidarTicket() throws Exception {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Ticket não validado!");
        Server server = new H2Server(
            new RandomName().toString(),
            new SqlScript("mandacarupark.sql")
        );
        server.start();
        Session session = server.session();
        Estacionamento estacionamento = new SqlEstacionamento(
            session,
            new SqlEntradas(session),
            new SqlSaidas(session),
            new SqlPagamentos(session),
            new PrecoFixo(new Dinheiro("5.00"))
        );
        Placa placa = new Placa("ABC1234");
        LocalDateTime agora = LocalDateTime.now();
        Ticket ticket = estacionamento.entrada(placa, agora);
        estacionamento.saida(ticket, placa, agora.plusMinutes(70));
        ticket.validado();
        server.stop();
    }
}
