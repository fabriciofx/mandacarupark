package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface Pagamento {
    Uuid id();
    LocalDateTime dataHora();
    Dinheiro valor();
}
