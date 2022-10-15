package com.github.fabriciofx.mandacarupark.cli;

import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.console.Console;

public class OpcaoEntradas implements Opcao {
    private final String mensagem;
    private final Console console;
    private final Entradas entradas;

    public OpcaoEntradas(
        final String mensagem,
        final Console console,
        final Entradas entradas
    ) {
        this.mensagem = mensagem;
        this.console = console;
        this.entradas = entradas;
    }

    @Override
    public int numero() {
        return 4;
    }

    @Override
    public void mensagem() {
        this.console.write(this.mensagem + "\n");
    }

    @Override
    public void run() {
        for (final Entrada entrada : this.entradas) {
            this.console.write(
                String.format(
                    "%s %s %s\n",
                    entrada.id(),
                    entrada.sobre().get("placa").toString(),
                    entrada.sobre().get("dataHora").toString()
                )
            );
        }
    }
}
