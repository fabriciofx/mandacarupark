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

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Insert;
import br.edu.ifpe.ead.si.mandacarupark.db.Select;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlEntradas implements Entradas {
    private final Session session;

    public SqlEntradas(final Session session) {
        this.session = session;
    }

    @Override
    public Entrada entrada(final Placa placa, final LocalDateTime dataHora) {
        Uuid id = new Uuid();
        String sql = String.format(
            "INSERT INTO entrada (id, placa, datahora) VALUES ('%s', '%s', '%s')",
            id,
            placa,
            dataHora
        );
        new Insert(this.session, sql).execute();
        return new SqlEntrada(this.session, id);
    }

    @Override
    public Entrada procura(final Uuid id) {
        String sql = String.format(
            "SELECT COUNT(*) FROM entrada WHERE id = '%s'",
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
        return new SqlEntrada(this.session, id);
    }

    @Override
    public Iterator<Entrada> iterator() {
        String sql = String.format("SELECT * FROM entrada");
        List<Entrada> items = new ArrayList<>();
        try {
            ResultSet rset = new Select(this.session, sql).result();
            while (rset.next()) {
                items.add(
                    new SqlEntrada(
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
