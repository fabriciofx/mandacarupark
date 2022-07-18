package br.edu.ifpe.ead.si.mandacarupark.preco;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Precos;
import java.time.LocalDateTime;

public class PrecoFixo implements Precos {
    private final Dinheiro valor;

    public PrecoFixo(Dinheiro valor) {
        this.valor = valor;
    }

    @Override
    public Dinheiro valor(LocalDateTime entrada, LocalDateTime saida) {
        return this.valor;
    }
}
