package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class SqlTicket implements Ticket {
    private final Session session;
    private final Uuid id;
    private final Placa placa;
    private final LocalDateTime dataHora;

    public SqlTicket(
        Session session,
        Uuid id,
        Placa placa,
        LocalDateTime dataHora
    ) {
        this.session = session;
        this.id = id;
        this.placa = placa;
        this.dataHora = dataHora;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public Placa placa() {
        return this.placa;
    }

    @Override
    public LocalDateTime dataHora() {
        return this.dataHora;
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
        if (valor == null) {
            throw new RuntimeException("Valor inválido!");
        }
        return valor;
    }

    @Override
    public boolean validado() {
        String sql = String.format(
            "SELECT COUNT(*) FROM pagamento WHERE id = '%s'",
            this.id
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
        return quantidade != 0;
    }
}
