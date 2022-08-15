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
import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;

public class TicketFake implements Ticket {
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
    public Placa placa() {
        return this.placa;
    }

    @Override
    public DataHora dataHora() {
        return this.dataHora;
    }

    @Override
    public Dinheiro valor() {
        final Pagamento pagamento = this.pagamentos.procura(this.id);
        return pagamento.valor();
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
}
