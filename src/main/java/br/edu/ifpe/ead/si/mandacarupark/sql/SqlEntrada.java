package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SqlEntrada implements Entrada {
    private final Session session;
    private final Uuid id;

    public SqlEntrada(
        Session session,
        Uuid id
    ) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public Placa placa() {
        Placa placa = null;
        String sql = String.format(
            "SELECT placa FROM entrada WHERE id = '%s'",
            this.id
        );
        try (Connection conn = this.session.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rset = stmt.executeQuery()) {
                    if (rset.next()) {
                        placa = new Placa(rset.getString(1));
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (placa == null) {
            throw new RuntimeException("Placa inválida!");
        }
        return placa;
    }

    @Override
    public LocalDateTime dataHora() {
        LocalDateTime dataHora = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSSX"
        );
        String sql = String.format(
            "SELECT datahora FROM entrada WHERE id = '%s'",
            this.id
        );
        try (Connection conn = this.session.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rset = stmt.executeQuery()) {
                    if (rset.next()) {
                        dataHora = LocalDateTime.parse(
                            rset.getString(1),
                            formato
                        );
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (dataHora == null) {
            throw new RuntimeException("Data/Hora inválida!");
        }
        return dataHora;
    }
}
