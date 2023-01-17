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
package com.github.fabriciofx.mandacarupark.locacoes;

import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.locacao.LocacaoSql;
import com.github.fabriciofx.mandacarupark.media.Page;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LocacoesSql implements Locacoes {
    private final Session session;
    private final Periodo periodo;

    public LocacoesSql(final Session session, final Periodo periodo) {
        this.session = session;
        this.periodo = periodo;
    }

    @Override
    public Iterator<Locacao> iterator() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT * FROM locacao WHERE entrada >= '%s' AND saida <= '%s'",
                    this.periodo.inicio().dateTime(),
                    this.periodo.termino().dateTime()
                )
            ).result()
        ) {
            final List<Locacao> itens = new ArrayList<>();
            while (rset.next()) {
                itens.add(
                    new LocacaoSql(
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

    @Override
    public Media print(final Media media) {
        final String regex = "\\$\\{ls\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ls\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(new String(media.bytes()));
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Locacao locacao : this) {
                Media page = new Page(matcher.group(1));
                page = new Page(locacao.print(page));
                sb.append(new String(page.bytes()));
            }
        }
        return new Page(
            new String(media.bytes()).replaceAll(
                regex,
                Matcher.quoteReplacement(sb.toString())
            )
        );
    }
}
