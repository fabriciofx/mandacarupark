package com.github.fabriciofx.mandacarupark.cli;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.Uuid;
import com.github.fabriciofx.mandacarupark.console.Console;

public class OpcaoPagamento implements Opcao {
    private final String mensagem;
    private final Console console;
    private final Estacionamento estacionamento;
    private final Entradas entradas;

    public OpcaoPagamento(
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
        return 3;
    }

    @Override
    public void mensagem() {
        this.console.write(this.mensagem + "\n");
    }

    @Override
    public void run() {
        this.console.write("  Ticket: ");
        final Uuid id = new Uuid(this.console.read());
        final Ticket ticket = this.entradas.ticket(id);
        final Ticket ticketValidado = this.estacionamento.pagamento(
            ticket,
            new DataHora()
        );
        this.console.write(
            String.format(
                "Ticket id: %s referente a placa %s %sestá pago!\n",
                ticketValidado.id(),
                ticketValidado.sobre().value("placa"),
                ticketValidado.validado() ? "" : "não "
            )
        );
    }
}
