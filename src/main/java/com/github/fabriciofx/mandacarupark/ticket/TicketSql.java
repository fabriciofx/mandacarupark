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
package com.github.fabriciofx.mandacarupark.ticket;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import com.github.fabriciofx.mandacarupark.ticket.imagem.Imagem;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemCodeQr;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemPapel;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTexto;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTicket;
import com.github.fabriciofx.mandacarupark.io.ResourceAsStream;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.sql.ResultSet;

public final class TicketSql implements Ticket {
    private final Session session;
    private final Id id;

    public TicketSql(final Session session, final Id id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Id id() {
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
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Media sobre(final Media media) {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT placa, datahora FROM entrada WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final DataHora dataHora;
            final Placa placa;
            if (rset.next()) {
                placa = new PlacaOf(rset.getString(1));
                dataHora = new DataHoraOf(rset.getString(2));
            } else {
                throw new RuntimeException(
                    "Dados sobre a entrada inexistentes ou inválidos!"
                );
            }
            return media.begin("ticket")
                .with("id", this.id)
                .with("placa", placa)
                .with("dataHora", dataHora)
                .end("ticket");
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Imagem imagem() {
        final Font roboto13;
        try {
            roboto13 = Font.createFont(
                Font.TRUETYPE_FONT,
                new ResourceAsStream("font/roboto-bold.ttf")
            ).deriveFont(13f);
        } catch (final FontFormatException | IOException ex) {
            throw new RuntimeException(ex);
        }
        return new ImagemTicket(
            new ImagemCodeQr(
                new ImagemTexto(
                    new ImagemPapel(150, 300),
                    roboto13,
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
