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
package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Insert;
import br.edu.ifpe.ead.si.mandacarupark.db.Select;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlPagamentos implements Pagamentos {
    private final Session session;

    public SqlPagamentos(final Session session) {
        this.session = session;
    }

    @Override
    public Pagamento pagamento(
        final Ticket ticket,
        final LocalDateTime dataHora,
        final Dinheiro valor
    ) {
        final String sql = String.format(
            "INSERT INTO pagamento (id, datahora, valor) VALUES ('%s', '%s', '%s')",
            ticket.id(),
            dataHora,
            valor.quantia()
        );
        new Insert(this.session, sql).execute();
        return new SqlPagamento(this.session, ticket.id());
    }

    @Override
    public Pagamento procura(final Uuid id) {
        final String sql = String.format(
            "SELECT COUNT(*) FROM pagamento WHERE id = '%s'",
            id
        );
        int quantidade = 0;
        try (final ResultSet rset = new Select(this.session, sql).result()) {
            if (rset.next()) {
                quantidade = rset.getInt(1);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (quantidade == 0) {
            throw new RuntimeException(
                String.format(
                    "Pagamento com id '%s' não encontrado!",
                    id
                )
            );
        }
        return new SqlPagamento(this.session, id);
    }

    @Override
    public Iterator<Pagamento> iterator() {
        final String sql = String.format("SELECT * FROM pagamento");
        try (final ResultSet rset = new Select(this.session, sql).result()) {
            final List<Pagamento> items = new ArrayList<>();
            while (rset.next()) {
                items.add(
                    new SqlPagamento(
                        this.session,
                        new Uuid(rset.getString(1))
                    )
                );
            }
            return items.iterator();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
