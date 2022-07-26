package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Locacao;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SqlLocacao implements Locacao {
    private final Session session;
    private final Uuid id;

    public SqlLocacao(Session session, Uuid id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Placa placa() {
        Placa placa = null;
        String sql = String.format(
            "SELECT placa FROM locacao WHERE id = '%s'",
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
    public LocalDateTime entrada() {
        LocalDateTime dataHora = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSSX"
        );
        String sql = String.format(
            "SELECT entrada FROM locacao WHERE id = '%s'",
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

    @Override
    public LocalDateTime saida() {
        LocalDateTime dataHora = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSSX"
        );
        String sql = String.format(
            "SELECT saida FROM locacao WHERE id = '%s'",
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
}
