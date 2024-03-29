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

import com.github.fabriciofx.mandacarupark.code.CodeQr;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class ImagemCodeQr implements Imagem {
    private final Imagem origin;
    private final String texto;
    private final int x;
    private final int y;
    private final int largura;
    private final int altura;

    public ImagemCodeQr(
        final Imagem imagem,
        final String texto,
        final int x,
        final int y,
        final int largura,
        final int altura
    ) {
        this.origin = imagem;
        this.texto = texto;
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public BufferedImage imagem() {
        final BufferedImage imagem = this.origin.imagem();
        final BufferedImage qrcode = new CodeQr(
            this.texto,
            this.largura,
            this.altura
        ).imagem();
        final Graphics2D g2d = (Graphics2D) imagem.getGraphics();
        g2d.drawImage(qrcode, this.x, this.y, null);
        return imagem;
    }
}
