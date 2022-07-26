package br.edu.ifpe.ead.si.mandacarupark.db;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SqlScript implements Script {
    private final InputStream input;

    public SqlScript(final String filename) {
        this(
            Thread.currentThread().getContextClassLoader().
                getResourceAsStream(filename)
        );
    }

    public SqlScript(final InputStream stream) {
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
