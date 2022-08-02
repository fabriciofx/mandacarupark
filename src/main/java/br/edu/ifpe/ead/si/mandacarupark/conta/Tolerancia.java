package br.edu.ifpe.ead.si.mandacarupark.conta;

import br.edu.ifpe.ead.si.mandacarupark.Conta;
import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Periodo;
import java.time.LocalDateTime;

public class Tolerancia implements Conta {
    private final int minutos;

    public Tolerancia() {
        this(30);
    }

    public Tolerancia(int minutos) {
        this.minutos = minutos;
    }

    @Override
    public boolean avalie(LocalDateTime entrada, LocalDateTime saida) {
        boolean resultado = false;
        if (new Periodo(entrada, saida).minutos() <= this.minutos) {
            resultado = true;
        }
        return resultado;
    }

    @Override
    public Dinheiro valor(LocalDateTime entrada, LocalDateTime saida) {
        return new Dinheiro("0.00");
    }
}
