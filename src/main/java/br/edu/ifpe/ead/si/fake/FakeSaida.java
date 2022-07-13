package br.edu.ifpe.ead.si.fake;

import br.edu.ifpe.ead.si.Placa;
import br.edu.ifpe.ead.si.Saida;
import java.time.LocalDateTime;

public class FakeSaida implements Saida {
    private final Placa placa;
    private final LocalDateTime dataHora;

    public FakeSaida(Placa placa, LocalDateTime dataHora) {
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
