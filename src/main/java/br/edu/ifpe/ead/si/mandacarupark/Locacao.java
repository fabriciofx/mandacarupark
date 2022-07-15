package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface Locacao {
    Placa placa();
    LocalDateTime entrada();
    LocalDateTime saida();
    double valor();
}
