package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface Saida {
    Uuid id();
    Placa placa();
    LocalDateTime dataHora();
}
