package br.edu.ifpe.ead.si.mandacarupark.cli;

import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;

public class OpcaoSaida implements Opcao {
    private final String mensagem;
    private final Console console;
    private final Estacionamento estacionamento;
    private final Entradas entradas;

    public OpcaoSaida(
        final String mensagem,
        final Console console,
        final Estacionamento estacionamento,
        final Entradas entradas
    ) {
        this.mensagem = mensagem;
        this.console = console;
        this.estacionamento = estacionamento;
        this.entradas = entradas;
    }

    @Override
    public int numero() {
        return 2;
    }

    @Override
    public void mensagem() {
        this.console.write(this.mensagem + "\n");
    }

    @Override
    public void run() {
        this.console.write(" Placa: ");
        final Placa placa = new Placa(this.console.read());
        this.console.write("Ticket: ");
        final Ticket ticket = this.entradas.ticket(
            new Uuid(this.console.read())
        );
        this.estacionamento.saida(ticket, placa, LocalDateTime.now());
    }
}
