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
import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Select;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SqlPagamento implements Pagamento {
    private final Session session;
    private final Uuid id;

    public SqlPagamento(final Session session, final Uuid id) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public LocalDateTime dataHora() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSSX"
        );
        String sql = String.format(
            "SELECT datahora FROM pagamento WHERE id = '%s'",
            this.id
        );
        try {
            LocalDateTime dataHora;
            ResultSet rset = new Select(this.session, sql).result();
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
        String sql = String.format(
            "SELECT valor FROM pagamento WHERE id = '%s'",
            this.id
        );
        try {
            Dinheiro valor;
            ResultSet rset = new Select(this.session, sql).result();
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
