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
package com.github.fabriciofx.mandacarupark.ticket;

import com.github.fabriciofx.mandacarupark.InputStreamAsBytes;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.contas.ContasFake;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosFake;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.ticket.imagem.Imagem;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemCodeQr;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemPapel;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTexto;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTicket;
import org.junit.Assert;
import org.junit.Test;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class TestImagem {
    @Test
    public void papel() throws Exception {
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("imagem/imagem-papel.png");
        final Imagem imagem = new ImagemPapel(150, 300);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem.imagem(), "png", baos);
        Assert.assertArrayEquals(
            new InputStreamAsBytes(correto).asBytes(),
            baos.toByteArray()
        );
    }

    @Test
    public void texto() throws Exception {
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("imagem/imagem-texto.png");
        final InputStream font = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("font/roboto-bold.ttf");
        final Font roboto = Font.createFont(Font.TRUETYPE_FONT, font)
            .deriveFont(13f);
        final Imagem imagem = new ImagemTexto(
            new ImagemPapel(150, 300),
            roboto,
            Color.BLACK,
            "MANDACARUPARK",
            12,
            26
        );
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem.imagem(), "png", baos);
        Assert.assertArrayEquals(
            new InputStreamAsBytes(correto).asBytes(),
            baos.toByteArray()
        );
    }

    @Test
    public void qrcode() throws Exception {
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("imagem/imagem-qrcode.png");
        final Imagem imagem = new ImagemCodeQr(
            new ImagemPapel(150, 300),
            new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944").toString(),
            0,
            0,
            100,
            150
        );
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem.imagem(), "png", baos);
        Assert.assertArrayEquals(
            new InputStreamAsBytes(correto).asBytes(),
            baos.toByteArray()
        );
    }

    @Test
    public void ticket() throws Exception {
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("imagem/imagem-ticket.png");
        final Imagem imagem = new ImagemTicket(
            new ImagemPapel(150, 300),
            new TicketFake(
                new PagamentosFake(),
                new ContasFake(),
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
                new PlacaOf("ABC1234"),
                new DataHoraOf("2022-07-21 12:01:15")
            )
        );
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem.imagem(), "png", baos);
        Assert.assertArrayEquals(
            new InputStreamAsBytes(correto).asBytes(),
            baos.toByteArray()
        );
    }

    @Test
    public void completo() throws Exception {
        final InputStream font = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("font/roboto-bold.ttf");
        final Font roboto13 = Font.createFont(Font.TRUETYPE_FONT, font)
            .deriveFont(13f);
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("imagem/imagem-completo.png");
        final Ticket ticket = new TicketFake(
            new PagamentosFake(),
            new ContasFake(),
            new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
            new PlacaOf("ABC1234"),
            new DataHoraOf("2022-07-21 12:01:15")
        );
        final Imagem imagem = new ImagemTicket(
            new ImagemCodeQr(
                new ImagemTexto(
                    new ImagemPapel(150, 300),
                    roboto13,
                    Color.BLACK,
                    "MANDACARUPARK",
                    12,
                    26
                ),
                ticket.id().toString(),
                10,
                50,
                125,
                200
            ),
            ticket
        );
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem.imagem(), "png", baos);
        Assert.assertArrayEquals(
            new InputStreamAsBytes(correto).asBytes(),
            baos.toByteArray()
        );
    }
}
