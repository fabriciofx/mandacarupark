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
package com.github.fabriciofx.mandacarupark.fake;

import com.github.fabriciofx.mandacarupark.Contas;
import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Ticket;

public class EstacionamentoFake implements Estacionamento {
    private final Entradas entradas;
    private final Saidas saidas;
    private final Pagamentos pagamentos;
    private final Contas contas;

    public EstacionamentoFake(final Contas contas) {
        this(
            new EntradasFake(),
            new SaidasFake(),
            new PagamentosFake(),
            contas
        );
    }

    public EstacionamentoFake(
        final Entradas entradas,
        final Saidas saidas,
        final Pagamentos pagamentos,
        final Contas contas
    ) {
        this.entradas = entradas;
        this.saidas = saidas;
        this.pagamentos = pagamentos;
        this.contas = contas;
    }

    @Override
    public Ticket entrada(final Placa placa, final DataHora dataHora) {
        final Entrada entrada = entradas.entrada(placa, dataHora);
        final Ticket ticket = new TicketFake(
            this.pagamentos,
            entrada.id(),
            placa,
            dataHora
        );
        return ticket;
    }

    @Override
    public Ticket pagamento(final Ticket ticket, final DataHora dataHora) {
        final Periodo periodo = new Periodo(
            ticket.sobre().value("dataHora"),
            dataHora
        );
        final Dinheiro valor = this.contas.conta(periodo).valor(periodo);
        this.pagamentos.pagamento(ticket, dataHora, valor);
        return new TicketFake(
            this.pagamentos,
            ticket.id(),
            ticket.sobre().value("placa"),
            ticket.sobre().value("dataHora")
        );
    }

    @Override
    public void saida(
        final Ticket ticket,
        final Placa placa,
        final DataHora dataHora
    ) {
        if (!ticket.validado()) {
            throw new RuntimeException("Ticket não validado!");
        }
        if (!ticket.sobre().value("placa").equals(placa)) {
            throw new RuntimeException("Ticket não confere com a placa!");
        }
        this.saidas.saida(ticket, placa, dataHora);
    }
}
