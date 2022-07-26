package br.edu.ifpe.ead.si.mandacarupark.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Insert {
    private final Session session;
    private final String query;

    public Insert(Session session, String query) {
        this.session = session;
        this.query = query;
    }

    public boolean execute() {
        try (Connection conn = this.session.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                return stmt.execute();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
