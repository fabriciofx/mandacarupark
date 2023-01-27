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
package com.github.fabriciofx.mandacarupark.web.server;

import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.db.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.web.http.TkRoutes;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import java.util.concurrent.CountDownLatch;

public final class WebServerProcess extends Thread {
    private final Session session;
    private final int port;
    private final CountDownLatch cdl;

    public WebServerProcess(
        final Session session,
        final int port,
        final CountDownLatch cdl
    ) {
        this.session = session;
        this.port = port;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try (
            final Server server = new ServerH2(
                this.session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            new FtBasic(
                new TkRoutes(this.session),
                this.port
            ).start(
                () -> {
                    this.cdl.countDown();
                    return Exit.NEVER.ready();
                }
            );
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
