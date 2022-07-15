package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Locacao;
import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.TabelaPrecos;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FakeEstacionamento implements Estacionamento {
    private final Map<Uuid, Locacao> locacoes;
    private final TabelaPrecos tabela;

    public FakeEstacionamento(TabelaPrecos tabela) {
        this(new HashMap<>(), tabela);
    }

    public FakeEstacionamento(
        Map<Uuid, Locacao> locacoes,
        TabelaPrecos tabela
    ) {
        this.locacoes = locacoes;
        this.tabela = tabela;
    }

    @Override
    public Ticket entrada(Placa placa, LocalDateTime dataHora) {
        Ticket ticket = new FakeTicket(placa, dataHora);
        LocalDateTime invalida = LocalDateTime.of(0, 1, 1, 0, 0, 0, 0);
        this.locacoes.put(
            ticket.id(),
            new FakeLocacao(
                ticket.id(),
                ticket.placa(),
                ticket.dataHora(),
                invalida,
                0.0
            )
        );
        return ticket;
    }

    @Override
    public Pagamento pagamento(Ticket ticket, LocalDateTime dataHora) {
        return this.tabela.pagamento(ticket, dataHora);
    }

    @Override
    public void saida(Ticket ticket, Placa placa, LocalDateTime dataHora) {
        if (!ticket.validado()) {
            throw new RuntimeException("Locação não paga!");
        }
        if (!ticket.placa().equals(placa)) {
            throw new RuntimeException("Ticket não confere com a placa!");
        }
        this.locacoes.put(
            ticket.id(),
            new FakeLocacao(
                ticket.id(),
                ticket.placa(),
                ticket.dataHora(),
                dataHora,
                ticket.valor()
            )
        );
    }
}
