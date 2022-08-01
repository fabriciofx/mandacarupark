package br.edu.ifpe.ead.si.mandacarupark.preco;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Periodo;
import br.edu.ifpe.ead.si.mandacarupark.Precos;
import java.time.LocalDateTime;

public class Tolerancia implements Precos {
    private final Precos origin;
    private final int minutos;

    public Tolerancia(final Precos precos) {
        this(precos, 30);
    }

    public Tolerancia(final Precos precos, int minutos) {
        this.origin = precos;
        this.minutos = minutos;
    }

    @Override
    public Dinheiro valor(LocalDateTime entrada, LocalDateTime saida) {
        Dinheiro valor = this.origin.valor(entrada, saida);
        if (new Periodo(entrada, saida).minutos() <= minutos) {
            valor = new Dinheiro("0.00");
        }
        return valor;
    }
}
