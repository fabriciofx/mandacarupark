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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class ImagemPapel implements Imagem {
    private final Color cor;
    private final int width;
    private final int height;

    public ImagemPapel(final int width, final int height) {
        this(Color.WHITE, width, height);
    }

    public ImagemPapel(final Color cor, final int width, final int height) {
        this.cor = cor;
        this.width = width;
        this.height = height;
    }

    @Override
    public BufferedImage imagem() {
        final BufferedImage imagem = new BufferedImage(
            this.width,
            this.height,
            BufferedImage.TYPE_INT_RGB
        );
        final Graphics2D g2d = imagem.createGraphics();
        g2d.setPaint(this.cor);
        g2d.fillRect(0, 0, imagem.getWidth(), imagem.getHeight());
        return imagem;
    }
}
