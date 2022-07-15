package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;

public class FakePagamento implements Pagamento {
    private final Ticket ticket;
    private final double valor;

    public FakePagamento(Ticket ticket, double valor) {
        this.ticket = ticket;
        this.valor = valor;
    }

    @Override
    public double valor() {
        return this.valor;
    }

    @Override
    public Ticket valida() {
        return new FakeTicket(
            this.ticket.id(),
            this.ticket.placa(),
            this.ticket.dataHora(),
            this.valor,
            true
        );
    }
}
