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
package com.github.fabriciofx.mandacarupark.pagination.page;

import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.adapter.Adapter;
import com.github.fabriciofx.mandacarupark.pagination.Page;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public final class PageSql<T> implements Page<T> {
    private final Supplier<List<T>> scalar;
    private final Adapter<T> adapter;
    private final Session session;
    private final String tablename;
    private final int size;
    private final int limit;
    private final int position;

    public PageSql(
        final Session session,
        final Adapter<T> adapter,
        final String tablename,
        final int size,
        final int limit,
        final int position
    ) {
        this.tablename = tablename;
        this.scalar = () -> {
            try (
                final ResultSet rset = new Select(
                    session,
                    new Sprintf(
                        "SELECT * FROM %s LIMIT %d OFFSET %d",
                        tablename,
                        limit,
                        position
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
        this.adapter = adapter;
        this.size = size;
        this.limit = limit;
        this.position = position;
    }

    @Override
    public List<T> content() {
        return this.scalar.get();
    }

    @Override
    public boolean hasNext() {
        return this.size > this.position + this.limit;
    }

    @Override
    public Page<T> next() {
        final int pos = this.position + this.limit;
        return new PageSql<>(
            this.session,
            this.adapter,
            this.tablename,
            this.size,
            this.limit,
            pos
        );
    }

    @Override
    public boolean hasPrevious() {
        return this.position - this.limit >= 0;
    }

    @Override
    public Page<T> previous() {
        final int pos = this.position - this.limit;
        return new PageSql<>(
            this.session,
            this.adapter,
            this.tablename,
            this.size,
            this.limit,
            pos
        );
    }
}
