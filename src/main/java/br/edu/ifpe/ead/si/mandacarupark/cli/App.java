package br.edu.ifpe.ead.si.mandacarupark.cli;

import br.edu.ifpe.ead.si.mandacarupark.Contas;
import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
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
import java.time.LocalDateTime;

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
            int opcao;
            do {
                console.clear();
                console.write("----------- MandacaruPark --------------\n");
                console.write("1 - Entrada\n");
                console.write("2 - Saída\n\n");
                console.write("9 - Sair\n\n");
                console.write("Opção: ");
                opcao = Integer.parseInt(console.read());
                switch (opcao) {
                    case 1:
                        console.write("Placa: ");
                        final Placa placa = new Placa(console.read());
                        final LocalDateTime dataHora = LocalDateTime.now();
                        final Ticket ticket = estacionamento.entrada(
                            placa,
                            dataHora
                        );
                        console.write(
                            String.format(
                                "Ticket id: %s em %s\n",
                                ticket.id(),
                                dataHora.toString()
                            )
                        );
                        break;
                    case 2:
                        break;
                    case 9:
                        console.clear();
                        for (final Entrada entrada : entradas) {
                            console.write(
                                String.format(
                                    "Entrada: %s\n",
                                    entrada.placa().toString()
                                )
                            );
                        }
                    default:
                }
            } while (opcao != 9);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
