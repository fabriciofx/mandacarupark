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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class ImagemPapel implements Imagem {
    private final Color cor;
    private final int largura;
    private final int altura;

    public ImagemPapel(final int largura, final int altura) {
        this(Color.WHITE, largura, altura);
    }

    public ImagemPapel(final Color cor, final int largura, final int altura) {
        this.cor = cor;
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public BufferedImage imagem() {
        final BufferedImage imagem = new BufferedImage(
            this.largura,
            this.altura,
            BufferedImage.TYPE_INT_RGB
        );
        final Graphics2D g2d = imagem.createGraphics();
        g2d.setPaint(this.cor);
        g2d.fillRect(0, 0, imagem.getWidth(), imagem.getHeight());
        return imagem;
    }
}
