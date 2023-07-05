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
package com.github.fabriciofx.mandacarupark.entradas;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Target;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("varargs")
public final class EntradasSource implements Entradas {
    private final Entradas origin;
    private final List<Target<Entrada>> targets;

    @SafeVarargs
    public EntradasSource(
        final Entradas entradas,
        final Target<Entrada>... targets
    ) {
        this.origin = entradas;
        this.targets = Arrays.asList(targets);
    }

    @Override
    public Entrada entrada(
        final Id id,
        final Placa placa,
        final DataHora dataHora
    ) {
        final Entrada entrada = this.origin.entrada(id, placa, dataHora);
        this.targets.forEach(target -> target.notifique(entrada));
        return entrada;
    }

    @Override
    public Entrada procura(Id id) {
        return this.origin.procura(id);
    }

    @Override
    public Iterator<Entrada> iterator() {
        return this.origin.iterator();
    }

    @Override
    public Media sobre(final Media media) {
        Media med = media.begin("entradas");
        for (final Entrada entrada : this) {
            med = entrada.sobre(med);
        }
        return med.end("entradas");
    }
}
