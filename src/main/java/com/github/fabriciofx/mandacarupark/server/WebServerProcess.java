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
package com.github.fabriciofx.mandacarupark.server;

import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.web.http.TkRoutes;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public final class WebServerProcess extends Thread {
    private final Estacionamento estacionamento;
    private final int port;
    private final CountDownLatch latch;

    public WebServerProcess(
        final Estacionamento estacionamento,
        final int port,
        final CountDownLatch latch
    ) {
        this.estacionamento = estacionamento;
        this.port = port;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            new FtBasic(
                new TkRoutes(this.estacionamento),
                this.port
            ).start(
                () -> {
                    this.latch.countDown();
                    return Exit.NEVER.ready();
                }
            );
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
