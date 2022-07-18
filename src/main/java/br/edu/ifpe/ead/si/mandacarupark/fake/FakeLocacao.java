package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Locacao;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;

public class FakeLocacao implements Locacao {
    private final Uuid id;
    private final Placa placa;
    private final LocalDateTime entrada;
    private final LocalDateTime saida;
    private final Dinheiro valor;

    public FakeLocacao(
        Uuid id,
        Placa placa,
        LocalDateTime entrada,
        LocalDateTime saida,
        Dinheiro valor
    ) {
        this.id = id;
        this.placa = placa;
        this.entrada = entrada;
        this.saida = saida;
        this.valor = valor;
    }

    @Override
    public Placa placa() {
        return this.placa;
    }

    @Override
    public LocalDateTime entrada() {
        return this.entrada;
    }

    @Override
    public LocalDateTime saida() {
        return this.saida;
    }

    @Override
    public Dinheiro valor() {
        return this.valor;
    }
}
