package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;

public class FakePagamento implements Pagamento {
    private final Uuid id;
    private final LocalDateTime dataHora;
    private final double valor;

    public FakePagamento(Uuid id, LocalDateTime dataHora, double valor) {
        this.id = id;
        this.dataHora = dataHora;
        this.valor = valor;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public LocalDateTime dataHora() {
        return this.dataHora;
    }

    @Override
    public double valor() {
        return this.valor;
    }
}
