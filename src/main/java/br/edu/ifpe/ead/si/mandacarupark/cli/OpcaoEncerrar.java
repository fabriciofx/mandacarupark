package br.edu.ifpe.ead.si.mandacarupark.cli;

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;

public class OpcaoEncerrar implements Opcao {
    private final String mensagem;
    private final Console console;
    private final Entradas entradas;

    public OpcaoEncerrar(
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
        return 9;
    }

    @Override
    public void mensagem() {
        this.console.write(this.mensagem + "\n");
    }

    @Override
    public void run() {
        this.console.clear();
        for (final Entrada entrada : this.entradas) {
            this.console.write(
                String.format(
                    "Entrada: %s\n",
                    entrada.placa().toString()
                )
            );
        }
    }
}
