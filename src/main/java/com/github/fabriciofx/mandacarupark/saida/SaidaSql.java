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
package com.github.fabriciofx.mandacarupark.saida;

import com.github.fabriciofx.mandacarupark.Data;
import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.data.DataMap;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.Select;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;

public final class SaidaSql implements Saida {
    private final Session session;
    private final Id id;

    public SaidaSql(final Session session, final Id id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Id id() {
        return this.id;
    }

    @Override
    public Data sobre() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT placa, datahora FROM saida WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final DataHora dataHora;
            final Placa placa;
            if (rset.next()) {
                placa = new PlacaOf(rset.getString(1));
                dataHora = new DataHoraOf(rset.getString(2));

            } else {
                throw new RuntimeException(
                    "Dados sobre a saída inexistentes ou inválidos!"
                );
            }
            return new DataMap(
                "id", this.id,
                "placa", placa,
                "dataHora", dataHora
            );
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
