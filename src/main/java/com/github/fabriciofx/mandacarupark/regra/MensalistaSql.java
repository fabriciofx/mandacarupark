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
package com.github.fabriciofx.mandacarupark.regra;

import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Regra;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;

public final class MensalistaSql implements Regra {
    private final Session session;
    private final Dinheiro valor;

    public MensalistaSql(final Session session, final Dinheiro valor) {
        this.session = session;
        this.valor = valor;
    }

    @Override
    public boolean avalie(final Id id, final Periodo periodo) {
        boolean pago = false;
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT pago FROM mensalista WHERE id = '%s' AND (inicio <= '%s' AND termino >= '%s')",
                    id,
                    periodo.inicio().dateTime(),
                    periodo.termino().dateTime()
                ).asString()
            ).result()
        ) {
            if (rset.next()) {
                pago = rset.getBoolean(1);
            }
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
        return pago;
    }

    @Override
    public Dinheiro valor(final Id id, final Periodo periodo) {
        return this.valor;
    }
}
