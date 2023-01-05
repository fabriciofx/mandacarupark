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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public final class ScriptSql implements Script {
    private final InputStream input;

    public ScriptSql(final String filename) {
        this(
            Thread.currentThread().getContextClassLoader().
                getResourceAsStream(filename)
        );
    }

    public ScriptSql(final InputStream stream) {
        this.input = stream;
    }

    public void run(final Session session) throws Exception {
        final List<String> lines = new ArrayList<>(0);
        try (Reader reader = new InputStreamReader(this.input)) {
            try (LineNumberReader liner = new LineNumberReader(reader)) {
                while (true) {
                    final String line = liner.readLine();
                    if (line == null) {
                        break;
                    }
                    final String trimmed = line.trim();
                    if (!trimmed.isEmpty()
                        && !trimmed.startsWith("--")
                        && !trimmed.startsWith("//")) {
                        lines.add(trimmed);
                    }
                }
            }
        }
        final StringJoiner joiner = new StringJoiner(" ");
        for (final String line : lines) {
            joiner.add(line);
        }
        final String[] cmds = joiner.toString().split(";");
        try (Connection conn = session.connection()) {
            for (final String cmd : cmds) {
                try (java.sql.Statement stmt = conn.createStatement()) {
                    final String sql = cmd.trim();
                    stmt.execute(sql);
                }
            }
        }
    }
}
