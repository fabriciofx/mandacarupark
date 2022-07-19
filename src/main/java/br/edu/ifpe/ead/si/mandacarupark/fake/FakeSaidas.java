package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Saida;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FakeSaidas implements Saidas {
    private final Map<Uuid, Saida> items;

    public FakeSaidas() {
        this(new HashMap<>());
    }

    public FakeSaidas(Map<Uuid, Saida> items) {
        this.items = items;
    }

    @Override
    public Saida saida(Ticket ticket, Placa placa, LocalDateTime dataHora) {
        Saida evento = new FakeSaida(ticket.id(), placa, dataHora);
        this.items.put(evento.id(), evento);
        return evento;
    }

    @Override
    public Iterator<Saida> iterator() {
        return this.items.values().iterator();
    }
}
