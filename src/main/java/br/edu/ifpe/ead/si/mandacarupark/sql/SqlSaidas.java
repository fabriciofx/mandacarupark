package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Saida;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.time.LocalDateTime;
import java.util.Iterator;

public class SqlSaidas implements Saidas {
    private final Session session;

    public SqlSaidas(Session session) {
        this.session = session;
    }

    @Override
    public Saida saida(Ticket ticket, Placa placa, LocalDateTime dataHora) {
        return new SqlSaida(this.session, ticket.id());
    }

    @Override
    public Saida procura(Uuid id) {
        return null;
    }

    @Override
    public Iterator<Saida> iterator() {
        return null;
    }
}
