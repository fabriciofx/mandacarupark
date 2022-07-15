package br.edu.ifpe.ead.si.mandacarupark;

import br.edu.ifpe.ead.si.mandacarupark.fake.FakeEstacionamento;
import br.edu.ifpe.ead.si.mandacarupark.tabela.PorHora;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class TestEstacionamento {
    @Test
    void locacao() {
        Estacionamento estacionamento = new FakeEstacionamento(
            new PorHora(5.00)
        );
        Placa placa = new Placa("ABC1234");
        LocalDateTime agora = LocalDateTime.now();
        Ticket ticket = estacionamento.entrada(placa, agora);
        Pagamento pagamento = estacionamento.pagamento(
            ticket,
            agora.plusMinutes(60)
        );
        ticket = pagamento.valida();
        estacionamento.saida(ticket, placa, agora.plusMinutes(70));
        Assertions.assertTrue(ticket.validado());
        Assertions.assertEquals(ticket.valor(), 5.00);
    }
}
