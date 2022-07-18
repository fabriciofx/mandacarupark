package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface Entrada {
    Uuid id();
    Placa placa();
    LocalDateTime dataHora();
}
