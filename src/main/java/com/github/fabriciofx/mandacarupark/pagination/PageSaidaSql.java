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
package com.github.fabriciofx.mandacarupark.pagination;

import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.saida.SaidaSql;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class PageSaidaSql implements Page<Saida> {
    private final Supplier<List<Saida>> scalar;
    private final Supplier<Integer> size;
    private final Session session;
    private final int limit;
    private final int position;

    public PageSaidaSql(
        final Session session,
        final int limit,
        final int position
    ) {
        this.scalar = () -> {
            try (
                final ResultSet rset = new Select(
                    session,
                    new Sprintf(
                        "SELECT * FROM saida LIMIT %d OFFSET %d",
                        limit,
                        position
                    )
                ).result()
            ) {
                final List<Saida> itens = new ArrayList<>();
                while (rset.next()) {
                    itens.add(
                        new SaidaSql(
                            session,
                            new Uuid(rset.getString(1))
                        )
                    );
                }
                return itens;
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        this.size = () -> {
            try (
                final ResultSet rset = new Select(
                    session,
                    new Sprintf(
                        "SELECT COUNT(*) FROM saida",
                        limit,
                        position
                    )
                ).result()
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
        this.limit = limit;
        this.position = position;
    }

    @Override
    public int count() {
        return this.scalar.get().size();
    }

    @Override
    public Saida content(final int index) {
        return this.scalar.get().get(index);
    }

    @Override
    public boolean hasNext() {
        return this.size.get() > this.position + this.limit;
    }

    @Override
    public Page<Saida> next() {
        final int pos = this.position + this.limit;
        return new PageSaidaSql(this.session, this.limit, pos);
    }

    @Override
    public boolean hasPrev() {
        return this.position - this.limit >= 0;
    }

    @Override
    public Page<Saida> prev() {
        final int pos = this.position - this.limit;
        return new PageSaidaSql(this.session, this.limit, pos);
    }
}
