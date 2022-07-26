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

import br.edu.ifpe.ead.si.mandacarupark.Entrada;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Select;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SqlEntrada implements Entrada {
    private final Session session;
    private final Uuid id;

    public SqlEntrada(
        Session session,
        Uuid id
    ) {
        this.session = session;
        this.id = id;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public Placa placa() {
        String sql = String.format(
            "SELECT placa FROM entrada WHERE id = '%s'",
            this.id
        );
        try {
            Placa placa ;
            ResultSet rset = new Select(this.session, sql).result();
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
    public LocalDateTime dataHora() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSSX"
        );
        String sql = String.format(
            "SELECT datahora FROM entrada WHERE id = '%s'",
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
}
