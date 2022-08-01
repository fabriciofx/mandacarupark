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
import br.edu.ifpe.ead.si.mandacarupark.preco.PrecoFixo;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public class TestFakeEstacionamento {
    @Test
    public void entrada() throws Exception {
        final Entradas entradas = new FakeEntradas();
        final Saidas saidas = new FakeSaidas();
        final Pagamentos pagamentos = new FakePagamentos();
        final Estacionamento estacionamento = new FakeEstacionamento(
            entradas,
            saidas,
            pagamentos,
            new PrecoFixo(new Dinheiro("5.00"))
        );
        final Placa placa = new Placa("ABC1234");
        final LocalDateTime agora = LocalDateTime.now();
        final Ticket ticket = estacionamento.entrada(placa, agora);
        final Entrada entrada = entradas.procura(ticket.id());
        Assert.assertEquals(entrada.placa().toString(), "ABC1234");
    }

    @Test
    public void locacao() {
        final Estacionamento estacionamento = new FakeEstacionamento(
            new PrecoFixo(new Dinheiro("5.00"))
        );
        final Placa placa = new Placa("ABC1234");
        final LocalDateTime agora = LocalDateTime.now();
        Ticket ticket = estacionamento.entrada(placa, agora);
        ticket = estacionamento.pagamento(ticket, agora.plusMinutes(60));
        estacionamento.saida(ticket, placa, agora.plusMinutes(70));
        Assert.assertTrue(ticket.validado());
        Assert.assertEquals(ticket.valor(), new Dinheiro("5.00"));
    }

    @Test()
    public void sairSemValidarTicket() {
        Assert.assertThrows(
            "Ticket não validado!",
            RuntimeException.class,
            () -> {
                Estacionamento estacionamento = new FakeEstacionamento(
                    new PrecoFixo(new Dinheiro("5.00"))
                );
                Placa placa = new Placa("ABC1234");
                LocalDateTime agora = LocalDateTime.now();
                Ticket ticket = estacionamento.entrada(placa, agora);
                estacionamento.saida(ticket, placa, agora.plusMinutes(70));
                ticket.validado();
            }
        );
    }
}
