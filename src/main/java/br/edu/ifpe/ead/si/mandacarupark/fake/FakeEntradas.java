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

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FakeEntradas implements Entradas {
    private final Map<Uuid, Entrada> items;

    public FakeEntradas() {
        this(new HashMap<>());
    }

    public FakeEntradas(final Map<Uuid, Entrada> items) {
        this.items = items;
    }

    @Override
    public Entrada entrada(final Placa placa, final LocalDateTime dataHora) {
        final Entrada evento = new FakeEntrada(new Uuid(), placa, dataHora);
        this.items.put(evento.id(), evento);
        return evento;
    }

    @Override
    public Entrada procura(final Uuid id) {
        return this.items.get(id);
    }

    @Override
    public Iterator<Entrada> iterator() {
        return this.items.values().iterator();
    }
}
