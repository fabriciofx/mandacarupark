package br.edu.ifpe.ead.si.mandacarupark.cli;

import br.edu.ifpe.ead.si.mandacarupark.Contas;
import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.conta.DomingoGratis;
import br.edu.ifpe.ead.si.mandacarupark.conta.Tolerancia;
import br.edu.ifpe.ead.si.mandacarupark.conta.ValorFixo;
import br.edu.ifpe.ead.si.mandacarupark.db.H2Server;
import br.edu.ifpe.ead.si.mandacarupark.db.RandomName;
import br.edu.ifpe.ead.si.mandacarupark.db.Server;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import br.edu.ifpe.ead.si.mandacarupark.db.SqlScript;
import br.edu.ifpe.ead.si.mandacarupark.sql.EntradasSql;
import br.edu.ifpe.ead.si.mandacarupark.sql.EstacionamentoSql;
import br.edu.ifpe.ead.si.mandacarupark.sql.PagamentosSql;
import br.edu.ifpe.ead.si.mandacarupark.sql.SaidasSql;

public class App {
    public static void main(String[] args) {
        try (
            final Server server = new H2Server(
                new RandomName().toString(),
                new SqlScript("mandacarupark.sql")
            ).start()
        ) {
            final Session session = server.session();
            final Entradas entradas = new EntradasSql(session);
            final Saidas saidas = new SaidasSql(session);
            final Pagamentos pagamentos = new PagamentosSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                saidas,
                pagamentos,
                new Contas(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new Dinheiro("5.00"))
                )
            );
            final Console console = new Consoles().console();
            new Menu(
                console,
                "-------------- MandacaruPark --------------",
                new OpcaoEntrada("1 - Entrada", console, estacionamento),
                new OpcaoSaida("2 - Saída", console, estacionamento, entradas),
                new Encerrar(
                    new OpcaoEncerrar(
                        "9 - Encerrar",
                        console,
                        entradas
                    )
                )
            ).run();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
