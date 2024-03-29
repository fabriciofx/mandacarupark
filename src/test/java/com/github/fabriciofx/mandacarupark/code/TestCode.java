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
package com.github.fabriciofx.mandacarupark.code;

import com.github.fabriciofx.mandacarupark.InputStreamAsBytes;
import org.junit.Assert;
import org.junit.Test;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class TestCode {
    @Test
    public void codebarEan13() throws Exception {
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("code/barcode-ean13.png");
        final Code barcode = new CodeBarEan13("089181452097");
        final BufferedImage imagem = barcode.imagem();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem, "png", baos);
        Assert.assertArrayEquals(
            new InputStreamAsBytes(correto).asBytes(),
            baos.toByteArray()
        );
    }

    @Test
    public void codeQr() throws Exception {
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("code/barcode-qr.png");
        final Code barcode = new CodeQr("b41d0e8c-56c2-4be6-a9e3-7afc74e3fa4a");
        final BufferedImage imagem = barcode.imagem();
        final Graphics2D g2d = imagem.createGraphics();
        final RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB
        );
        g2d.setRenderingHints(rh);
        final InputStream font = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("font/roboto-bold.ttf");
        final Font roboto13 = Font.createFont(Font.TRUETYPE_FONT, font)
            .deriveFont(13f);
        g2d.setColor(Color.black);
        g2d.setFont(roboto13);
        g2d.drawString("Hello World", 20, 20);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem, "png", baos);
        Assert.assertArrayEquals(
            new InputStreamAsBytes(correto).asBytes(),
            baos.toByteArray()
        );
    }
}
