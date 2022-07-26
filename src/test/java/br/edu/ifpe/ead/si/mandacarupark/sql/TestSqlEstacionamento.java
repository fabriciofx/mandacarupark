package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.db.H2Server;
import br.edu.ifpe.ead.si.mandacarupark.db.RandomName;
import br.edu.ifpe.ead.si.mandacarupark.db.Server;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import br.edu.ifpe.ead.si.mandacarupark.db.SqlScript;
import br.edu.ifpe.ead.si.mandacarupark.preco.PrecoFixo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class TestSqlEstacionamento {
    @Test
    public void entrada() throws Exception {
        Server server = new H2Server(
            new RandomName().toString(),
            new SqlScript("mandacarupark.sql")
        );
        server.start();
        Session session = server.session();
        Entradas entradas = new SqlEntradas(session);
        Estacionamento estacionamento = new SqlEstacionamento(
            session,
            entradas,
            new SqlSaidas(session),
            new SqlPagamentos(session),
            new PrecoFixo(new Dinheiro("5.00"))
        );
        Placa placa = new Placa("ABC1234");
        LocalDateTime agora = LocalDateTime.now();
        Ticket ticket = estacionamento.entrada(placa, agora);
        Entrada entrada = entradas.procura(ticket.id());
        Assertions.assertEquals(entrada.placa().toString(), "ABC1234");
        server.stop();
    }

    @Test
    void locacao() throws Exception {
        Server server = new H2Server(
            new RandomName().toString(),
            new SqlScript("mandacarupark.sql")
        );
        server.start();
        Session session = server.session();
        Entradas entradas = new SqlEntradas(session);
        Estacionamento estacionamento = new SqlEstacionamento(
            session,
            entradas,
            new SqlSaidas(session),
            new SqlPagamentos(session),
            new PrecoFixo(new Dinheiro("5.00"))
        );
        Placa placa = new Placa("ABC1234");
        LocalDateTime agora = LocalDateTime.now();
        Ticket ticket = estacionamento.entrada(placa, agora);
        ticket = estacionamento.pagamento(ticket, agora.plusMinutes(60));
        estacionamento.saida(ticket, placa, agora.plusMinutes(70));
        Assertions.assertTrue(ticket.validado());
        Assertions.assertEquals(ticket.valor(), new Dinheiro("5.00"));
        server.stop();
    }

    @Test()
    void sairSemValidarTicket() {
        RuntimeException exception = Assertions.assertThrows(
            RuntimeException.class,
            () -> {
                Server server = new H2Server(
                    new RandomName().toString(),
                    new SqlScript("mandacarupark.sql")
                );
                server.start();
                Session session = server.session();
                Estacionamento estacionamento = new SqlEstacionamento(
                    session,
                    new SqlEntradas(session),
                    new SqlSaidas(session),
                    new SqlPagamentos(session),
                    new PrecoFixo(new Dinheiro("5.00"))
                );
                Placa placa = new Placa("ABC1234");
                LocalDateTime agora = LocalDateTime.now();
                Ticket ticket = estacionamento.entrada(placa, agora);
                estacionamento.saida(ticket, placa, agora.plusMinutes(70));
                ticket.validado();
                server.stop();
            }
        );
        Assertions.assertEquals("Ticket não validado!", exception.getMessage());
    }
}
