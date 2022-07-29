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
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Locacoes;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Periodo;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.preco.PrecoFixo;
import org.junit.Test;
import java.time.LocalDateTime;

public class TestFakeLocacoes {
    @Test
    public void locacoes() {
        Entradas entradas = new FakeEntradas();
        Saidas saidas = new FakeSaidas();
        Pagamentos pagamentos = new FakePagamentos();
        Estacionamento estacionamento = new FakeEstacionamento(
            entradas,
            saidas,
            pagamentos,
            new PrecoFixo(new Dinheiro("5.00"))
        );
        // Locação 1
        Placa placa = new Placa("ABC1234");
        LocalDateTime agora = LocalDateTime.now();
        Ticket ticket = estacionamento.entrada(placa, agora);
        ticket = estacionamento.pagamento(ticket, agora.plusMinutes(60));
        estacionamento.saida(ticket, placa, agora.plusMinutes(70));
        // Locação 2
        placa = new Placa("DEF5678");
        ticket = estacionamento.entrada(placa, agora.plusMinutes(1));
        ticket = estacionamento.pagamento(ticket, agora.plusMinutes(40));
        estacionamento.saida(ticket, placa, agora.plusMinutes(45));
        // Locação 3
        placa = new Placa("GHI9012");
        ticket = estacionamento.entrada(placa, agora.plusMinutes(2));
        ticket = estacionamento.pagamento(ticket, agora.plusMinutes(55));
        estacionamento.saida(ticket, placa, agora.plusMinutes(52));
        Locacoes locacoes = new FakeLocacoes(
            entradas,
            saidas,
            pagamentos,
            new Periodo(agora.minusMinutes(5), agora.plusMinutes(80))
        );
    }
}
