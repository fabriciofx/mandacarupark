package com.github.fabriciofx.mandacarupark.conta;

import com.github.fabriciofx.mandacarupark.Conta;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;

public final class Cortesia implements Conta {
    @Override
    public boolean avalie(final Periodo periodo) {
        return true;
    }

    @Override
    public Dinheiro valor(final Periodo periodo) {
        return new DinheiroOf("0.00");
    }
}
