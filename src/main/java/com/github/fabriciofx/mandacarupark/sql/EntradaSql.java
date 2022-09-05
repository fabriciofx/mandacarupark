/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022 Fabrício Barros Cabral
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
package com.github.fabriciofx.mandacarupark.sql;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Uuid;
import com.github.fabriciofx.mandacarupark.dados.Dados;
import com.github.fabriciofx.mandacarupark.db.Select;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;

public final class EntradaSql implements Entrada {
    private final Session session;
    private final Uuid id;

    public EntradaSql(final Session session, final Uuid id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public Dados sobre() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT placa, datahora FROM entrada WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final DataHora dataHora;
            final Placa placa;
            if (rset.next()) {
                placa = new Placa(rset.getString(1));
                dataHora = new DataHora(rset.getString(2));
            } else {
                throw new RuntimeException(
                    "Dados sobre a entrada inexistentes ou inválidos!"
                );
            }
            return new Dados(
                "id", this.id,
                "placa", placa,
                "dataHora", dataHora
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
