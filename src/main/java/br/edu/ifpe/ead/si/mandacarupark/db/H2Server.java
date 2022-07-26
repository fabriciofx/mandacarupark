package br.edu.ifpe.ead.si.mandacarupark.db;

import java.io.IOException;

public class H2Server implements Server {
    private final String dbname;
    private final SqlScript script;

    public H2Server(String dbname, SqlScript scrpt) {
        this.dbname = dbname;
        this.script = scrpt;
    }

    @Override
    public void start() throws Exception {
        this.script.run(this.session());
    }

    @Override
    public void stop() throws Exception {
        // Intended empty.
    }

    @Override
    public Session session() {
        return new NoAuthSession(
            new H2DataSource(this.dbname)
        );
    }

    @Override
    public void close() throws IOException {
        // Intended empty.
    }
}
