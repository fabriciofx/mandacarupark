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
package com.github.fabriciofx.mandacarupark.entradas;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Insert;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.entrada.EntradaSql;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EntradasSql implements Entradas {
    private final Session session;

    public EntradasSql(final Session session) {
        this.session = session;
    }

    @Override
    public Entrada entrada(
        final Id id,
        final Placa placa,
        final DataHora dataHora
    ) {
        new Insert(
            this.session,
            new Sprintf(
                "INSERT INTO entrada (id, placa, datahora) VALUES ('%s', '%s', '%s')",
                id,
                placa,
                dataHora.dateTime()
            )
        ).result();
        return new EntradaSql(this.session, id);
    }

    @Override
    public Entrada procura(final Id id) {
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
        return new EntradaSql(this.session, id);
    }

    @Override
    public Template print(final Template template) {
        final String regex = "\\$\\{es\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{es\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(new String(template.bytes()));
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Entrada entrada : this) {
                Template page = new HtmlTemplate(matcher.group(1));
                page = new HtmlTemplate(entrada.print(page));
                sb.append(new String(page.bytes()));
            }
        }
        return new HtmlTemplate(
            new String(template.bytes()).replaceAll(regex, sb.toString())
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
            final List<Entrada> itens = new ArrayList<>();
            while (rset.next()) {
                itens.add(
                    new EntradaSql(
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
