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

import br.edu.ifpe.ead.si.mandacarupark.DataHora;
import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.data.DataStream;
import br.edu.ifpe.ead.si.mandacarupark.data.MemoryDataStream;
import br.edu.ifpe.ead.si.mandacarupark.db.Insert;
import br.edu.ifpe.ead.si.mandacarupark.db.Select;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import br.edu.ifpe.ead.si.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntradasSql implements Entradas {
    private final Session session;

    public EntradasSql(final Session session) {
        this.session = session;
    }

    @Override
    public Entrada entrada(final Placa placa, final DataHora dataHora) {
        final Uuid id = new Uuid();
        new Insert(
            this.session,
            new Sprintf(
                "INSERT INTO entrada (id, placa, datahora) VALUES ('%s', '%s', '%s')",
                id,
                placa,
                dataHora
            )
        ).execute();
        return new EntradaSql(this.session, id);
    }

    @Override
    public Entrada procura(final Uuid id) {
        int quantidade = 0;
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT COUNT(*) FROM entrada WHERE id = '%s'",
                    id
                )
            ).result()
        ) {
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
        return new EntradaSql(this.session, id);
    }

    @Override
    public Ticket ticket(Uuid id) {
        final Entrada entrada = this.procura(id);
        return new TicketSql(
            this.session,
            id,
            entrada.placa(),
            entrada.dataHora()
        );
    }

    @Override
    public Iterator<Entrada> iterator() {
        try (
            final ResultSet rset = new Select(
                this.session,
                "SELECT * FROM entrada WHERE NOT EXISTS (SELECT FROM saida WHERE entrada.id = saida.id)"
            ).result()
        ) {
            final List<Entrada> items = new ArrayList<>();
            while (rset.next()) {
                items.add(
                    new EntradaSql(
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

    @Override
    public DataStream sobre() {
        return new MemoryDataStream();
    }
}
