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
package com.github.fabriciofx.mandacarupark.pagamento;

import com.github.fabriciofx.mandacarupark.Data;
import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Page;
import com.github.fabriciofx.mandacarupark.data.DataMap;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.regex.Matcher;

public final class PagamentoSql implements Pagamento {
    private final Session session;
    private final Id id;

    public PagamentoSql(final Session session, final Id id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Id id() {
        return this.id;
    }

    @Override
    public Data sobre() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT datahora, valor FROM pagamento WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final DataHora dataHora;
            final Dinheiro valor;
            if (rset.next()) {
                dataHora = new DataHoraOf(rset.getString(1));
                valor = new DinheiroOf(rset.getBigDecimal(2));
            } else {
                throw new RuntimeException("Dados inexistentes ou inválidos!");
            }
            return new DataMap(
                "dataHora", dataHora,
                "valor", valor
            );
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String print(final Page page, final String prefix) {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT datahora, valor FROM pagamento WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final DataHora dataHora;
            final Dinheiro valor;
            if (rset.next()) {
                dataHora = new DataHoraOf(rset.getString(1));
                valor = new DinheiroOf(rset.getBigDecimal(2));
            } else {
                throw new RuntimeException("Dados inexistentes ou inválidos!");
            }
            return page
                .with(prefix + ".id", this.id)
                .with(prefix + ".dataHora", dataHora)
                .with(
                    prefix + ".valor",
                    Matcher.quoteReplacement(valor.toString())
                )
                .asString();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
