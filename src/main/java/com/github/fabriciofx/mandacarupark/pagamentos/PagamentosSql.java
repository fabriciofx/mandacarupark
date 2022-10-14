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
package com.github.fabriciofx.mandacarupark.pagamentos;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.db.Insert;
import com.github.fabriciofx.mandacarupark.db.Select;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.pagamento.PagamentoSql;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class PagamentosSql implements Pagamentos {
    private final Session session;

    public PagamentosSql(final Session session) {
        this.session = session;
    }

    @Override
    public Pagamento pagamento(
        final Ticket ticket,
        final DataHora dataHora,
        final Dinheiro valor
    ) {
        new Insert(
            this.session,
            new Sprintf(
                "INSERT INTO pagamento (id, datahora, valor) VALUES ('%s', '%s', '%s')",
                ticket.id(),
                dataHora,
                valor.quantia()
            )
        ).execute();
        return new PagamentoSql(this.session, ticket.id());
    }

    @Override
    public List<Pagamento> procura(final Id id) {
        return Arrays.asList(new PagamentoSql(this.session, id));
    }

    @Override
    public int tamanho() {
        int quantidade = 0;
        try (
            final ResultSet rset = new Select(
                this.session,
                "SELECT COUNT(*) FROM pagamento"
            ).result()
        ) {
            if (rset.next()) {
                quantidade = rset.getInt(1);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return quantidade;
    }

    @Override
    public Iterator<Pagamento> iterator() {
        try (
            final ResultSet rset = new Select(
                this.session,
                "SELECT * FROM pagamento"
            ).result()) {
            final List<Pagamento> itens = new ArrayList<>();
            while (rset.next()) {
                itens.add(
                    new PagamentoSql(
                        this.session,
                        new Uuid(rset.getString(1))
                    )
                );
            }
            return itens.iterator();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
