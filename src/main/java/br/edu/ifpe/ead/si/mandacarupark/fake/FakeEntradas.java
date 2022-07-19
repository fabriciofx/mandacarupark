package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FakeEntradas implements Entradas {
    private final Map<Uuid, Entrada> items;

    public FakeEntradas() {
        this(new HashMap<>());
    }

    public FakeEntradas(Map<Uuid, Entrada> items) {
        this.items = items;
    }

    public Entrada entrada(Placa placa, LocalDateTime dataHora) {
        Entrada evento = new FakeEntrada(new Uuid(), placa, dataHora);
        this.items.put(evento.id(), evento);
        return evento;
    }

    @Override
    public Iterator<Entrada> iterator() {
        return this.items.values().iterator();
    }
}
