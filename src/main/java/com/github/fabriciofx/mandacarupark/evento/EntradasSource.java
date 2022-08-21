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
package com.github.fabriciofx.mandacarupark.evento;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.Uuid;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EntradasSource implements Entradas {
    private final Entradas origin;
    private final List<Target<Entrada>> targets;

    public EntradasSource(
        final Entradas entradas,
        final Target<Entrada>... targets
    ) {
        this.origin = entradas;
        this.targets = Arrays.asList(targets);
    }

    @Override
    public Entrada entrada(Placa placa, DataHora dataHora) {
        final Entrada entrada = this.origin.entrada(placa, dataHora);
        this.targets.forEach(target -> target.notifique(entrada));
        return entrada;
    }

    @Override
    public Entrada procura(Uuid id) {
        return this.origin.procura(id);
    }

    @Override
    public Ticket ticket(Uuid id) {
        return this.origin.ticket(id);
    }

    @Override
    public Iterator<Entrada> iterator() {
        return this.origin.iterator();
    }
}
