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
package com.github.fabriciofx.mandacarupark.cli;

import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.console.Console;

public final class OpcaoEncerrar implements Opcao {
    private final String mensagem;
    private final Console console;
    private final Entradas entradas;

    public OpcaoEncerrar(
        final String mensagem,
        final Console console,
        final Entradas entradas
    ) {
        this.mensagem = mensagem;
        this.console = console;
        this.entradas = entradas;
    }

    @Override
    public int numero() {
        return 9;
    }

    @Override
    public void mensagem() {
        this.console.write(this.mensagem + "\n");
    }

    @Override
    public void run() {
        this.console.clear();
        for (final Entrada entrada : this.entradas) {
            this.console.write(
                String.format(
                    "Entrada: %s\n",
                    entrada.sobre().valor("placa").toString()
                )
            );
        }
    }
}
