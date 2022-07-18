package br.edu.ifpe.ead.si.mandacarupark.tabela;

import br.edu.ifpe.ead.si.mandacarupark.TabelaPrecos;
import java.time.LocalDateTime;

public class PrecoFixo implements TabelaPrecos {
    private final double valor;

    public PrecoFixo(double valor) {
        this.valor = valor;
    }

    @Override
    public double valor(LocalDateTime entrada, LocalDateTime saida) {
        return this.valor;
    }
}
