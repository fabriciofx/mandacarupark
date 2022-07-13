package br.edu.ifpe.ead.si.fake;

import br.edu.ifpe.ead.si.Entrada;
import br.edu.ifpe.ead.si.Placa;
import java.time.LocalDateTime;

public class FakeEntrada implements Entrada {
    private final Placa placa;
    private final LocalDateTime dataHora;

    public FakeEntrada(Placa placa, LocalDateTime dataHora) {
        this.placa = placa;
        this.dataHora = dataHora;
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
