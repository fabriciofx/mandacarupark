package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;

public class FakeEntrada implements Entrada {
    private final Uuid id;
    private final Placa placa;
    private final LocalDateTime dataHora;

    public FakeEntrada(Uuid id, Placa placa, LocalDateTime dataHora) {
        this.id = id;
        this.placa = placa;
        this.dataHora = dataHora;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public Placa placa() {
        return this.placa;
    }

    @Override
    public LocalDateTime dataHora() {
        return this.dataHora;
    }
}
