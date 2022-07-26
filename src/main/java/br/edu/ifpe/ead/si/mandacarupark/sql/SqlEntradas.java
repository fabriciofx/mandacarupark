package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Iterator;

public class SqlEntradas implements Entradas {
    private final Session session;

    public SqlEntradas(Session session) {
        this.session = session;
    }

    @Override
    public Entrada entrada(Placa placa, LocalDateTime dataHora) {
        Uuid id = new Uuid();
        String sql = String.format(
            "INSERT INTO entrada (id, placa, datahora) VALUES ('%s', '%s', " +
                "'%s')",
            id,
            placa,
            dataHora
        );
        try (Connection conn = this.session.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.execute();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return new SqlEntrada(this.session, id);
    }

    @Override
    public Entrada procura(Uuid id) {
        String sql = String.format(
            "SELECT COUNT(*) FROM entrada WHERE id = '%s'",
            id
        );
        int quantidade = 0;
        try (Connection conn = this.session.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rset = stmt.executeQuery()) {
                    if (rset.next()) {
                        quantidade = rset.getInt(1);
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (quantidade == 0) {
            throw new RuntimeException(
                String.format(
                    "Entrada com id '%s' não encontrada!",
                    id
                )
            );
        }
        return new SqlEntrada(this.session, id);
    }

    @Override
    public Iterator<Entrada> iterator() {
        return null;
    }
}
