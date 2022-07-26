package br.edu.ifpe.ead.si.mandacarupark.db;

import javax.sql.DataSource;
import java.sql.Connection;

public class NoAuthSession implements Session {
    private final DataSource source;

    public NoAuthSession(final DataSource source) {
        this.source = source;
    }

    @Override
    public Connection connection() throws Exception {
        return this.source.getConnection();
    }
}
