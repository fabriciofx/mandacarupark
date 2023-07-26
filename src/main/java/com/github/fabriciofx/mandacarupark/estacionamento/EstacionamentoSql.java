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
package com.github.fabriciofx.mandacarupark.estacionamento;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Permanencia;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Regras;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.locacoes.LocacoesSql;
import com.github.fabriciofx.mandacarupark.media.MemMedia;
import com.github.fabriciofx.mandacarupark.periodo.PeriodoOf;
import com.github.fabriciofx.mandacarupark.permanencia.PermanenciaOf;
import com.github.fabriciofx.mandacarupark.regra.Cortesia;
import com.github.fabriciofx.mandacarupark.ticket.TicketSql;

public final class EstacionamentoSql implements Estacionamento {
    private final Session session;
    private final Entradas entradas;
    private final Saidas saidas;
    private final Pagamentos pagamentos;
    private final Regras regras;

    public EstacionamentoSql(
        final Session session,
        final Entradas entradas,
        final Saidas saidas,
        final Pagamentos pagamentos,
        final Regras regras
    ) {
        this.session = session;
        this.entradas = entradas;
        this.saidas = saidas;
        this.pagamentos = pagamentos;
        this.regras = regras;
    }

    @Override
    public Ticket entrada(
        final Id id,
        final Placa placa,
        final DataHora dataHora
    ) {
        final Entrada entrada = this.entradas.entrada(id, placa, dataHora);
        return new TicketSql(this.session, entrada.id());
    }

    @Override
    public Dinheiro valor(final Ticket ticket, final DataHora termino) {
        final Periodo periodo = new PeriodoOf(
            ticket.sobre(new MemMedia()).select("dataHora"),
            termino
        );
        return this.regras.regra(
            ticket.id(),
            periodo,
            new Cortesia()
        ).valor(ticket.id(), periodo);
    }

    @Override
    public void pagamento(final Ticket ticket, final DataHora dataHora) {
        final Dinheiro valor = this.valor(ticket, dataHora);
        this.pagamentos.pagamento(ticket, dataHora, valor);
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
        if (!ticket.sobre(new MemMedia()).select("placa").equals(placa)) {
            throw new RuntimeException("Ticket não confere com a placa!");
        }
        this.saidas.saida(ticket, placa, dataHora);
    }

    @Override
    public Permanencia permanencia(final Ticket ticket, final DataHora atual) {
        return new PermanenciaOf(
            ticket.sobre(new MemMedia()).select("dataHora"),
            atual
        );
    }

    @Override
    public Media sobre(final Media media) {
        return media.with("entradas", this.entradas)
            .with("saidas", this.saidas)
            .with("pagamentos", this.pagamentos)
            .with("regras", this.regras)
            .with("locacoes", new LocacoesSql(this.session));
    }
}
