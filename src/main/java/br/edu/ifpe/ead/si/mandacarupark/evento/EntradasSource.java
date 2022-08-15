package br.edu.ifpe.ead.si.mandacarupark.evento;

import br.edu.ifpe.ead.si.mandacarupark.DataHora;
import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.data.DataStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EntradasSource implements Entradas {
    private final Entradas origin;
    private final List<Target<Entrada>> targets;

    public EntradasSource(
        final Entradas entradas,
        final Target<Entrada>... targets
    ) {
        this.origin = entradas;
        this.targets = Arrays.asList(targets);
    }

    @Override
    public Entrada entrada(Placa placa, DataHora dataHora) {
        final Entrada entrada = this.origin.entrada(placa, dataHora);
        this.targets.forEach(target -> target.notifique(entrada));
        return entrada;
    }

    @Override
    public Entrada procura(Uuid id) {
        return this.origin.procura(id);
    }

    @Override
    public Ticket ticket(Uuid id) {
        return this.origin.ticket(id);
    }

    @Override
    public DataStream sobre() {
        return this.origin.sobre();
    }

    @Override
    public Iterator<Entrada> iterator() {
        return this.origin.iterator();
    }
}
