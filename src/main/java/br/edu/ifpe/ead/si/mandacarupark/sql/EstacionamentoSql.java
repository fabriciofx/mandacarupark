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
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;

public class EstacionamentoSql implements Estacionamento {
    private final Session session;
    private final Entradas entradas;
    private final Saidas saidas;
    private final Pagamentos pagamentos;
    private final Contas contas;

    public EstacionamentoSql(
        final Session session,
        final Entradas entradas,
        final Saidas saidas,
        final Pagamentos pagamentos,
        final Contas contas
    ) {
        this.session = session;
        this.entradas = entradas;
        this.saidas = saidas;
        this.pagamentos = pagamentos;
        this.contas = contas;
    }

    @Override
    public Ticket entrada(final Placa placa, final DataHora dataHora) {
        final Entrada entrada = this.entradas.entrada(placa, dataHora);
        return new TicketSql(this.session, entrada.id(), placa, dataHora);
    }

    @Override
    public Ticket pagamento(final Ticket ticket, final DataHora dataHora) {
        final Dinheiro valor = this.contas.conta(
            ticket.dataHora(),
            dataHora
        ).valor(
            ticket.dataHora(),
            dataHora
        );
        this.pagamentos.pagamento(ticket, dataHora, valor);
        return new TicketSql(
            this.session,
            ticket.id(),
            ticket.placa(),
            ticket.dataHora()
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
        if (!ticket.placa().equals(placa)) {
            throw new RuntimeException("Ticket não confere com a placa!");
        }
        this.saidas.saida(ticket, placa, dataHora);
    }
}
