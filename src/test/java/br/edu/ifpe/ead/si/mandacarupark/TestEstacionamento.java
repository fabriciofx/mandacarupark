package br.edu.ifpe.ead.si.mandacarupark;

import br.edu.ifpe.ead.si.mandacarupark.fake.FakeEstacionamento;
import br.edu.ifpe.ead.si.mandacarupark.preco.PrecoFixo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class TestEstacionamento {
    @Test
    void locacao() {
        Estacionamento estacionamento = new FakeEstacionamento(
            new PrecoFixo(new Dinheiro("5.00"))
        );
        Placa placa = new Placa("ABC1234");
        LocalDateTime agora = LocalDateTime.now();
        Ticket ticket = estacionamento.entrada(placa, agora);
        ticket = estacionamento.pagamento(ticket, agora.plusMinutes(60));
        estacionamento.saida(ticket, placa, agora.plusMinutes(70));
        Assertions.assertTrue(ticket.validado());
        Assertions.assertEquals(ticket.valor(), new Dinheiro("5.00"));
    }

    @Test()
    void sairSemValidarTicket() {
        RuntimeException exception = Assertions.assertThrows(
            RuntimeException.class,
            () -> {
                Estacionamento estacionamento = new FakeEstacionamento(
                    new PrecoFixo(new Dinheiro("5.00"))
                );
                Placa placa = new Placa("ABC1234");
                LocalDateTime agora = LocalDateTime.now();
                Ticket ticket = estacionamento.entrada(placa, agora);
                estacionamento.saida(ticket, placa, agora.plusMinutes(70));
                ticket.validado();
            }
        );
        Assertions.assertEquals("Ticket não validado!", exception.getMessage());
    }
}
