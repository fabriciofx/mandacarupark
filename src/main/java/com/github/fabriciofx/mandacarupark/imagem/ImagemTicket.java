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
package com.github.fabriciofx.mandacarupark.imagem;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public final class ImagemTicket implements Imagem {
    private final Imagem origin;
    private final Ticket ticket;

    public ImagemTicket(final Imagem imagem, final Ticket ticket) {
        this.origin = imagem;
        this.ticket = ticket;
    }

    @Override
    public BufferedImage imagem() {
        final Placa placa = ticket.sobre().valor("placa");
        final DataHora dataHora = ticket.sobre().valor("dataHora");
        final Font helvetica7 = new Font("Lucida Sans Unicode", Font.PLAIN, 7);
        final Font helvetica13 = new Font(
            "Lucida Sans Unicode",
            Font.PLAIN,
            13
        );
        final BufferedImage imagem = new ImagemTexto(
            this.origin,
            helvetica7,
            Color.BLACK,
            this.ticket.id().toString(),
            5,
            220
        ).imagem();
        final BufferedImage imagem2 = new ImagemTexto(
            () -> imagem,
            helvetica13,
            Color.BLACK,
            new Sprintf("Placa           %s", placa.toString()).asString(),
            10,
            250
        ).imagem();
        final BufferedImage imagem3 = new ImagemTexto(
            () -> imagem2,
            helvetica13,
            Color.BLACK,
            new Sprintf("Data       %s", dataHora.data()).asString(),
            10,
            263
        ).imagem();
        final BufferedImage imagem4 = new ImagemTexto(
            () -> imagem3,
            helvetica13,
            Color.BLACK,
            new Sprintf("Hora           %s", dataHora.hora()).asString(),
            10,
            276
        ).imagem();
        return imagem4;
    }
}
