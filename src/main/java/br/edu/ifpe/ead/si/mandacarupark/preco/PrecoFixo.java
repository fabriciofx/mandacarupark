package br.edu.ifpe.ead.si.mandacarupark.preco;

import br.edu.ifpe.ead.si.mandacarupark.Precos;
import java.time.LocalDateTime;

public class PrecoFixo implements Precos {
    private final double valor;

    public PrecoFixo(double valor) {
        this.valor = valor;
    }

    @Override
    public double valor(LocalDateTime entrada, LocalDateTime saida) {
        return this.valor;
    }
}
