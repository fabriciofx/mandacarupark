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
package com.github.fabriciofx.mandacarupark.ticket.imagem;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.media.MapMedia;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public final class ImagemTicket implements Imagem {
    private final Imagem origin;
    private final Ticket ticket;

    public ImagemTicket(final Imagem imagem, final Ticket ticket) {
        this.origin = imagem;
        this.ticket = ticket;
    }

    @Override
    public BufferedImage imagem() {
        final Media media = ticket.sobre(new MapMedia());
        final Placa placa = media.get("placa");
        final DataHora dataHora = media.get("dataHora");
        final InputStream font7 = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("font/roboto-bold.ttf");
        final InputStream font13 = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("font/roboto-bold.ttf");
        final Font roboto7;
        final Font roboto13;
        try {
            roboto7 = Font.createFont(Font.TRUETYPE_FONT, font7)
                .deriveFont(7f);
            roboto13 = Font.createFont(Font.TRUETYPE_FONT, font13)
                .deriveFont(13f);
        } catch (final FontFormatException ex) {
            throw new RuntimeException(ex);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
        final BufferedImage imagem = new ImagemTexto(
            this.origin,
            roboto7,
            Color.BLACK,
            this.ticket.id().toString(),
            5,
            220
        ).imagem();
        final BufferedImage imagem2 = new ImagemTexto(
            () -> imagem,
            roboto13,
            Color.BLACK,
            new Sprintf("Placa           %s", placa.toString()).asString(),
            10,
            250
        ).imagem();
        final BufferedImage imagem3 = new ImagemTexto(
            () -> imagem2,
            roboto13,
            Color.BLACK,
            new Sprintf("Data       %s", dataHora.data()).asString(),
            10,
            263
        ).imagem();
        final BufferedImage imagem4 = new ImagemTexto(
            () -> imagem3,
            roboto13,
            Color.BLACK,
            new Sprintf("Hora           %s", dataHora.hora()).asString(),
            10,
            276
        ).imagem();
        return imagem4;
    }
}
