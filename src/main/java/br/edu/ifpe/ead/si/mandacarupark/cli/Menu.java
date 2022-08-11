package br.edu.ifpe.ead.si.mandacarupark.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final Console console;
    private final String header;
    private final List<Opcao> opcoes;

    public Menu(
        final Console console,
        final String header,
        final Opcao... opcoes
    ) {
        this.console = console;
        this.header = header;
        this.opcoes = Arrays.asList(opcoes);
    }

    public void run() {
        final Map<Integer, Opcao> escolhas = new HashMap<>();
        this.opcoes.forEach(opcao -> escolhas.put(opcao.numero(), opcao));
        this.console.clear();
        while (true) {
            this.console.write(this.header + "\n");
            this.opcoes.forEach(opcao -> opcao.mensagem());
            this.console.write("\nOpção: ");
            int opcao = Integer.parseInt(console.read());
            escolhas.get(opcao).run();
        }
    }
}
