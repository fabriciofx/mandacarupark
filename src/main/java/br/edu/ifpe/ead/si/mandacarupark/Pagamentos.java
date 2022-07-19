package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface Pagamentos extends Iterable<Pagamento> {
    Pagamento pagamento(Ticket ticket, LocalDateTime dataHora, Dinheiro valor);
    Pagamento get(Uuid id);
}
