/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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

    public Dinheiro(final String quantia) {
        this(new BigDecimal(quantia));
    }

    public Dinheiro(final BigDecimal quantia) {
        this(
            quantia,
            Currency.getInstance("BRL"),
            new Locale("pt", "BR")
        );
    }

    public Dinheiro(
        final BigDecimal quantia,
        final Currency moeda,
        final Locale localizacao
    ) {
        this.quantia = quantia;
        this.moeda = moeda;
        this.localizacao = localizacao;
    }

    public BigDecimal quantia() {
        return this.quantia;
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
    public boolean equals(final Object obj) {
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
    public int compareTo(final Dinheiro dinheiro) {
        return this.quantia.compareTo(dinheiro.quantia);
    }
}
