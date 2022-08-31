package com.github.fabriciofx.mandacarupark.conta;

import com.github.fabriciofx.mandacarupark.Conta;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Periodo;

public final class Cortesia implements Conta {
    @Override
    public boolean avalie(final Periodo periodo) {
        return true;
    }

    @Override
    public Dinheiro valor(final Periodo periodo) {
        return new Dinheiro("0.00");
    }
}
