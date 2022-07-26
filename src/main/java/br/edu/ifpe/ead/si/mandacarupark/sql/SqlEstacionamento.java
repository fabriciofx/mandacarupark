package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Precos;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.time.LocalDateTime;

public class SqlEstacionamento implements Estacionamento {
    private final Session session;
    private final Entradas entradas;
    private final Saidas saidas;
    private final Pagamentos pagamentos;
    private final Precos precos;

    public SqlEstacionamento(
        Session session,
        Entradas entradas,
        Saidas saidas,
        Pagamentos pagamentos,
        Precos precos
    ) {
        this.session = session;
        this.entradas = entradas;
        this.saidas = saidas;
        this.pagamentos = pagamentos;
        this.precos = precos;
    }

    @Override
    public Ticket entrada(Placa placa, LocalDateTime dataHora) {
        Entrada entrada = this.entradas.entrada(placa, dataHora);
        return new SqlTicket(this.session, entrada.id(), placa, dataHora);
    }

    @Override
    public Ticket pagamento(Ticket ticket, LocalDateTime dataHora) {
        Dinheiro valor = this.precos.valor(ticket.dataHora(), dataHora);
        this.pagamentos.pagamento(ticket, dataHora, valor);
        return new SqlTicket(
            this.session,
            ticket.id(),
            ticket.placa(),
            ticket.dataHora()
        );
    }

    @Override
    public void saida(Ticket ticket, Placa placa, LocalDateTime dataHora) {
        if (!ticket.validado()) {
            throw new RuntimeException("Ticket não validado!");
        }
        if (!ticket.placa().equals(placa)) {
            throw new RuntimeException("Ticket não confere com a placa!");
        }
        this.saidas.saida(ticket, placa, dataHora);
    }
}
