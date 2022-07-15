package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;

public class FakeTicket implements Ticket {
    private final Uuid id;
    private final Placa placa;
    private final LocalDateTime dataHora;
    private final double valor;
    private final boolean validado;

    public FakeTicket(
        Placa placa,
        LocalDateTime dataHora
    ) {
        this(
            new Uuid(),
            placa,
            dataHora,
            0.0,
            false
        );
    }

    public FakeTicket(
        Uuid id,
        Placa placa,
        LocalDateTime dataHora,
        double valor,
        boolean validado
    ) {
        this.id = id;
        this.placa = placa;
        this.dataHora = dataHora;
        this.valor = valor;
        this.validado = validado;
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

    @Override
    public double valor() {
        return this.valor;
    }

    @Override
    public boolean validado() {
        return this.validado;
    }
}
