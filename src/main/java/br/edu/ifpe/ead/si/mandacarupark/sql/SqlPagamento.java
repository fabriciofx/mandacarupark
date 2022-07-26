package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SqlPagamento implements Pagamento {
    private final Session session;
    private final Uuid id;

    public SqlPagamento(Session session, Uuid id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public LocalDateTime dataHora() {
        LocalDateTime dataHora;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSSX"
        );
        String sql = String.format(
            "SELECT datahora FROM pagamento WHERE id = '%s'",
            this.id
        );
        try (Connection conn = this.session.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rset = stmt.executeQuery()) {
                    dataHora = LocalDateTime.parse(
                        rset.getString(1),
                        formato
                    );
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return dataHora;
    }

    @Override
    public Dinheiro valor() {
        Dinheiro valor = null;
        String sql = String.format(
            "SELECT valor FROM pagamento WHERE id = '%s'",
            this.id
        );
        try (Connection conn = this.session.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rset = stmt.executeQuery()) {
                    if (rset.next()) {
                        valor = new Dinheiro(rset.getBigDecimal(1));
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return valor;
    }
}
