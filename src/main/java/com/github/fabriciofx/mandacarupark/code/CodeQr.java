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
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public final class CodeQr implements Code {
    private final String texto;
    private final int largura;
    private final int altura;

    public CodeQr(final String texto) {
        this(texto, 150, 300);
    }

    public CodeQr(final String texto, final int largura, final int altura) {
        this.texto = texto;
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public BufferedImage imagem() {
        final QRCodeWriter writer = new QRCodeWriter();
        final BitMatrix bitMatrix;
        try {
            bitMatrix = writer.encode(
                this.texto,
                BarcodeFormat.QR_CODE,
                this.largura,
                this.altura,
                Map.of(EncodeHintType.MARGIN, 0)
            );
        } catch (final WriterException ex) {
            throw new RuntimeException(ex);
        }
        return crop(MatrixToImageWriter.toBufferedImage(bitMatrix));
    }

    @Override
    public String texto() {
        return this.texto;
    }

    /*
     * Mesmo com a margem do QRcode configurada para 0, ainda assim há uma
     * grande margem branca na imagem do QRcode. Assim, é preciso cortar
     * "crop" a imagem, para diminuir esta margem.
     */
    private BufferedImage crop(final BufferedImage image) {
        // A posição Y vai para 25% (1/4) da largura
        final int newY = (int) (image.getWidth() * 0.25);
        // A altura vai para 66% (1/3) da altura anterior
        final int newHeight = (int) (image.getHeight() * 0.66);
        final BufferedImage sub = image.getSubimage(
            0,
            newY,
            image.getWidth(),
            newHeight
        );
        final BufferedImage crop = new BufferedImage(
            sub.getWidth(),
            sub.getHeight(),
            BufferedImage.TYPE_INT_RGB
        );
        final Graphics2D g2dCrop = crop.createGraphics();
        g2dCrop.drawImage(sub, 0, 0, null);
        return crop;
    }
}
