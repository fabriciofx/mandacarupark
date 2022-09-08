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
package com.github.fabriciofx.mandacarupark.placa;

import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.text.Text;
import java.util.Objects;

public final class PlacaOf implements Placa {
    private final Text numero;

    public PlacaOf(final String numero) {
        this(() ->  numero);
    }

    public PlacaOf(final Text numero) {
        this.numero = numero;
    }

    @Override
    public String numero() {
        return this.numero.asString();
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj || obj instanceof PlacaOf &&
            PlacaOf.class.cast(obj).numero().equals(this.numero.asString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numero());
    }

    @Override
    public String toString() {
        return this.numero();
    }
}
