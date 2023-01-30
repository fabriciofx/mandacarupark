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
package com.github.fabriciofx.mandacarupark.web.http;

import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.db.Session;
import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkWithType;
import org.takes.tk.TkWrap;

public final class TkRoutes extends TkWrap {
    public TkRoutes(final Estacionamento estacionamento) {
        super(
            new TkFork(
                new FkRegex("/robots.txt", ""),
                new FkRegex(
                    "/css/.+\\.css",
                    new TkWithType(
                        new TkClasspath("/webapp"),
                        "text/css"
                    )
                ),
                new FkRegex(
                    "/",
                    new TkEntradas(estacionamento)
                ),
                new FkRegex(
                    "/entradas",
                    new TkFork(
                        new FkMethods("GET", new TkEntradas(estacionamento))
                    )
                ),
                new FkRegex(
                    "/saidas",
                    new TkFork(
                        new FkMethods("GET", new TkSaidas(estacionamento))
                    )
                ),
                new FkRegex(
                    "/pagamentos",
                    new TkFork(
                        new FkMethods("GET", new TkPagamentos(estacionamento))
                    )
                ),
                new FkRegex(
                    "/locacoes",
                    new TkFork(
                        new FkMethods("GET", new TkLocacoes(estacionamento))
                    )
                ),
                new FkRegex(
                    "/entrada",
                    new TkFork(
                        new FkMethods("POST", new TkEntrada(estacionamento))
                    )
                ),
                new FkRegex(
                    "/saida",
                    new TkFork(
                        new FkMethods("POST", new TkSaida(estacionamento))
                    )
                ),
                new FkRegex(
                    "/pagamento",
                    new TkFork(
                        new FkMethods("POST", new TkPagamento(estacionamento))
                    )
                ),
                new FkRegex(
                    "/(?<path>[^/]+)",
                    new TkPage()
                )
            )
        );
    }
}

