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
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.adapter.ResultSetAsLocacao;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.pagination.Pages;
import com.github.fabriciofx.mandacarupark.db.pagination.pages.PagesSql;
import com.github.fabriciofx.mandacarupark.text.Sprintf;

public final class LocacoesSql implements Locacoes {
    private final Session session;

    public LocacoesSql(final Session session) {
        this.session = session;
    }

    @Override
    public Pages<Locacao> pages(final int limit, final Periodo periodo) {
        return new PagesSql<>(
            this.session,
            new Sprintf(
                "SELECT COUNT(*) FROM locacao WHERE entrada >= '%s' AND saida <= '%s'",
                periodo.inicio().dateTime(),
                periodo.termino().dateTime()
            ).asString(),
            new Sprintf(
                "SELECT * FROM locacao WHERE entrada >= '%s' AND saida <= '%s'",
                periodo.inicio().dateTime(),
                periodo.termino().dateTime()
            ).asString(),
            new ResultSetAsLocacao(this.session),
            limit
        );
    }
}
