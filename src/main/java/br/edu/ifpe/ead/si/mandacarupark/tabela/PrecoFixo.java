package br.edu.ifpe.ead.si.mandacarupark.tabela;

import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.TabelaPrecos;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.fake.FakePagamento;
import java.time.LocalDateTime;

public class PrecoFixo implements TabelaPrecos {
    private final double valor;

    public PrecoFixo(double valor) {
        this.valor = valor;
    }

    @Override
    public Pagamento pagamento(Ticket ticket, LocalDateTime saida) {
        return new FakePagamento(ticket, this.valor);
    }
}
