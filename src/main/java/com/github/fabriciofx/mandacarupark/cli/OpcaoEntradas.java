package com.github.fabriciofx.mandacarupark.cli;

import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.console.Console;
import com.github.fabriciofx.mandacarupark.text.TextTable;

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
        int linhas = 0;
        for (final Entrada entrada : this.entradas) {
            linhas++;
        }
        final String[][] tabela = new String[linhas][3];
        int linha = 0;
        for (final Entrada entrada : this.entradas) {
            tabela[linha][0] = String.format(
                " %s ",
                entrada.id().numero()
            );
            tabela[linha][1] = String.format(
                " %s ",
                entrada.sobre().get("placa").toString()
            );
            tabela[linha][2] = String.format(
                " %s ",
                entrada.sobre().get("dataHora").toString()
            );
            linha++;
        }
        this.console.write(new TextTable(tabela).asString());
    }
}
