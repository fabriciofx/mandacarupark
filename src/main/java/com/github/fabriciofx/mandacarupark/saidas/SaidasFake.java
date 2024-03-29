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
package com.github.fabriciofx.mandacarupark.saidas;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.db.pagination.Pages;
import com.github.fabriciofx.mandacarupark.db.pagination.pages.PagesFake;
import com.github.fabriciofx.mandacarupark.saida.SaidaFake;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SaidasFake implements Saidas {
    private final Map<Id, Saida> itens;

    public SaidasFake(final Saida... saidas) {
        this.itens = new HashMap<>();
        for (final Saida saida : saidas) {
            this.itens.put(saida.id(), saida);
        }
    }

    public SaidasFake() {
        this(new HashMap<>());
    }

    public SaidasFake(final Map<Id, Saida> itens) {
        this.itens = itens;
    }

    @Override
    public Saida saida(
        final Ticket ticket,
        final Placa placa,
        final DataHora dataHora
    ) {
        final Saida evento = new SaidaFake(ticket.id(), placa, dataHora);
        this.itens.put(evento.id(), evento);
        return evento;
    }

    @Override
    public List<Saida> procura(final Id id) {
        final Saida saida = this.itens.get(id);
        final List<Saida> lista = new ArrayList<>(0);
        if (saida != null) {
            lista.add(saida);
        }
        return lista;
    }

    @Override
    public Pages<Saida> pages(final int limit) {
        return new PagesFake<>(limit, new ArrayList<>(this.itens.values()));
    }
}
