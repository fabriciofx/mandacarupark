package br.edu.ifpe.ead.si;

import java.time.LocalDateTime;

public interface Entradas extends Iterable<Entrada> {
    Entrada entrada(Placa placa, LocalDateTime dataHora);
}
