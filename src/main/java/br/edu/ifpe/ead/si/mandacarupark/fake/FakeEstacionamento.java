package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Precos;
import br.edu.ifpe.ead.si.mandacarupark.Saida;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FakeEstacionamento implements Estacionamento {
    private final Entradas entradas;
    private final Map<Uuid, Saida> saidas;
    private final Map<Uuid, Pagamento> pagamentos;
    private final Precos precos;

    public FakeEstacionamento(
        Entradas entradas,
        Map<Uuid, Saida> saidas,
        Map<Uuid, Pagamento> pagamentos,
        Precos precos
    ) {
        this.entradas = entradas;
        this.saidas = saidas;
        this.pagamentos = pagamentos;
        this.precos = precos;
    }

    public FakeEstacionamento(Precos precos) {
        this(
            new FakeEntradas(),
            new HashMap<>(),
            new HashMap<>(),
            precos
        );
    }

    @Override
    public Ticket entrada(Placa placa, LocalDateTime dataHora) {
        Entrada entrada = entradas.entrada(placa, dataHora);
        Ticket ticket = new FakeTicket(
            this.pagamentos,
            entrada.id(),
            placa,
            dataHora,
            new Dinheiro("0.0")
        );
        return ticket;
    }

    @Override
    public Ticket pagamento(Ticket ticket, LocalDateTime dataHora) {
        Dinheiro valor = this.precos.valor(ticket.dataHora(), dataHora);
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
