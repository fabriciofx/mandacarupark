package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Locacao;
import br.edu.ifpe.ead.si.mandacarupark.Locacoes;
import br.edu.ifpe.ead.si.mandacarupark.Periodo;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlLocacoes implements Locacoes {
    private final Session session;
    private final Periodo periodo;

    public SqlLocacoes(Session session, Periodo periodo) {
        this.session = session;
        this.periodo = periodo;
    }

    @Override
    public Iterator<Locacao> iterator() {
        List<Locacao> items = new ArrayList<>();
        String sql = String.format(
            "SELECT * FROM locacao WHERE entrada >= '%s' AND saida <= '%s'",
            this.periodo.inicio(), this.periodo.termino()
        );
        try (Connection conn = this.session.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rset = stmt.executeQuery()) {
                    if (rset.next()) {
                        items.add(
                            new SqlLocacao(
                                this.session,
                                new Uuid(rset.getString(1))
                            )
                        );
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return items.iterator();
    }
}
