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
package com.github.fabriciofx.mandacarupark.pagamentos;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Insert;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
import com.github.fabriciofx.mandacarupark.pagamento.PagamentoSql;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                dataHora.dateTime(),
                valor.quantia()
            )
        ).result();
        return new PagamentoSql(this.session, ticket.id());
    }

    @Override
    public List<Pagamento> procura(final Id id) {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf("SELECT * FROM pagamento WHERE id = '%s'", id)
            ).result()
        ) {
            final List<Pagamento> itens = new ArrayList<>(0);
            if (rset.next()) {
                itens.add(
                    new PagamentoSql(
                        this.session,
                        new Uuid(rset.getString(1))
                    )
                );
            }
            return itens;
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int quantidade() {
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
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
        return quantidade;
    }

    @Override
    public Template print(final Template template) {
        final String regex = "\\$\\{ps\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ps\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(new String(template.bytes()));
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Pagamento pagamento : this) {
                Template page = new HtmlTemplate(matcher.group(1));
                page = new HtmlTemplate(pagamento.print(page));
                sb.append(new String(page.bytes()));
            }
        }
        return new HtmlTemplate(
            new String(template.bytes()).replaceAll(
                regex,
                Matcher.quoteReplacement(sb.toString())
            )
        );
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
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
