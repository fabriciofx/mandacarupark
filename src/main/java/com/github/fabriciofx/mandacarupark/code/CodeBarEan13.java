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
package com.github.fabriciofx.mandacarupark.code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import java.awt.image.BufferedImage;

public final class CodeBarEan13 implements Code {
    private final String texto;
    private final int largura;
    private final int altura;

    public CodeBarEan13(final String texto) {
        this(texto, 300, 150);
    }

    public CodeBarEan13(
        final String texto,
        final int largura,
        final int altura
    ) {
        this.texto = texto;
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public BufferedImage imagem() {
        final EAN13Writer writer = new EAN13Writer();
        final BitMatrix bitMatrix = writer.encode(
            this.texto,
            BarcodeFormat.EAN_13,
            this.largura,
            this.altura
        );
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @Override
    public String texto() {
        return this.texto;
    }
}
