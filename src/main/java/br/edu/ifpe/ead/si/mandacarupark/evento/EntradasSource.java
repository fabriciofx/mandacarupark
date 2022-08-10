package br.edu.ifpe.ead.si.mandacarupark.evento;

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.data.DataStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EntradasSource implements Entradas {
    private final Entradas entradas;
    private final List<Target<Entrada>> targets;

    public EntradasSource(
        final Entradas entradas,
        final Target<Entrada>... targets
    ) {
        this.entradas = entradas;
        this.targets = Arrays.asList(targets);
    }

    @Override
    public Entrada entrada(Placa placa, LocalDateTime dataHora) {
        final Entrada entrada = entradas.entrada(placa, dataHora);
        this.targets.forEach(target -> target.notifique(entrada));
        return entrada;
    }

    @Override
    public Entrada procura(Uuid id) {
        return this.entradas.procura(id);
    }

    @Override
    public DataStream sobre() {
        return this.entradas.sobre();
    }

    @Override
    public Iterator<Entrada> iterator() {
        return this.entradas.iterator();
    }
}
