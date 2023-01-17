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
package com.github.fabriciofx.mandacarupark.locacao;

import com.github.fabriciofx.mandacarupark.Data;
import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.data.DataMap;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.stmt.Select;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.sql.ResultSet;
import java.util.regex.Matcher;

public final class LocacaoSql implements Locacao {
    private final Session session;
    private final Id id;

    public LocacaoSql(final Session session, final Id id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Data sobre() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT placa, entrada, saida, valor FROM locacao WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final Placa placa;
            final DataHora entrada, saida;
            final Dinheiro valor;
            if (rset.next()) {
                placa = new PlacaOf(rset.getString(1));
                entrada = new DataHoraOf(rset.getString(2));
                if (rset.getString(3) != null) {
                    saida = new DataHoraOf(rset.getString(3));
                } else {
                    saida = new DataHoraOf();
                }
                if (rset.getString(4) != null) {
                    valor = new DinheiroOf(rset.getString(4));
                } else {
                    valor = new DinheiroOf("0.00");
                }
            } else {
                throw new RuntimeException(
                    "Dados sobre a locação são inexistentes ou inválidos!"
                );
            }
            return new DataMap(
                "placa", placa,
                "entrada", entrada,
                "saida", saida,
                "valor", valor
            );
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Media print(final Media media) {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT placa, entrada, saida, valor FROM locacao WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final Placa placa;
            final DataHora entrada, saida;
            final Dinheiro valor;
            if (rset.next()) {
                placa = new PlacaOf(rset.getString(1));
                entrada = new DataHoraOf(rset.getString(2));
                if (rset.getString(3) != null) {
                    saida = new DataHoraOf(rset.getString(3));
                } else {
                    saida = new DataHoraOf();
                }
                if (rset.getString(4) != null) {
                    valor = new DinheiroOf(rset.getString(4));
                } else {
                    valor = new DinheiroOf("0.00");
                }
            } else {
                throw new RuntimeException(
                    "Dados sobre a locação são inexistentes ou inválidos!"
                );
            }
            return media
                .with("placa", placa.toString())
                .with("entrada", entrada.toString())
                .with("saida", saida.toString())
                .with("valor", Matcher.quoteReplacement(valor.toString()));
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
