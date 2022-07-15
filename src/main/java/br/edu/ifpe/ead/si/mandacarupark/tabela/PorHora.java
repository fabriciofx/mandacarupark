package br.edu.ifpe.ead.si.mandacarupark.tabela;

import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Periodo;
import br.edu.ifpe.ead.si.mandacarupark.TabelaPrecos;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.fake.FakePagamento;
import java.time.LocalDateTime;

public class PorHora implements TabelaPrecos {
    private final double valor;

    public PorHora(double valor) {
        this.valor = valor;
    }

    @Override
    public Pagamento pagamento(Ticket ticket, LocalDateTime saida) {
        long minutos = new Periodo(ticket.dataHora(), saida).minutos();
        long horas = minutos / 60;
        return new FakePagamento(ticket, horas * this.valor);
    }
}
