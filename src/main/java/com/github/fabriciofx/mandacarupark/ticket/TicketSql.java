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
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.Uuid;
import com.github.fabriciofx.mandacarupark.data.DataMap;
import com.github.fabriciofx.mandacarupark.db.Select;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import com.github.fabriciofx.mandacarupark.ticket.imagem.Imagem;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemCodeQr;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemPapel;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTexto;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTicket;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;

public final class TicketSql implements Ticket {
    private final Session session;
    private final Uuid id;
    private final Placa placa;
    private final DataHora dataHora;

    public TicketSql(
        final Session session,
        final Uuid id,
        final Placa placa,
        final DataHora dataHora
    ) {
        this.session = session;
        this.id = id;
        this.placa = placa;
        this.dataHora = dataHora;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public boolean validado() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT COUNT(*) FROM pagamento WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            int quantidade;
            if (rset.next()) {
                quantidade = rset.getInt(1);
            } else {
                throw new RuntimeException(
                    "Validação inexistente ou inválida!"
                );
            }
            return quantidade != 0;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Data sobre() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT valor FROM pagamento WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final Dinheiro valor;
            if (rset.next()) {
                valor = new DinheiroOf(rset.getBigDecimal(1));
            } else {
                valor = new DinheiroOf("0.00");
            }
            return new DataMap(
                "placa", this.placa,
                "dataHora", this.dataHora,
                "valor", valor
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
