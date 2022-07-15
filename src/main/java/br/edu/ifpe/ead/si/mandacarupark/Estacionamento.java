package br.edu.ifpe.ead.si.mandacarupark;

import java.time.LocalDateTime;

public interface Estacionamento {
    Ticket entrada(Placa placa, LocalDateTime dataHora);
    Pagamento pagamento(Ticket ticket, LocalDateTime dataHora);
    void saida(Ticket ticket, Placa placa, LocalDateTime dataHora);
}
