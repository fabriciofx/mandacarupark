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
package br.edu.ifpe.ead.si.mandacarupark.sql;

import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Locacao;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Select;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocacaoSql implements Locacao {
    private final Session session;
    private final Uuid id;

    public LocacaoSql(final Session session, final Uuid id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Placa placa() {
        final String sql = String.format(
            "SELECT placa FROM locacao WHERE id = '%s'",
            this.id
        );
        try (final ResultSet rset = new Select(this.session, sql).result()) {
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
    public LocalDateTime entrada() {
        final DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSSX"
        );
        final String sql = String.format(
            "SELECT entrada FROM locacao WHERE id = '%s'",
            this.id
        );
        try (final ResultSet rset = new Select(this.session, sql).result()) {
            final LocalDateTime dataHora;
            if (rset.next()) {
                dataHora = LocalDateTime.parse(rset.getString(1), formato);
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
    public LocalDateTime saida() {
        final DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSSX"
        );
        final String sql = String.format(
            "SELECT saida FROM locacao WHERE id = '%s'",
            this.id
        );
        try (final ResultSet rset = new Select(this.session, sql).result()) {
            final LocalDateTime dataHora;
            if (rset.next()) {
                dataHora = LocalDateTime.parse(rset.getString(1), formato);
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
        final String sql = String.format(
            "SELECT valor FROM pagamento WHERE id = '%s'",
            this.id
        );
        try (final ResultSet rset = new Select(this.session, sql).result()) {
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
