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

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Verifica se uma determinada placa é válida.
 */
public class Placa {
    private static final Pattern PADRAO = Pattern.compile(
        "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}"
    );
    private final String numero;

    public Placa(final String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(final Object obj) {
        this.valida(this.numero);
        return this == obj || obj instanceof Placa &&
            Placa.class.cast(obj).numero.equals(this.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numero);
    }

    @Override
    public String toString() {
        this.valida(this.numero);
        return this.numero;
    }

    private void valida(final String numero) {
        if (!this.PADRAO.matcher(numero).matches()) {
            throw new RuntimeException("Placa: número inválido!");
        }
    }
}
