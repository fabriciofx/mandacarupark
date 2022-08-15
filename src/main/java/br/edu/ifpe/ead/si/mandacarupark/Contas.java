package br.edu.ifpe.ead.si.mandacarupark;

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

    public Conta conta(DataHora entrada, DataHora saida) {
        for (final Conta item : this.items) {
            if (item.avalie(entrada, saida)) {
                return item;
            }
        }
        return null;
    }
}
