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

import com.github.fabriciofx.mandacarupark.Dados;
import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.Uuid;

public final class TicketFake implements Ticket {
    private final Pagamentos pagamentos;
    private final Uuid id;
    private final Placa placa;
    private final DataHora dataHora;

    public TicketFake(
        final Pagamentos pagamentos,
        final Placa placa,
        final DataHora dataHora
    ) {
        this(
            pagamentos,
            new Uuid(),
            placa,
            dataHora
        );
    }

    public TicketFake(
        final Pagamentos pagamentos,
        final Uuid id,
        final Placa placa,
        final DataHora dataHora
    ) {
        this.pagamentos = pagamentos;
        this.id = id;
        this.placa = placa;
        this.dataHora = dataHora;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public boolean validado() {
        boolean result = false;
        final Pagamento pagamento = this.pagamentos.procura(this.id);
        if (pagamento != null) {
            result = true;
        }
        return result;
    }

    @Override
    public Dados sobre() {
        final Pagamento pagamento = this.pagamentos.procura(this.id);
        final Dinheiro valor;
        if (pagamento == null) {
            valor = new Dinheiro("0.00");
        } else {
            valor = pagamento.sobre().valor("valor");
        }
        return new Dados(
            "placa", this.placa,
            "dataHora", this.dataHora,
            "valor", valor
        );
    }
}
