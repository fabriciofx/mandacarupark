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
package com.github.fabriciofx.mandacarupark.estacionamento;

import com.github.fabriciofx.mandacarupark.Contas;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.conta.DomingoGratis;
import com.github.fabriciofx.mandacarupark.conta.Tolerancia;
import com.github.fabriciofx.mandacarupark.conta.ValorFixo;
import com.github.fabriciofx.mandacarupark.contas.ContasFake;
import com.github.fabriciofx.mandacarupark.contas.ContasOf;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.entradas.EntradasFake;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosFake;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.saidas.SaidasFake;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public final class TestEstacionamentoFake {
    @Test
    public void entrada() {
        final Entradas entradas = new EntradasFake(
            new PagamentosFake(),
            new ContasFake()
        );
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            new SaidasFake(),
            new PagamentosFake(),
            new ContasFake()
        );
        final Ticket ticket = estacionamento.entrada(
            new PlacaOf("ABC1234"),
            new DataHoraOf(
                LocalDateTime.of(2022, 8, 2, 10, 30)
            )
        );
        final Entrada entrada = entradas.procura(ticket.id());
        Assert.assertEquals(
            "ABC1234",
            entrada.sobre().get("placa").toString()
        );
    }

    @Test
    public void locacao() {
        final Contas contas = new ContasOf(
            new DomingoGratis(),
            new Tolerancia(),
            new ValorFixo(new DinheiroOf("5.00"))
        );
        final Pagamentos pagamentos = new PagamentosFake();
        final Entradas entradas = new EntradasFake(pagamentos, contas);
        final Saidas saidas = new SaidasFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            saidas,
            pagamentos,
            contas
        );
        final Placa placa = new PlacaOf("ABC1234");
        final LocalDateTime dateTime = LocalDateTime.of(2022, 8, 2, 10, 30);
        final Ticket ticket = estacionamento.entrada(
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
            ticket.valor(new DataHoraOf())
        );
    }

    @Test
    public void tolerancia() {
        final Contas contas = new ContasOf(
            new DomingoGratis(),
            new Tolerancia(),
            new ValorFixo(new DinheiroOf("5.00"))
        );
        final Pagamentos pagamentos = new PagamentosFake();
        final Entradas entradas = new EntradasFake(pagamentos, contas);
        final Saidas saidas = new SaidasFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            saidas,
            pagamentos,
            contas
        );
        final Placa placa = new PlacaOf("ABC1234");
        final LocalDateTime dateTime = LocalDateTime.of(2022, 8, 2, 10, 30);
        final Ticket ticket = estacionamento.entrada(
            placa,
            new DataHoraOf(dateTime)
        );
        estacionamento.pagamento(
            ticket,
            new DataHoraOf(dateTime.plusMinutes(20))
        );
        estacionamento.saida(
            ticket,
            placa,
            new DataHoraOf(dateTime.plusMinutes(25))
        );
        Assert.assertTrue(ticket.validado());
        Assert.assertEquals(
            new DinheiroOf("0.00"),
            ticket.valor(new DataHoraOf(dateTime.plusMinutes(20)))
        );
    }

    @Test
    public void domingoGratis() {
        final Contas contas = new ContasOf(
            new DomingoGratis(),
            new Tolerancia(),
            new ValorFixo(new DinheiroOf("5.00"))
        );
        final Pagamentos pagamentos = new PagamentosFake();
        final Entradas entradas = new EntradasFake(pagamentos, contas);
        final Saidas saidas = new SaidasFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            saidas,
            pagamentos,
            contas
        );
        final Placa placa = new PlacaOf("ABC1234");
        final LocalDateTime dateTime = LocalDateTime.of(2022, 7, 31, 10, 30);
        final Ticket ticket = estacionamento.entrada(
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
            new DinheiroOf("0.00"),
            ticket.valor(new DataHoraOf(dateTime.plusMinutes(60)))
        );
    }

    @Test()
    public void sairSemValidarTicket() {
        Assert.assertThrows(
            "Ticket não validado!",
            RuntimeException.class,
            () -> {
                final Contas contas = new ContasOf(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new DinheiroOf("5.00"))
                );
                final Pagamentos pagamentos = new PagamentosFake();
                final Entradas entradas = new EntradasFake(pagamentos, contas);
                final Saidas saidas = new SaidasFake();
                final Estacionamento estacionamento = new EstacionamentoFake(
                    entradas,
                    saidas,
                    pagamentos,
                    contas
                );
                Placa placa = new PlacaOf("ABC1234");
                final LocalDateTime dateTime = LocalDateTime.of(
                    2022, 8, 2, 10, 30
                );
                final Ticket ticket = estacionamento.entrada(
                    placa,
                    new DataHoraOf(dateTime)
                );
                estacionamento.saida(
                    ticket,
                    placa,
                    new DataHoraOf(dateTime.plusMinutes(70))
                );
                ticket.validado();
            }
        );
    }

    @Test
    public void sobreEntradas() {
        final Contas contas = new ContasOf(
            new DomingoGratis(),
            new Tolerancia(),
            new ValorFixo(new DinheiroOf("5.00"))
        );
        final Pagamentos pagamentos = new PagamentosFake();
        final Entradas entradas = new EntradasFake(pagamentos, contas);
        final Saidas saidas = new SaidasFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            saidas,
            pagamentos,
            contas
        );
        estacionamento.entrada(
            new PlacaOf("ABC1234"),
            new DataHoraOf(
                LocalDateTime.of(2022, 8, 2, 10, 30)
            )
        );
        estacionamento.entrada(
            new PlacaOf("XYZ9876"),
            new DataHoraOf(
                LocalDateTime.of(2022, 8, 2, 11, 12)
            )
        );
        final StringBuilder sb = new StringBuilder();
        for (final Entrada entrada : entradas) {
            sb.append(entrada.sobre().toString());
        }
        Assert.assertTrue(
            sb.toString().contains("placa=XYZ9876") &&
            sb.toString().contains("dataHora=2022-08-02 11:12") &&
            sb.toString().contains("placa=ABC1234") &&
            sb.toString().contains("dataHora=2022-08-02 10:30")
        );
    }
}
