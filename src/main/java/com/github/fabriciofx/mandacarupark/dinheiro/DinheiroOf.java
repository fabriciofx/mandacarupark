/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022-2023 Fabrício Barros Cabral
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
package com.github.fabriciofx.mandacarupark.dinheiro;

import com.github.fabriciofx.mandacarupark.Dinheiro;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public final class DinheiroOf implements Dinheiro {
    private final BigDecimal quantia;
    private final Currency moeda;
    private final Locale localizacao;

    public DinheiroOf(final String quantia) {
        this(new BigDecimal(quantia));
    }

    public DinheiroOf(final BigDecimal quantia) {
        this(
            quantia,
            Currency.getInstance("BRL"),
            new Locale.Builder().setLanguageTag("pt-BR").build()
        );
    }

    public DinheiroOf(
        final BigDecimal quantia,
        final Currency moeda,
        final Locale localizacao
    ) {
        this.quantia = quantia;
        this.moeda = moeda;
        this.localizacao = localizacao;
    }

    @Override
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
        final DinheiroOf dinheiro = (DinheiroOf) obj;
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
        return Objects.hash(this.quantia, this.moeda, this.localizacao);
    }

    @Override
    public int compareTo(final Dinheiro dinheiro) {
        return this.quantia.compareTo(dinheiro.quantia());
    }
}
