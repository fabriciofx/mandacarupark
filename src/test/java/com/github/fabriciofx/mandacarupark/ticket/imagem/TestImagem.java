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
package com.github.fabriciofx.mandacarupark.ticket.imagem;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.Uuid;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosFake;
import com.github.fabriciofx.mandacarupark.ticket.TicketFake;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemCodeQr;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemPapel;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemParaArquivo;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTexto;
import com.github.fabriciofx.mandacarupark.ticket.imagem.ImagemTicket;
import org.junit.Assert;
import org.junit.Test;
import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class TestImagem {
    @Test
    public void papel() throws IOException {
        final String filename = "imagem-papel.png";
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(filename);
        final File file = new File(filename);
        new ImagemParaArquivo(
            new ImagemPapel(150, 300),
            filename
        ).salva();
        final byte[] atual = Files.readAllBytes(file.toPath());
        final byte[] esperado = toBytes(correto);
        file.delete();
        Assert.assertArrayEquals(esperado, atual);
    }

    @Test
    public void texto() throws IOException {
        final String filename = "imagem-texto.png";
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(filename);
        final File file = new File(filename);
        new ImagemParaArquivo(
            new ImagemTexto(
                new ImagemPapel(150, 300),
                new Font("Lucida Sans Unicode", Font.PLAIN, 13),
                Color.BLACK,
                "MANDACARUPARK",
                12,
                26
            ),
            filename
        ).salva();
        final byte[] atual = Files.readAllBytes(file.toPath());
        final byte[] esperado = toBytes(correto);
        file.delete();
        Assert.assertArrayEquals(esperado, atual);
    }

    @Test
    public void qrcode() throws IOException {
        final String filename = "imagem-qrcode.png";
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(filename);
        final File file = new File(filename);
        new ImagemParaArquivo(
            new ImagemCodeQr(
                new ImagemPapel(150, 300),
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944").toString(),
                0,
                0,
                100,
                150
            ),
            filename
        ).salva();
        final byte[] atual = Files.readAllBytes(file.toPath());
        final byte[] esperado = toBytes(correto);
        file.delete();
        Assert.assertArrayEquals(esperado, atual);
    }

    @Test
    public void ticket() throws IOException {
        final String filename = "imagem-ticket.png";
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(filename);
        final File file = new File(filename);
        new ImagemParaArquivo(
            new ImagemTicket(
                new ImagemPapel(150, 300),
                new TicketFake(
                    new PagamentosFake(),
                    new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
                    new PlacaOf("ABC1234"),
                    new DataHora("2022-07-21 12:01:15")
                )
            ),
            filename
        ).salva();
        final byte[] atual = Files.readAllBytes(file.toPath());
        final byte[] esperado = toBytes(correto);
        file.delete();
        Assert.assertArrayEquals(esperado, atual);
    }

    @Test
    public void completo() throws IOException {
        final String filename = "imagem-completo.png";
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(filename);
        final File file = new File(filename);
        final Ticket ticket = new TicketFake(
            new PagamentosFake(),
            new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
            new PlacaOf("ABC1234"),
            new DataHora("2022-07-21 12:01:15")
        );
        new ImagemParaArquivo(
            new ImagemTicket(
                new ImagemCodeQr(
                    new ImagemTexto(
                        new ImagemPapel(150, 300),
                        new Font("Lucida Sans Unicode", Font.PLAIN, 13),
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
            ),
            filename
        ).salva();
        final byte[] atual = Files.readAllBytes(file.toPath());
        final byte[] esperado = toBytes(correto);
        file.delete();
        Assert.assertArrayEquals(esperado, atual);
    }

    private byte[] toBytes(final InputStream stream) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] buf = new byte[1024];
        while (true) {
            final int read = stream.read(buf);
            if (read < 0) {
                break;
            }
            baos.write(buf, 0, read);
        }
        return baos.toByteArray();
    }
}
