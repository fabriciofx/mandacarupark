package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Saida;
import br.edu.ifpe.ead.si.mandacarupark.Precos;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FakeEstacionamento implements Estacionamento {
    private final Map<Uuid, Entrada> entradas;
    private final Map<Uuid, Saida> saidas;
    private final Map<Uuid, Pagamento> pagamentos;
    private final Precos precos;

    public FakeEstacionamento(Precos precos) {
        this.entradas = new HashMap<>();
        this.saidas = new HashMap<>();
        this.pagamentos = new HashMap<>();
        this.precos = precos;
    }

    @Override
    public Ticket entrada(Placa placa, LocalDateTime dataHora) {
        Ticket ticket = new FakeTicket(this.pagamentos, placa, dataHora);
        this.entradas.put(
            ticket.id(),
            new FakeEntrada(
                ticket.id(),
                ticket.placa(),
                dataHora
            )
        );
        return ticket;
    }

    @Override
    public Ticket pagamento(Ticket ticket, LocalDateTime dataHora) {
        double valor = this.precos.valor(ticket.dataHora(), dataHora);
        this.pagamentos.put(
            ticket.id(),
            new FakePagamento(
                ticket.id(),
                dataHora,
                valor
            )
        );
        return new FakeTicket(
            this.pagamentos,
            ticket.id(),
            ticket.placa(),
            ticket.dataHora(),
            valor
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
        this.saidas.put(
            ticket.id(),
            new FakeSaida(
                ticket.id(),
                ticket.placa(),
                dataHora
            )
        );
    }
}
