package br.edu.ifpe.ead.si.fake;

import br.edu.ifpe.ead.si.Entrada;
import br.edu.ifpe.ead.si.Entradas;
import br.edu.ifpe.ead.si.Placa;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;

public class FakeEntradas implements Entradas {
    @Override
    public Entrada entrada(Placa placa, LocalDateTime dataHora) {
        return new FakeEntrada(placa, dataHora);
    }

    @Override
    public Iterator<Entrada> iterator() {
        return new LinkedList<Entrada>().iterator();
    }
}
