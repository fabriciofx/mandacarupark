package com.github.fabriciofx.mandacarupark.cli;

import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.console.Console;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.id.Uuid;

public final class OpcaoPagamento implements Opcao {
    private final String mensagem;
    private final Console console;
    private final Estacionamento estacionamento;

    public OpcaoPagamento(
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
        final Entradas entradas = this.estacionamento.sobre().get("entradas");
        final Ticket ticket = entradas.procura(id).ticket();
        this.estacionamento.pagamento(
            ticket,
            new DataHoraOf()
        );
        this.console.write(
            String.format(
                "Ticket id: %s referente a placa %s %sestá pago!\n",
                ticket.id(),
                ticket.sobre().get("placa"),
                ticket.validado() ? "" : "não "
            )
        );
    }
}
