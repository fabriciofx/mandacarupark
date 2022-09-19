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
package com.github.fabriciofx.mandacarupark.cli;

import com.github.fabriciofx.mandacarupark.Contas;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.console.Console;
import com.github.fabriciofx.mandacarupark.console.Consoles;
import com.github.fabriciofx.mandacarupark.conta.DomingoGratis;
import com.github.fabriciofx.mandacarupark.conta.Tolerancia;
import com.github.fabriciofx.mandacarupark.conta.ValorFixo;
import com.github.fabriciofx.mandacarupark.db.DataSourceH2File;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.db.Server;
import com.github.fabriciofx.mandacarupark.db.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.SessionNoAuth;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.entradas.EntradasSql;
import com.github.fabriciofx.mandacarupark.estacionamento.EstacionamentoSql;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosSql;
import com.github.fabriciofx.mandacarupark.saidas.SaidasSql;

public final class App {
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
                    new ValorFixo(new DinheiroOf("5.00"))
                )
            );
            final Console console = new Consoles().console();
            new Menu(
                console,
                "-------------- MandacaruPark --------------",
                new OpcaoEntrada(
                    "1 - Entrada",
                    console,
                    estacionamento
                ),
                new OpcaoSaida(
                    "2 - Saída",
                    console,
                    estacionamento,
                    entradas
                ),
                new OpcaoPagamento(
                    "3 - Pagamento",
                    console,
                    estacionamento,
                    entradas
                ),
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
