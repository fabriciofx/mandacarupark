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

import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Regras;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.data.MapEntryOf;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.entradas.EntradasFake;
import com.github.fabriciofx.mandacarupark.estacionamento.EstacionamentoFake;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosFake;
import com.github.fabriciofx.mandacarupark.periodo.PeriodoOf;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.regras.RegrasFake;
import com.github.fabriciofx.mandacarupark.regras.RegrasOf;
import com.github.fabriciofx.mandacarupark.saidas.SaidasFake;
import com.github.fabriciofx.mandacarupark.ticket.TicketFake;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public final class TestRegra {
    @Test
    public void tolerancia() {
        final Regras regras = new RegrasOf(
            new DomingoGratis(),
            new Tolerancia(),
            new ValorFixo(new DinheiroOf("5.00"))
        );
        final Pagamentos pagamentos = new PagamentosFake();
        final Entradas entradas = new EntradasFake(pagamentos);
        final Saidas saidas = new SaidasFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            saidas,
            pagamentos,
            regras
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
            estacionamento.valor(
                ticket,
                new DataHoraOf(dateTime.plusMinutes(20))
            )
        );
    }

    @Test
    public void domingoGratis() {
        final Regras regras = new RegrasOf(
            new DomingoGratis(),
            new Tolerancia(),
            new ValorFixo(new DinheiroOf("5.00"))
        );
        final Pagamentos pagamentos = new PagamentosFake();
        final Entradas entradas = new EntradasFake(pagamentos);
        final Saidas saidas = new SaidasFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            saidas,
            pagamentos,
            regras
        );
        final Placa placa = new PlacaOf("ABC1234");
        final LocalDateTime dateTime = LocalDateTime.of(2022, 7, 31, 10, 30);
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
            new DinheiroOf("0.00"),
            estacionamento.valor(
                ticket,
                new DataHoraOf(dateTime.plusMinutes(60))
            )
        );
    }

    @Test
    public void valorVariavelTempoExato() {
        final Pagamentos pagamentos = new PagamentosFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            new EntradasFake(pagamentos),
            new SaidasFake(),
            pagamentos,
            new RegrasFake(
                new ValorVariavel(
                    new DinheiroOf("6.00"),
                    new DinheiroOf("4.00")
                )
            )
        );
        final Ticket ticket = new TicketFake(
            new PagamentosFake(),
            new Uuid(),
            new PlacaOf("ABC1234"),
            new DataHoraOf("24/12/2022 12:45:20")
        );
        Assert.assertEquals(
            new DinheiroOf("6.00"),
            estacionamento.valor(ticket, new DataHoraOf("24/12/2022 16:45:20"))
        );
    }

    @Test
    public void valorVariavelComUmMinutoExcedente() {
        final Pagamentos pagamentos = new PagamentosFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            new EntradasFake(pagamentos),
            new SaidasFake(),
            pagamentos,
            new RegrasFake(
                new ValorVariavel(
                    new DinheiroOf("6.00"),
                    new DinheiroOf("4.00")
                )
            )
        );
        final Ticket ticket = new TicketFake(
            new PagamentosFake(),
            new Uuid(),
            new PlacaOf("ABC1234"),
            new DataHoraOf("24/12/2022 12:45:20")
        );
        Assert.assertEquals(
            new DinheiroOf("10.00"),
            estacionamento.valor(ticket, new DataHoraOf("24/12/2022 16:46:20"))
        );
    }

    @Test
    public void valorVariavelComUmaHoraEUmMinutoExcedente() {
        final Pagamentos pagamentos = new PagamentosFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            new EntradasFake(pagamentos),
            new SaidasFake(),
            pagamentos,
            new RegrasFake(
                new ValorVariavel(
                    new DinheiroOf("6.00"),
                    new DinheiroOf("4.00")
                )
            )
        );
        final Ticket ticket = new TicketFake(
            new PagamentosFake(),
            new Uuid(),
            new PlacaOf("ABC1234"),
            new DataHoraOf("24/12/2022 12:45:20")
        );
        Assert.assertEquals(
            new DinheiroOf("14.00"),
            estacionamento.valor(ticket, new DataHoraOf("24/12/2022 17:46:20"))
        );
    }

    @Test
    public void mensalista() {
        final Pagamentos pagamentos = new PagamentosFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            new EntradasFake(pagamentos),
            new SaidasFake(),
            pagamentos,
            new RegrasOf(
                new Tolerancia(),
                new DomingoGratis(),
                new MensalistaFake(
                    new DinheiroOf("50.00"),
                    new MapEntryOf<>(
                        new Uuid("e4c1f93a-a00d-4ae2-b3f9-2bdc2cbe23e7"),
                        new PeriodoOf(
                            new DataHoraOf("01/01/2023 10:00:00"),
                            new DataHoraOf("31/01/2023 22:00:00")
                        )
                    )
                ),
                new ValorFixo(new DinheiroOf("8.00"))
            )
        );
        final Placa placa = new PlacaOf("ABC1234");
        final Ticket ticket = estacionamento.entrada(
            new Uuid("e4c1f93a-a00d-4ae2-b3f9-2bdc2cbe23e7"),
            placa,
            new DataHoraOf("05/01/2023 10:21:36")
        );
        estacionamento.pagamento(
            ticket,
            new DataHoraOf("05/01/2023 12:27:14")
        );
        estacionamento.saida(
            ticket,
            placa,
            new DataHoraOf("05/01/2023 12:35:58")
        );
        Assert.assertTrue(ticket.validado());
        Assert.assertEquals(
            new DinheiroOf("50.00"),
            estacionamento.valor(
                ticket,
                new DataHoraOf("05/01/2023 12:27:14")
            )
        );
    }
}
