package br.edu.ifpe.ead.si.mandacarupark;

import br.edu.ifpe.ead.si.mandacarupark.Conta;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Contas {
    private final List<Conta> items;

    public Contas(Conta... items) {
        this(Arrays.asList(items));
    }

    public Contas(List<Conta> items) {
        this.items = items;
    }

    public Conta conta(LocalDateTime entrada, LocalDateTime saida) {
        for (final Conta item : this.items) {
            if (item.avalie(entrada, saida)) {
                return item;
            }
        }
        return null;
    }
}
