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

import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Saida;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Select;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlSaidas implements Saidas {
    private final Session session;

    public SqlSaidas(final Session session) {
        this.session = session;
    }

    @Override
    public Saida saida(
        final Ticket ticket,
        final Placa placa,
        final LocalDateTime dataHora
    ) {
        return new SqlSaida(this.session, ticket.id());
    }

    @Override
    public Saida procura(final Uuid id) {
        String sql = String.format(
            "SELECT COUNT(*) FROM saida WHERE id = '%s'",
            id
        );
        int quantidade = 0;
        try {
            ResultSet rset = new Select(this.session, sql).result();
            if (rset.next()) {
                quantidade = rset.getInt(1);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (quantidade == 0) {
            throw new RuntimeException(
                String.format(
                    "Entrada com id '%s' não encontrada!",
                    id
                )
            );
        }
        return new SqlSaida(this.session, id);
    }

    @Override
    public Iterator<Saida> iterator() {
        String sql = String.format("SELECT * FROM saida");
        List<Saida> items = new ArrayList<>();
        try {
            ResultSet rset = new Select(this.session, sql).result();
            while (rset.next()) {
                items.add(
                    new SqlSaida(
                        this.session,
                        new Uuid(rset.getString(1))
                    )
                );
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return items.iterator();
    }
}
