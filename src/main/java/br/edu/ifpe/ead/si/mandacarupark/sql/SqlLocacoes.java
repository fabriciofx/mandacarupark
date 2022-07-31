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

import br.edu.ifpe.ead.si.mandacarupark.Locacao;
import br.edu.ifpe.ead.si.mandacarupark.Locacoes;
import br.edu.ifpe.ead.si.mandacarupark.Periodo;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import br.edu.ifpe.ead.si.mandacarupark.db.Select;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlLocacoes implements Locacoes {
    private final Session session;
    private final Periodo periodo;

    public SqlLocacoes(final Session session, final Periodo periodo) {
        this.session = session;
        this.periodo = periodo;
    }

    @Override
    public Iterator<Locacao> iterator() {
        String sql = String.format(
            "SELECT * FROM locacao WHERE entrada >= '%s' AND saida <= '%s'",
            this.periodo.inicio(), this.periodo.termino()
        );
        List<Locacao> items = new ArrayList<>();
        try {
            ResultSet rset = new Select(this.session, sql).result();
            while (rset.next()) {
                items.add(
                    new SqlLocacao(
                        this.session,
                        new Uuid(rset.getString(1))
                    )
                );
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return items.iterator();
    }
}
