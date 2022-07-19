package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface Saidas extends Iterable<Saida> {
    Saida saida(Ticket ticket, Placa placa, LocalDateTime dataHora);
}
