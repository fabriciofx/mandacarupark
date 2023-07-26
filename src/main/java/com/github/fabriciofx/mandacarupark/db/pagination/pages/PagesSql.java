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
package com.github.fabriciofx.mandacarupark.db.pagination.pages;

import com.github.fabriciofx.mandacarupark.adapter.Adapter;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.pagination.Page;
import com.github.fabriciofx.mandacarupark.db.pagination.Pages;
import com.github.fabriciofx.mandacarupark.db.pagination.page.PageSql;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.text.Text;
import java.sql.ResultSet;
import java.util.function.Supplier;

public final class PagesSql<T> implements Pages<T> {
    private final Supplier<Integer> size;
    private final Adapter<T> adapter;
    private final Session session;
    private final Text allQuery;
    private final int limit;

    public PagesSql(
        final Session session,
        final String countQuery,
        final String allQuery,
        final Adapter<T> adapter,
        final int limit
    ) {
        this(session, () -> countQuery, () -> allQuery, adapter, limit);
    }

    public PagesSql(
        final Session session,
        final Text countQuery,
        final Text allQuery,
        final Adapter<T> adapter,
        final int limit
    ) {
        this.size = () -> {
            try (
                final ResultSet rset = new Select(session, countQuery).result()
            ) {
                if (rset.next()) {
                    return rset.getInt(1);
                } else {
                    return 0;
                }
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        this.session = session;
        this.adapter = adapter;
        this.allQuery = allQuery;
        this.limit = limit;
    }

    @Override
    public int count() {
        final int rem = Math.min(this.size.get() % this.limit, 1);
        return this.size.get() / this.limit + rem;
    }

    @Override
    public Page<T> page(final int number) {
        return new PageSql<>(
            this.session,
            this.adapter,
            this.allQuery,
            this.size.get(),
            this.limit,
            number
        );
    }
}
