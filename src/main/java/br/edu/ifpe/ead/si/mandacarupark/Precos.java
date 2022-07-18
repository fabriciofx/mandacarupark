package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface Precos {
    Dinheiro valor(LocalDateTime entrada, LocalDateTime saida);
}
