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
package com.github.fabriciofx.mandacarupark;

import java.util.Map;

public class Dados {
    private final Map<String, Object> itens;

    public Dados(
        final String chave1,
        final Object valor1,
        final String chave2,
        final Object valor2,
        final String chave3,
        final Object valor3,
        final String chave4,
        final Object valor4,
        final String chave5,
        final Object valor5,
        final String chave6,
        final Object valor6,
        final String chave7,
        final Object valor7,
        final String chave8,
        final Object valor8,
        final String chave9,
        final Object valor9,
        final String chave10,
        final Object valor10
    ) {
        this(Map.of(
            chave1,
            valor1,
            chave2,
            valor2,
            chave3,
            valor3,
            chave4,
            valor4,
            chave5,
            valor5,
            chave6,
            valor6,
            chave7,
            valor7,
            chave8,
            valor8,
            chave9,
            valor9,
            chave10,
            valor10
        ));
    }

    public Dados(
        final String chave1,
        final Object valor1,
        final String chave2,
        final Object valor2,
        final String chave3,
        final Object valor3,
        final String chave4,
        final Object valor4,
        final String chave5,
        final Object valor5,
        final String chave6,
        final Object valor6,
        final String chave7,
        final Object valor7,
        final String chave8,
        final Object valor8,
        final String chave9,
        final Object valor9
    ) {
        this(Map.of(
            chave1,
            valor1,
            chave2,
            valor2,
            chave3,
            valor3,
            chave4,
            valor4,
            chave5,
            valor5,
            chave6,
            valor6,
            chave7,
            valor7,
            chave8,
            valor8,
            chave9,
            valor9
        ));
    }

    public Dados(
        final String chave1,
        final Object valor1,
        final String chave2,
        final Object valor2,
        final String chave3,
        final Object valor3,
        final String chave4,
        final Object valor4,
        final String chave5,
        final Object valor5,
        final String chave6,
        final Object valor6,
        final String chave7,
        final Object valor7,
        final String chave8,
        final Object valor8
    ) {
        this(Map.of(
            chave1,
            valor1,
            chave2,
            valor2,
            chave3,
            valor3,
            chave4,
            valor4,
            chave5,
            valor5,
            chave6,
            valor6,
            chave7,
            valor7,
            chave8,
            valor8
        ));
    }

    public Dados(
        final String chave1,
        final Object valor1,
        final String chave2,
        final Object valor2,
        final String chave3,
        final Object valor3,
        final String chave4,
        final Object valor4,
        final String chave5,
        final Object valor5,
        final String chave6,
        final Object valor6,
        final String chave7,
        final Object valor7
    ) {
        this(Map.of(
            chave1,
            valor1,
            chave2,
            valor2,
            chave3,
            valor3,
            chave4,
            valor4,
            chave5,
            valor5,
            chave6,
            valor6,
            chave7,
            valor7
        ));
    }

    public Dados(
        final String chave1,
        final Object valor1,
        final String chave2,
        final Object valor2,
        final String chave3,
        final Object valor3,
        final String chave4,
        final Object valor4,
        final String chave5,
        final Object valor5,
        final String chave6,
        final Object valor6
    ) {
        this(Map.of(
            chave1,
            valor1,
            chave2,
            valor2,
            chave3,
            valor3,
            chave4,
            valor4,
            chave5,
            valor5,
            chave6,
            valor6
        ));
    }

    public Dados(
        final String chave1,
        final Object valor1,
        final String chave2,
        final Object valor2,
        final String chave3,
        final Object valor3,
        final String chave4,
        final Object valor4,
        final String chave5,
        final Object valor5
    ) {
        this(Map.of(
            chave1,
            valor1,
            chave2,
            valor2,
            chave3,
            valor3,
            chave4,
            valor4,
            chave5,
            valor5
        ));
    }

    public Dados(
        final String chave1,
        final Object valor1,
        final String chave2,
        final Object valor2,
        final String chave3,
        final Object valor3,
        final String chave4,
        final Object valor4
    ) {
        this(Map.of(
            chave1,
            valor1,
            chave2,
            valor2,
            chave3,
            valor3,
            chave4,
            valor4
        ));
    }

    public Dados(
        final String chave1,
        final Object valor1,
        final String chave2,
        final Object valor2,
        final String chave3,
        final Object valor3
    ) {
        this(Map.of(chave1, valor1, chave2, valor2, chave3, valor3));
    }

    public Dados(
        final String chave1,
        final Object valor1,
        final String chave2,
        final Object valor2
    ) {
        this(Map.of(chave1, valor1, chave2, valor2));
    }

    public Dados(final String chave1, final Object valor1) {
        this(Map.of(chave1, valor1));
    }

    public Dados(final Map<String, Object> itens) {
        this.itens = itens;
    }

    public <T> T valor(final String chave) {
        return (T) (this.itens.get(chave));
    }

    public Dados com(final String chave, final Object valor) {
        this.itens.put(chave, valor);
        return new Dados(this.itens);
    }

    @Override
    public String toString() {
        return this.itens.toString();
    }
}