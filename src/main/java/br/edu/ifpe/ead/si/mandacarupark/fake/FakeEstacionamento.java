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
import br.edu.ifpe.ead.si.mandacarupark.Precos;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import java.time.LocalDateTime;

public class FakeEstacionamento implements Estacionamento {
    private final Entradas entradas;
    private final Saidas saidas;
    private final Pagamentos pagamentos;
    private final Precos precos;

    public FakeEstacionamento(
        Entradas entradas,
        Saidas saidas,
        Pagamentos pagamentos,
        Precos precos
    ) {
        this.entradas = entradas;
        this.saidas = saidas;
        this.pagamentos = pagamentos;
        this.precos = precos;
    }

    public FakeEstacionamento(Precos precos) {
        this(
            new FakeEntradas(),
            new FakeSaidas(),
            new FakePagamentos(),
            precos
        );
    }

    @Override
    public Ticket entrada(Placa placa, LocalDateTime dataHora) {
        Entrada entrada = entradas.entrada(placa, dataHora);
        Ticket ticket = new FakeTicket(
            this.pagamentos,
            entrada.id(),
            placa,
            dataHora,
            new Dinheiro("0.0")
        );
        return ticket;
    }

    @Override
    public Ticket pagamento(Ticket ticket, LocalDateTime dataHora) {
        Dinheiro valor = this.precos.valor(ticket.dataHora(), dataHora);
        this.pagamentos.pagamento(ticket, dataHora, valor);
        return new FakeTicket(
            this.pagamentos,
            ticket.id(),
            ticket.placa(),
            ticket.dataHora(),
            valor
        );
    }

    @Override
    public void saida(Ticket ticket, Placa placa, LocalDateTime dataHora) {
        if (!ticket.validado()) {
            throw new RuntimeException("Ticket não validado!");
        }
        if (!ticket.placa().equals(placa)) {
            throw new RuntimeException("Ticket não confere com a placa!");
        }
        this.saidas.saida(ticket, placa, dataHora);
    }
}