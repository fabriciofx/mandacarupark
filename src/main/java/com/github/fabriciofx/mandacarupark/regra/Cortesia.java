package com.github.fabriciofx.mandacarupark.regra;

import com.github.fabriciofx.mandacarupark.Regra;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;

public final class Cortesia implements Regra {
    @Override
    public boolean avalie(final Periodo periodo) {
        return true;
    }

    @Override
    public Dinheiro valor(final Periodo periodo) {
        return new DinheiroOf("0.00");
    }
}
