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
package com.github.fabriciofx.mandacarupark;

import com.github.fabriciofx.mandacarupark.barcode.Barcode;
import com.github.fabriciofx.mandacarupark.barcode.BarcodeQrCode;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Impressora {
    public Impressora() {
    }

    public void imprima(final Ticket ticket, final File arquivo) {
        final Placa placa = ticket.sobre().valor("placa");
        final DataHora dataHora = ticket.sobre().valor("dataHora");
        final Barcode barcode = new BarcodeQrCode(ticket.id().toString());
        final BufferedImage image = barcode.image();
        final Graphics2D g2d = image.createGraphics();
        final RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB
        );
        g2d.setRenderingHints(rh);
        g2d.setColor(Color.black);
        final Font helvetica7 = new Font("Lucida Sans Unicode", Font.PLAIN, 7);
        g2d.setFont(helvetica7);
        drawString(
            g2d,
            ticket.id().toString(),
            5,
            220
        );
        final Font helvetica13 = new Font("Lucida Sans Unicode", Font.PLAIN, 13);
        g2d.setFont(helvetica13);
        drawString(
            g2d,
            new Sprintf("Placa           %s", placa.toString()).asString(),
            10,
            250
        );
        drawString(
            g2d,
            new Sprintf("Data      %s", dataHora.data()).asString(),
            10,
            263
        );
        drawString(
            g2d,
            new Sprintf("Hora           %s", dataHora.hora()).asString(),
            10,
            276
        );
        try {
            ImageIO.write(image, "png", arquivo);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void drawString(
        final Graphics2D g2d,
        final String text,
        final int x,
        final int y
    ) {
        int newY = y;
        for (final String line : text.split("\n")) {
            g2d.drawString(line, x, newY);
            newY = newY + g2d.getFontMetrics().getHeight();
        }
    }
}
