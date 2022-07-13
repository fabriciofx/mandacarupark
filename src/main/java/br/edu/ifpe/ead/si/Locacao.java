package br.edu.ifpe.ead.si;

import java.time.LocalDateTime;

public interface Locacao {
    Ticket ticket();
    LocalDateTime entrada();
    LocalDateTime saida();
    double valor();
}
