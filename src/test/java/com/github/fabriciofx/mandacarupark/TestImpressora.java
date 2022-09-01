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
package com.github.fabriciofx.mandacarupark;

import com.github.fabriciofx.mandacarupark.fake.PagamentosFake;
import com.github.fabriciofx.mandacarupark.fake.TicketFake;
import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class TestImpressora {
    @Test
    public void impresso() throws IOException {
        final InputStream correto = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("ticket-correto.png");
        final File file = new File("ticket.png");
        new Impressora().imprima(
            new TicketFake(
                new PagamentosFake(),
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
                new Placa("ABC1234"),
                new DataHora("2022-07-21 12:01:15")
            ),
            file
        );
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
