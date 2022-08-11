package br.edu.ifpe.ead.si.mandacarupark.cli;

public class Encerrar implements Opcao {
    private final Opcao origin;

    public Encerrar(final Opcao opcao) {
        this.origin = opcao;
    }

    @Override
    public int numero() {
        return this.origin.numero();
    }

    @Override
    public void mensagem() {
        this.origin.mensagem();
    }

    @Override
    public void run() {
        this.origin.run();
        System.exit(0);
    }
}
