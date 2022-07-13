package br.edu.ifpe.ead.si.fake;

import br.edu.ifpe.ead.si.Entrada;
import br.edu.ifpe.ead.si.Saida;
import br.edu.ifpe.ead.si.Saidas;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;

public class FakeSaidas implements Saidas {
    @Override
    public Saida saida(Entrada entrada, LocalDateTime dataHora) {
        return new FakeSaida(entrada.placa(), dataHora);
    }

    @Override
    public Iterator<Saida> iterator() {
        return new LinkedList<Saida>().iterator();
    }
}
