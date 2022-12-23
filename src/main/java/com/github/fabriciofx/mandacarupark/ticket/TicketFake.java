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
package com.github.fabriciofx.mandacarupark.ticket;

import com.github.fabriciofx.mandacarupark.Data;
import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.data.DataMap;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.ticket.imagem.Imagem;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemCodeQr;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemPapel;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTexto;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTicket;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

public final class TicketFake implements Ticket {
    private final Pagamentos pagamentos;
    private final Id id;
    private final Placa placa;
    private final DataHora dataHora;

    public TicketFake(
        final Pagamentos pagamentos,
        final Id id,
        final Placa placa,
        final DataHora dataHora
    ) {
        this.pagamentos = pagamentos;
        this.id = id;
        this.placa = placa;
        this.dataHora = dataHora;
    }

    @Override
    public Id id() {
        return this.id;
    }

    @Override
    public boolean validado() {
        final List<Pagamento> pagamento = this.pagamentos.procura(this.id);
        return !pagamento.isEmpty();
    }

    @Override
    public Dinheiro valor() {
        final List<Pagamento> pagamento = this.pagamentos.procura(this.id);
        final Dinheiro valor;
        if (pagamento.isEmpty()) {
            valor = new DinheiroOf("0.00");
        } else {
            valor = pagamento.get(0).sobre().get("valor");
        }
        return valor;
    }

    @Override
    public Data sobre() {
        return new DataMap(
            "placa", this.placa,
            "dataHora", this.dataHora
        );
    }

    @Override
    public Imagem imagem() {
        return new ImagemTicket(
            new ImagemCodeQr(
                new ImagemTexto(
                    new ImagemPapel(150, 300),
                    new Font("Lucida Sans Unicode", Font.PLAIN, 13),
                    Color.BLACK,
                    "MANDACARUPARK",
                    12,
                    26
                ),
                this.id.toString(),
                10,
                50,
                125,
                200
            ),
            this
        );
    }
}
