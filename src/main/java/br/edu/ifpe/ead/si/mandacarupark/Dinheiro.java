package br.edu.ifpe.ead.si.mandacarupark;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public class Dinheiro implements Comparable<Dinheiro> {
    private final BigDecimal quantia;
    private final Currency moeda;
    private final Locale localizacao;

    public Dinheiro(String quantia) {
        this(
            new BigDecimal(quantia),
            Currency.getInstance("BRL"),
            new Locale("pt", "BR")
        );
    }

    public Dinheiro(BigDecimal quantia, Currency moeda, Locale localizacao) {
        this.quantia = quantia;
        this.moeda = moeda;
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        final NumberFormat formatoMoeda = NumberFormat.getInstance(
            this.localizacao
        );
        formatoMoeda.setMinimumFractionDigits(
            this.moeda.getDefaultFractionDigits()
        );
        return String.format(
            "%s %s",
            this.moeda.getSymbol(this.localizacao),
            formatoMoeda.format(this.quantia)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Dinheiro dinheiro = (Dinheiro) obj;
        return Objects.equals(
            this.quantia,
            dinheiro.quantia
        ) && Objects.equals(
            this.moeda,
            dinheiro.moeda
        ) && Objects.equals(this.localizacao, dinheiro.localizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantia, moeda, localizacao);
    }

    @Override
    public int compareTo(Dinheiro dinheiro) {
        return this.quantia.compareTo(dinheiro.quantia);
    }
}
