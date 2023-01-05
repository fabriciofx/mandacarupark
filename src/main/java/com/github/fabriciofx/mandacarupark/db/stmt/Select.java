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
package com.github.fabriciofx.mandacarupark.db.stmt;

import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.text.Text;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class Select {
    private final Session session;
    private final Text query;

    public Select(final Session session, final String query) {
        this(session, () -> query);
    }

    public Select(final Session session, final Text query) {
        this.session = session;
        this.query = query;
    }

    public ResultSet result() {
        try (final Connection conn = this.session.connection()) {
            try (
                final PreparedStatement stmt = conn.prepareStatement(
                    this.query.asString()
                )
            ) {
                try (final ResultSet rset = stmt.executeQuery()) {
                    final RowSetFactory rsf = RowSetProvider.newFactory();
                    final CachedRowSet crs = rsf.createCachedRowSet();
                    crs.populate(rset);
                    return crs;
                }
            }
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
