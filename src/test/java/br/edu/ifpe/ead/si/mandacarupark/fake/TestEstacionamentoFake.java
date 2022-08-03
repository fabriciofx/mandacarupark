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
package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Contas;
import br.edu.ifpe.ead.si.mandacarupark.conta.DomingoGratis;
import br.edu.ifpe.ead.si.mandacarupark.conta.ValorFixo;
import br.edu.ifpe.ead.si.mandacarupark.conta.Tolerancia;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public class TestEstacionamentoFake {
    @Test
    public void entrada() throws Exception {
        final Entradas entradas = new EntradasFake();
        final Saidas saidas = new SaidasFake();
        final Pagamentos pagamentos = new PagamentosFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            saidas,
            pagamentos,
            new Contas(
                new DomingoGratis(),
                new Tolerancia(),
                new ValorFixo(new Dinheiro("5.00"))
            )
        );
        final Placa placa = new Placa("ABC1234");
        final LocalDateTime dataHora = LocalDateTime.of(2022, 8, 2, 10, 30);
        final Ticket ticket = estacionamento.entrada(placa, dataHora);
        final Entrada entrada = entradas.procura(ticket.id());
        Assert.assertEquals(entrada.placa().toString(), "ABC1234");
    }

    @Test
    public void locacao() {
        final Estacionamento estacionamento = new EstacionamentoFake(
            new Contas(
                new DomingoGratis(),
                new Tolerancia(),
                new ValorFixo(new Dinheiro("5.00"))
            )
        );
        final Placa placa = new Placa("ABC1234");
        final LocalDateTime dataHora = LocalDateTime.of(2022, 8, 2, 10, 30);
        Ticket ticket = estacionamento.entrada(placa, dataHora);
        ticket = estacionamento.pagamento(ticket, dataHora.plusMinutes(60));
        estacionamento.saida(ticket, placa, dataHora.plusMinutes(70));
        Assert.assertTrue(ticket.validado());
        Assert.assertEquals(ticket.valor(), new Dinheiro("5.00"));
    }

    @Test
    public void tolerancia() {
        final Estacionamento estacionamento = new EstacionamentoFake(
            new Contas(
                new DomingoGratis(),
                new Tolerancia(),
                new ValorFixo(new Dinheiro("5.00"))
            )
        );
        final Placa placa = new Placa("ABC1234");
        final LocalDateTime dataHora = LocalDateTime.of(2022, 8, 2, 10, 30);
        Ticket ticket = estacionamento.entrada(placa, dataHora);
        ticket = estacionamento.pagamento(ticket, dataHora.plusMinutes(20));
        estacionamento.saida(ticket, placa, dataHora.plusMinutes(25));
        Assert.assertTrue(ticket.validado());
        Assert.assertEquals(ticket.valor(), new Dinheiro("0.00"));
    }

    @Test()
    public void sairSemValidarTicket() {
        Assert.assertThrows(
            "Ticket não validado!",
            RuntimeException.class,
            () -> {
                Estacionamento estacionamento = new EstacionamentoFake(
                    new Contas(
                        new DomingoGratis(),
                        new Tolerancia(),
                        new ValorFixo(new Dinheiro("5.00"))
                    )
                );
                Placa placa = new Placa("ABC1234");
                final LocalDateTime dataHora = LocalDateTime.of(
                    2022, 8, 2, 10, 30
                );
                Ticket ticket = estacionamento.entrada(placa, dataHora);
                estacionamento.saida(ticket, placa, dataHora.plusMinutes(70));
                ticket.validado();
            }
        );
    }
}
