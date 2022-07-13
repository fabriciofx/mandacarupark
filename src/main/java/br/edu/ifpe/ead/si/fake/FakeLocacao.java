package br.edu.ifpe.ead.si.fake;

import br.edu.ifpe.ead.si.Entrada;
import br.edu.ifpe.ead.si.Locacao;
import br.edu.ifpe.ead.si.Saida;
import br.edu.ifpe.ead.si.Ticket;
import java.time.LocalDateTime;

public class FakeLocacao implements Locacao {
    private final Entrada entrada;
    private final Saida saida;
    private final double valor;

    public FakeLocacao(Entrada entrada, Saida saida, double valor) {
        this.entrada = entrada;
        this.saida = saida;
        this.valor = valor;
    }

    @Override
    public Ticket ticket() {
        return new FakeTicket(
            this.entrada.placa(),
            this.entrada.dataHora(),
            false
        );
    }

    @Override
    public LocalDateTime entrada() {
        return this.entrada.dataHora();
    }

    @Override
    public LocalDateTime saida() {
        return this.saida.dataHora();
    }

    @Override
    public double valor() {
        return this.valor;
    }
}
