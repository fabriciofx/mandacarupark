package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Iterator;

public class SqlPagamentos implements Pagamentos {
    private final Session session;

    public SqlPagamentos(Session session) {
        this.session = session;
    }

    @Override
    public Pagamento pagamento(
        Ticket ticket,
        LocalDateTime dataHora,
        Dinheiro valor
    ) {
        String sql = String.format(
            "INSERT INTO pagamento (id, datahora, valor) VALUES ('%s', '%s', '%s')",
            ticket.id(),
            dataHora,
            valor.quantia()
        );
        try (Connection conn = this.session.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.execute();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return new SqlPagamento(this.session, ticket.id());
    }

    @Override
    public Pagamento procura(Uuid id) {
        return null;
    }

    @Override
    public Iterator<Pagamento> iterator() {
        return null;
    }
}
