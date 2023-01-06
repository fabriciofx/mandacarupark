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
import com.github.fabriciofx.mandacarupark.Page;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Insert;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.saida.SaidaSql;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SaidasSql implements Saidas {
    private final Session session;

    public SaidasSql(final Session session) {
        this.session = session;
    }

    @Override
    public Saida saida(
        final Ticket ticket,
        final Placa placa,
        final DataHora dataHora
    ) {
        new Insert(
            this.session,
            new Sprintf(
                "INSERT INTO saida (id, placa, datahora) VALUES ('%s', '%s', '%s')",
                ticket.id(),
                placa,
                dataHora.dateTime()
            )
        ).execute();
        return new SaidaSql(this.session, ticket.id());
    }

    @Override
    public Saida procura(final Id id) {
        int quantidade = 0;
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT COUNT(*) FROM saida WHERE id = '%s'",
                    id
                )
            ).result()
        ) {
            if (rset.next()) {
                quantidade = rset.getInt(1);
            }
        } catch (final Exception ex) {
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
        return new SaidaSql(this.session, id);
    }

    @Override
    public String print(final Page page, final String prefix) {
        final String regex = new Sprintf(
            "\\$\\{%s\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{%s\\.end}",
            prefix,
            prefix
        ).asString();
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(page.asString());
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Saida saida : this) {
                Page pg = new Page(matcher.group(1));
                pg = new Page(saida.print(pg, "s"));
                sb.append(pg.asString());
            }
        }
        return page.asString().replaceAll(regex, sb.toString());
    }

    @Override
    public Iterator<Saida> iterator() {
        try (
            final ResultSet rset = new Select(
                this.session,
                "SELECT * FROM saida"
            ).result()
        ) {
            final List<Saida> itens = new ArrayList<>();
            while (rset.next()) {
                itens.add(
                    new SaidaSql(
                        this.session,
                        new Uuid(rset.getString(1))
                    )
                );
            }
            return itens.iterator();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
