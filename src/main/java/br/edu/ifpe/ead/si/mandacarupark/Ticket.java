package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface Ticket {
    Uuid id();
    Placa placa();
    LocalDateTime dataHora();
    Dinheiro valor();
    boolean validado();
}
