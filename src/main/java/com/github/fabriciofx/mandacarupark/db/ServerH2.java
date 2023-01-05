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
package com.github.fabriciofx.mandacarupark.db;

import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.db.stmt.Insert;
import java.io.IOException;

public final class ServerH2 implements Server {
    private final Session session;
    private final ScriptSql script;

    public ServerH2(final Session session, final ScriptSql scrpt) {
        this.session = session;
        this.script = scrpt;
    }

    @Override
    public void start() throws Exception {
        this.script.run(this.session);
    }

    @Override
    public void stop() throws Exception {
        new Insert(this.session, "SHUTDOWN").execute();
    }

    @Override
    public void close() throws IOException {
        try {
            this.stop();
        } catch(final Exception ex) {
            throw new IOException(ex);
        }
    }
}
