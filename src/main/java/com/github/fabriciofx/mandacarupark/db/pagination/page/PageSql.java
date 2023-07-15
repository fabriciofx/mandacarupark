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
package com.github.fabriciofx.mandacarupark.db.pagination.page;

import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.adapter.Adapter;
import com.github.fabriciofx.mandacarupark.db.pagination.Page;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public final class PageSql<T> implements Page<T> {
    private final Supplier<List<T>> itens;
    private final Session session;
    private final Adapter<T> adapter;
    private final String allQuery;
    private final int size;
    private final int limit;
    private final int number;

    public PageSql(
        final Session session,
        final Adapter<T> adapter,
        final String allQuery,
        final int size,
        final int limit,
        final int number
    ) {
        this.itens = () -> {
            try (
                final ResultSet rset = new Select(
                    session,
                    new Sprintf(
                        "%s LIMIT %d OFFSET %d",
                        allQuery,
                        limit,
                        limit * number
                    )
                ).result()
            ) {
                final List<T> itens = new ArrayList<>();
                while (rset.next()) {
                    itens.add(adapter.adapt(rset));
                }
                return itens;
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        this.session = session;
        this.allQuery = allQuery;
        this.adapter = adapter;
        this.size = size;
        this.limit = limit;
        this.number = number;
    }

    @Override
    public List<T> content() {
        return this.itens.get();
    }

    @Override
    public boolean hasNext() {
        final int rem = Math.min(this.size % this.limit, 1);
        final int pages = this.size / this.limit + rem;
        return this.number < pages - 1;
    }

    @Override
    public Page<T> next() {
        return new PageSql<>(
            this.session,
            this.adapter,
            this.allQuery,
            this.size,
            this.limit,
            this.number + 1
        );
    }

    @Override
    public boolean hasPrevious() {
        return this.number > 0;
    }

    @Override
    public Page<T> previous() {
        return new PageSql<>(
            this.session,
            this.adapter,
            this.allQuery,
            this.size,
            this.limit,
            this.number - 1
        );
    }
}
