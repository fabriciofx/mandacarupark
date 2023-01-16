package com.github.fabriciofx.mandacarupark.regra;

import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.db.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.entradas.EntradasSql;
import com.github.fabriciofx.mandacarupark.estacionamento.EstacionamentoSql;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosSql;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.regras.RegrasOf;
import com.github.fabriciofx.mandacarupark.saidas.SaidasSql;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public final class TestRegraSql {
    @Test
    public void tolerancia() throws Exception {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Entradas entradas = new EntradasSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                new SaidasSql(session),
                new PagamentosSql(session),
                new RegrasOf(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new DinheiroOf("5.00"))
                )
            );
            final Placa placa = new PlacaOf("ABC1234");
            final LocalDateTime dateTime = LocalDateTime.of(2022, 8, 2, 10, 30);
            final Ticket ticket = estacionamento.entrada(
                new Uuid(),
                placa,
                new DataHoraOf(dateTime)
            );
            estacionamento.pagamento(
                ticket,
                new DataHoraOf(dateTime.plusMinutes(20))
            );
            estacionamento.saida(
                ticket,
                placa,
                new DataHoraOf(dateTime.plusMinutes(25))
            );
            Assert.assertTrue(ticket.validado());
            Assert.assertEquals(
                new DinheiroOf("0.00"),
                estacionamento.valor(
                    ticket,
                    new DataHoraOf(dateTime.plusMinutes(19))
                )
            );
        }
    }

    @Test
    public void domingoGratis() throws Exception {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Entradas entradas = new EntradasSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                new SaidasSql(session),
                new PagamentosSql(session),
                new RegrasOf(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new DinheiroOf("5.00"))
                )
            );
            final Placa placa = new PlacaOf("ABC1234");
            final LocalDateTime dateTime = LocalDateTime.of(
                2022, 7, 31, 10, 30
            );
            final Ticket ticket = estacionamento.entrada(
                new Uuid(),
                placa,
                new DataHoraOf(dateTime)
            );
            estacionamento.pagamento(
                ticket,
                new DataHoraOf(dateTime.plusMinutes(60))
            );
            estacionamento.saida(
                ticket,
                placa,
                new DataHoraOf(dateTime.plusMinutes(70))
            );
            Assert.assertTrue(ticket.validado());
            Assert.assertEquals(
                new DinheiroOf("0.00"),
                estacionamento.valor(
                    ticket,
                    new DataHoraOf(dateTime.plusMinutes(59))
                )
            );
        }
    }
}
