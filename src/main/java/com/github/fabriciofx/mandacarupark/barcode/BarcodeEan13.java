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
package com.github.fabriciofx.mandacarupark.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import java.awt.image.BufferedImage;

public final class BarcodeEan13 implements Barcode {
    private final String text;
    private final int width;
    private final int height;

    public BarcodeEan13(final String text) {
        this(text, 300, 150);
    }

    public BarcodeEan13(final String text, final int width, final int height) {
        this.text = text;
        this.width = width;
        this.height = height;
    }

    @Override
    public BufferedImage image() {
        final EAN13Writer writer = new EAN13Writer();
        final BitMatrix bitMatrix = writer.encode(
            this.text,
            BarcodeFormat.EAN_13,
            this.width,
            this.height
        );
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @Override
    public String text() {
        return this.text;
    }
}
