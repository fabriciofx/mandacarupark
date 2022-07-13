package br.edu.ifpe.ead.si;

import java.time.LocalDateTime;

public interface Saidas extends Iterable<Saida> {
    Saida saida(Entrada entrada, LocalDateTime dataHora);
}
