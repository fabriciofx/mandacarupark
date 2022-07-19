package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FakePagamentos implements Pagamentos {
    private final Map<Uuid, Pagamento> items;

    public FakePagamentos() {
        this(new HashMap<>());
    }

    public FakePagamentos(Map<Uuid, Pagamento> items) {
        this.items = items;
    }

    @Override
    public Pagamento pagamento(
        Ticket ticket,
        LocalDateTime dataHora,
        Dinheiro valor
    ) {
        Pagamento evento = new FakePagamento(ticket.id(), dataHora, valor);
        this.items.put(ticket.id(), evento);
        return evento;
    }

    @Override
    public Pagamento get(Uuid id) {
        return this.items.get(id);
    }

    @Override
    public Iterator<Pagamento> iterator() {
        return this.items.values().iterator();
    }
}
