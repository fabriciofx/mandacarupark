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

public final class NoNulls implements Dinheiro {
    private final Dinheiro origin;

    public NoNulls(final Dinheiro dinheiro) {
        this.origin = dinheiro;
    }

    @Override
    public BigDecimal quantia() {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de um dinheiro válido"
            );
        }
        final BigDecimal quantia = this.origin.quantia();
        if (quantia == null) {
            throw new IllegalStateException(
                "NULL ao invés de uma quantia válida"
            );
        }
        return quantia;
    }

    @Override
    public int compareTo(final Dinheiro dinheiro) {
        if (dinheiro == null) {
            throw new IllegalArgumentException(
                "NULL ao invés de um dinheiro válido"
            );
        }
        return this.origin.quantia().compareTo(dinheiro.quantia());
    }
}
