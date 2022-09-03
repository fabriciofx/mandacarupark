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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class ImagemTexto implements Imagem {
    private final Imagem origin;
    private final Font fonte;
    private final Color color;
    private final String texto;
    private final int x;
    private final int y;

    public ImagemTexto(
        final Imagem imagem,
        final Font fonte,
        final Color cor,
        final String texto,
        final int x,
        final int y
    ) {
        this.origin = imagem;
        this.fonte = fonte;
        this.color = cor;
        this.texto = texto;
        this.x = x;
        this.y = y;
    }

    @Override
    public BufferedImage imagem() {
        final BufferedImage imagem = this.origin.imagem();
        final Graphics2D g2d = imagem.createGraphics();
        final RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB
        );
        g2d.setRenderingHints(rh);
        g2d.setFont(this.fonte);
        g2d.setColor(this.color);
        drawString(g2d, this.texto, this.x, this.y);
        return imagem;
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
