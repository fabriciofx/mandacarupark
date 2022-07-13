package br.edu.ifpe.ead.si.locacao;

import br.edu.ifpe.ead.si.Locacao;
import br.edu.ifpe.ead.si.Ticket;
import java.time.LocalDateTime;

public class DomingoGratis implements Locacao {
    private final Locacao origin;

    public DomingoGratis(Locacao locacao) {
        this.origin = locacao;
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
        return 0;
    }
}
