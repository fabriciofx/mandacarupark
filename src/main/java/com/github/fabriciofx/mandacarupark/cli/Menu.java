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
package com.github.fabriciofx.mandacarupark.cli;

import com.github.fabriciofx.mandacarupark.console.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Menu {
    private final Console console;
    private final String cabecalho;
    private final List<Opcao> opcoes;

    public Menu(
        final Console console,
        final String cabecalho,
        final Opcao... opcoes
    ) {
        this.console = console;
        this.cabecalho = cabecalho;
        this.opcoes = Arrays.asList(opcoes);
    }

    public void run() {
        final Map<Integer, Opcao> escolhas = new HashMap<>();
        this.opcoes.forEach(opcao -> escolhas.put(opcao.numero(), opcao));
        this.console.clear();
        while (true) {
            this.console.write(this.cabecalho + "\n");
            this.opcoes.forEach(opcao -> opcao.mensagem());
            this.console.write("\nOpção: ");
            int opcao = Integer.parseInt(console.read());
            escolhas.get(opcao).run();
        }
    }
}
