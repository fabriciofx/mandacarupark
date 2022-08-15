package br.edu.ifpe.ead.si.mandacarupark.cli;

import br.edu.ifpe.ead.si.mandacarupark.DataHora;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;

public class OpcaoEntrada implements Opcao {
    private final String mensagem;
    private final Console console;
    private final Estacionamento estacionamento;

    public OpcaoEntrada(
        final String mensagem,
        final Console console,
        final Estacionamento estacionamento
    ) {
        this.mensagem = mensagem;
        this.console = console;
        this.estacionamento = estacionamento;
    }

    @Override
    public int numero() {
        return 1;
    }

    @Override
    public void mensagem() {
        this.console.write(this.mensagem + "\n");
    }

    @Override
    public void run() {
        this.console.write("Placa: ");
        final Placa placa = new Placa(this.console.read());
        final DataHora dataHora = new DataHora();
        final Ticket ticket = this.estacionamento.entrada(
            placa,
            dataHora
        );
        this.console.write(
            String.format(
                "Ticket id: %s em %s\n",
                ticket.id(),
                dataHora.toString()
            )
        );
    }
}
