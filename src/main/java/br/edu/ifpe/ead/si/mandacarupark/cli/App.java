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
package br.edu.ifpe.ead.si.mandacarupark.cli;

import br.edu.ifpe.ead.si.mandacarupark.Contas;
import br.edu.ifpe.ead.si.mandacarupark.Dinheiro;
import br.edu.ifpe.ead.si.mandacarupark.Entradas;
import br.edu.ifpe.ead.si.mandacarupark.Estacionamento;
import br.edu.ifpe.ead.si.mandacarupark.Pagamentos;
import br.edu.ifpe.ead.si.mandacarupark.Saidas;
import br.edu.ifpe.ead.si.mandacarupark.conta.DomingoGratis;
import br.edu.ifpe.ead.si.mandacarupark.conta.Tolerancia;
import br.edu.ifpe.ead.si.mandacarupark.conta.ValorFixo;
import br.edu.ifpe.ead.si.mandacarupark.db.DataSourceH2File;
import br.edu.ifpe.ead.si.mandacarupark.db.ScriptSql;
import br.edu.ifpe.ead.si.mandacarupark.db.Server;
import br.edu.ifpe.ead.si.mandacarupark.db.ServerH2;
import br.edu.ifpe.ead.si.mandacarupark.db.Session;
import br.edu.ifpe.ead.si.mandacarupark.db.SessionNoAuth;
import br.edu.ifpe.ead.si.mandacarupark.sql.EntradasSql;
import br.edu.ifpe.ead.si.mandacarupark.sql.EstacionamentoSql;
import br.edu.ifpe.ead.si.mandacarupark.sql.PagamentosSql;
import br.edu.ifpe.ead.si.mandacarupark.sql.SaidasSql;

public class App {
    public static void main(String[] args) {
        try (
            final Server server = new ServerH2(
                new SessionNoAuth(
                    new DataSourceH2File("mandacarupark")
                ),
                new ScriptSql("mandacarupark.sql")
            ).start()
        ) {
            final Session session = server.session();
            final Entradas entradas = new EntradasSql(session);
            final Saidas saidas = new SaidasSql(session);
            final Pagamentos pagamentos = new PagamentosSql(session);
            final Estacionamento estacionamento = new EstacionamentoSql(
                session,
                entradas,
                saidas,
                pagamentos,
                new Contas(
                    new DomingoGratis(),
                    new Tolerancia(),
                    new ValorFixo(new Dinheiro("5.00"))
                )
            );
            final Console console = new Consoles().console();
            new Menu(
                console,
                "-------------- MandacaruPark --------------",
                new OpcaoEntrada("1 - Entrada", console, estacionamento),
                new OpcaoSaida("2 - Saída", console, estacionamento, entradas),
                new Encerrar(
                    new OpcaoEncerrar(
                        "9 - Encerrar",
                        console,
                        entradas
                    )
                )
            ).run();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
