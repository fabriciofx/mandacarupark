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
package com.github.fabriciofx.mandacarupark.db.pagination;

import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.db.pagination.Page;
import com.github.fabriciofx.mandacarupark.db.pagination.pages.PagesSql;
import com.github.fabriciofx.mandacarupark.adapter.ResultSetAsSaida;
import com.github.fabriciofx.mandacarupark.server.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("unchecked")
public final class TestPageSql {
    @Test
    public void countLessThanLimit() {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Page<Saida> page = new PagesSql<>(
                session,
                "SELECT COUNT(*) FROM saida",
                "SELECT * FROM saida",
                new ResultSetAsSaida(session),
                2
            ).page(0);
            Assert.assertEquals(2, page.content().size());
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void countGreaterThanLimit() {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Page<Saida> page = new PagesSql<>(
                session,
                "SELECT COUNT(*) FROM saida",
                "SELECT * FROM saida",
                new ResultSetAsSaida(session),
                2
            ).page(0);
            Assert.assertEquals(2, page.content().size());
            Assert.assertEquals(1, page.next().content().size());
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void contentNext() {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Page<Saida> page = new PagesSql<>(
                session,
                "SELECT COUNT(*) FROM saida",
                "SELECT * FROM saida",
                new ResultSetAsSaida(session),
                2
            ).page(0);
            Assert.assertEquals(
                new Uuid("4c32b3dd-8636-43c0-9786-4804ca2b73f5"),
                page.content().get(0).id()
            );
            Assert.assertEquals(
                new Uuid("07d8e078-5b47-4fb5-9fb4-dd2c80dd8036"),
                page.content().get(1).id()
            );
            Assert.assertEquals(
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
                page.next().content().get(0).id()
            );
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void contentIndexOutOfBoundPositive() {
        Assert.assertThrows(
            "index must be between 0 and 1",
            IndexOutOfBoundsException.class,
            () -> {
                final Session session = new NoAuth(
                    new H2Memory(
                        new RandomName()
                    )
                );
                try (
                    final Server server = new ServerH2(
                        session,
                        new ScriptSql("mandacarupark.sql")
                    )
                ) {
                    server.start();
                    final Page<Saida> page = new PagesSql<>(
                        session,
                        "SELECT COUNT(*) FROM saida",
                        "SELECT * FROM saida",
                        new ResultSetAsSaida(session),
                        2
                    ).page(0);
                    page.content().get(2);
                }
            }
        );
    }

    @Test
    public void contentIndexOutOfBoundNegative() {
        Assert.assertThrows(
            "index must be between 0 and 1",
            IndexOutOfBoundsException.class,
            () -> {
                final Session session = new NoAuth(
                    new H2Memory(
                        new RandomName()
                    )
                );
                try (
                    final Server server = new ServerH2(
                        session,
                        new ScriptSql("mandacarupark.sql")
                    )
                ) {
                    server.start();
                    final Page<Saida> page = new PagesSql<>(
                        session,
                        "SELECT COUNT(*) FROM saida",
                        "SELECT * FROM saida",
                        new ResultSetAsSaida(session),
                        2
                    ).page(0);
                    page.content().get(-1);
                }
            }
        );
    }

    @Test
    public void hasNextOneElement() {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Page<Saida> page = new PagesSql<>(
                session,
                "SELECT COUNT(*) FROM saida",
                "SELECT * FROM saida",
                new ResultSetAsSaida(session),
                3
            ).page(0);
            Assert.assertFalse(page.hasNext());
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void hasNext() {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Page<Saida> page = new PagesSql<>(
                session,
                "SELECT COUNT(*) FROM saida",
                "SELECT * FROM saida",
                new ResultSetAsSaida(session),
                2
            ).page(0);
            Assert.assertTrue(page.hasNext());
            Assert.assertFalse(page.next().hasNext());
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void noHasPreviousForTheFirstPage() {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Page<Saida> page = new PagesSql<>(
                session,
                "SELECT COUNT(*) FROM saida",
                "SELECT * FROM saida",
                new ResultSetAsSaida(session),
                3
            ).page(0);
            Assert.assertFalse(page.hasPrevious());
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void hasPrevious() {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Page<Saida> page = new PagesSql<>(
                session,
                "SELECT COUNT(*) FROM saida",
                "SELECT * FROM saida",
                new ResultSetAsSaida(session),
                2
            ).page(0).next();
            Assert.assertTrue(page.hasPrevious());
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void contentPrevious() {
        final Session session = new NoAuth(
            new H2Memory(
                new RandomName()
            )
        );
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Page<Saida> page = new PagesSql<>(
                session,
                "SELECT COUNT(*) FROM saida",
                "SELECT * FROM saida",
                new ResultSetAsSaida(session),
                2
            ).page(0).next();
            Assert.assertEquals(
                new Uuid("8c878e6f-ee13-4a37-a208-7510c2638944"),
                page.content().get(0).id()
            );
            Assert.assertEquals(
                new Uuid("4c32b3dd-8636-43c0-9786-4804ca2b73f5"),
                page.previous().content().get(0).id()
            );
            Assert.assertEquals(
                new Uuid("07d8e078-5b47-4fb5-9fb4-dd2c80dd8036"),
                page.previous().content().get(1).id()
            );
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
