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
package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.DataHora;
import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Locacao;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;

public class LocacaoFake implements Locacao {
    private final Uuid id;
    private final Placa placa;
    private final DataHora entrada;
    private final DataHora saida;
    private final Dinheiro valor;

    public LocacaoFake(
        final Uuid id,
        final Placa placa,
        final DataHora entrada,
        final DataHora saida,
        final Dinheiro valor
    ) {
        this.id = id;
        this.placa = placa;
        this.entrada = entrada;
        this.saida = saida;
        this.valor = valor;
    }

    @Override
    public Placa placa() {
        return this.placa;
    }

    @Override
    public DataHora entrada() {
        return this.entrada;
    }

    @Override
    public DataHora saida() {
        return this.saida;
    }

    @Override
    public Dinheiro valor() {
        return this.valor;
    }
}
