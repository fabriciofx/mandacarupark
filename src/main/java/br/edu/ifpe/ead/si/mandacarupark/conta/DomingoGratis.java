package br.edu.ifpe.ead.si.mandacarupark.conta;

import br.edu.ifpe.ead.si.mandacarupark.Conta;
import br.edu.ifpe.ead.si.mandacarupark.DataHora;
import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import java.time.DayOfWeek;

public class DomingoGratis implements Conta {
    @Override
    public boolean avalie(DataHora entrada, DataHora saida) {
        return entrada.dateTime().getDayOfWeek() == DayOfWeek.SUNDAY &&
            saida.dateTime().getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    @Override
    public Dinheiro valor(DataHora entrada, DataHora saida) {
        return new Dinheiro("0.00");
    }
}
