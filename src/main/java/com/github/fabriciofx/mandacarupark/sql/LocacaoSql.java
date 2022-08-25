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
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Uuid;
import com.github.fabriciofx.mandacarupark.db.Select;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LocacaoSql implements Locacao {
    private final Session session;
    private final Uuid id;

    public LocacaoSql(final Session session, final Uuid id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Map<String, String> sobre() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT placa, entrada, saida, valor FROM locacao WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final Map<String, String> dados;
            if (rset.next()) {
                final DataHora entrada = new DataHora(rset.getString(2));
                final DataHora saida = new DataHora(rset.getString(3));
                dados = Map.of(
                    "placa", rset.getString(1),
                    "entrada", entrada.toString(),
                    "saida", saida.toString(),
                    "valor", rset.getString(4)
                );
            } else {
                throw new RuntimeException(
                    "Dados sobre a locação são inexistentes ou inválidos!"
                );
            }
            return dados;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}