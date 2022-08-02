package br.edu.ifpe.ead.si.mandacarupark.conta;

import br.edu.ifpe.ead.si.mandacarupark.Conta;
import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DomingoGratis implements Conta {
    @Override
    public boolean avalie(LocalDateTime entrada, LocalDateTime saida) {
        return entrada.getDayOfWeek() == DayOfWeek.SUNDAY &&
            saida.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    @Override
    public Dinheiro valor(LocalDateTime entrada, LocalDateTime saida) {
        return new Dinheiro("0.00");
    }
}
