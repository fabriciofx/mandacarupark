package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface TabelaPrecos {
    Pagamento pagamento(Ticket ticket, LocalDateTime saida);
}
