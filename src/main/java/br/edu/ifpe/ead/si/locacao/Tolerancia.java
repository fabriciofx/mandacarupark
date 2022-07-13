package br.edu.ifpe.ead.si.locacao;

import br.edu.ifpe.ead.si.Locacao;
import br.edu.ifpe.ead.si.Periodo;
import br.edu.ifpe.ead.si.Ticket;
import java.time.LocalDateTime;

public class Tolerancia implements Locacao {
    private final Locacao origin;
    private final int tempo;

    public Tolerancia(Locacao locacao, int tempo) {
        this.origin = locacao;
        this.tempo = tempo;
    }

    @Override
    public Ticket ticket() {
        return this.origin.ticket();
    }

    @Override
    public LocalDateTime entrada() {
        return this.origin.entrada();
    }

    @Override
    public LocalDateTime saida() {
        return this.origin.saida();
    }

    @Override
    public double valor() {
        double valor;
        Periodo periodo = new Periodo(this.entrada(), this.saida());
        if (periodo.minutos() <= this.tempo) {
            valor = 0;
        } else {
            valor = this.origin.valor();
        }
        return valor;
    }
}
