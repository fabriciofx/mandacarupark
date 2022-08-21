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

public class LocacaoSql implements Locacao {
    private final Session session;
    private final Uuid id;

    public LocacaoSql(final Session session, final Uuid id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Placa placa() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT placa FROM locacao WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final Placa placa;
            if (rset.next()) {
                placa = new Placa(rset.getString(1));
            } else {
                throw new RuntimeException("Placa inexistente ou inválida!");
            }
            return placa;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DataHora entrada() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT entrada FROM locacao WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final DataHora dataHora;
            if (rset.next()) {
                dataHora = new DataHora(rset.getString(1));
            } else {
                throw new RuntimeException(
                    "Data/Hora inexistente ou inválida!"
                );
            }
            return dataHora;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DataHora saida() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT saida FROM locacao WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final DataHora dataHora;
            if (rset.next()) {
                dataHora = new DataHora(rset.getString(1));
            } else {
                throw new RuntimeException(
                    "Data/Hora inexistente ou inválida!"
                );
            }
            return dataHora;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Dinheiro valor() {
        try (
            final ResultSet rset = new Select(
                this.session,
                new Sprintf(
                    "SELECT valor FROM pagamento WHERE id = '%s'",
                    this.id
                )
            ).result()
        ) {
            final Dinheiro valor;
            if (rset.next()) {
                valor = new Dinheiro(rset.getBigDecimal(1));
            } else {
                throw new RuntimeException(
                    "Valor inexistente ou inválida!"
                );
            }
            return valor;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
