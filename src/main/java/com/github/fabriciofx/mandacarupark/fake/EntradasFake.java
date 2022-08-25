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
package com.github.fabriciofx.mandacarupark.fake;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.Uuid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntradasFake implements Entradas {
    private final Map<Uuid, Entrada> items;
    private final Pagamentos pagamentos;

    public EntradasFake() {
        this(new PagamentosFake());
    }

    public EntradasFake(final Pagamentos pagamentos) {
        this(pagamentos, new HashMap<>());
    }

    public EntradasFake(
        final Pagamentos pagamentos,
        final Map<Uuid, Entrada> items
    ) {
        this.pagamentos = pagamentos;
        this.items = items;
    }

    @Override
    public Entrada entrada(final Placa placa, final DataHora dataHora) {
        final Entrada evento = new EntradaFake(new Uuid(), placa, dataHora);
        this.items.put(evento.id(), evento);
        return evento;
    }

    @Override
    public Entrada procura(final Uuid id) {
        return this.items.get(id);
    }

    @Override
    public Ticket ticket(Uuid id) {
        final Entrada entrada = this.procura(id);
        return new TicketFake(
            this.pagamentos,
            id,
            new Placa(entrada.sobre().get("placa")),
            new DataHora(entrada.sobre().get("dataHora"))
        );
    }

    @Override
    public Iterator<Entrada> iterator() {
        return this.items.values().iterator();
    }
}