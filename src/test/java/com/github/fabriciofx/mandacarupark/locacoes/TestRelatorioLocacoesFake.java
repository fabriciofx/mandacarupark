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
package com.github.fabriciofx.mandacarupark.locacoes;

import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.entrada.EntradaFake;
import com.github.fabriciofx.mandacarupark.entradas.EntradasFake;
import com.github.fabriciofx.mandacarupark.estacionamento.EstacionamentoFake;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.pagamento.PagamentoFake;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosFake;
import com.github.fabriciofx.mandacarupark.periodo.PeriodoOf;
import com.github.fabriciofx.mandacarupark.placa.PlacaOf;
import com.github.fabriciofx.mandacarupark.regras.RegrasFake;
import com.github.fabriciofx.mandacarupark.saida.SaidaFake;
import com.github.fabriciofx.mandacarupark.saidas.SaidasFake;
import com.jcabi.matchers.XhtmlMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import java.time.LocalDateTime;

public class TestRelatorioLocacoesFake {
    @Test
    public void relatorio() throws Exception {
        final Pagamentos pagamentos = new PagamentosFake(
            new PagamentoFake(
                new Uuid("d589e997-c61d-47b3-90cf-c5ba23c8b427"),
                new DataHoraOf(LocalDateTime.of(2022, 8, 2, 11, 30)),
                new DinheiroOf("5.00")
            ),
            new PagamentoFake(
                new Uuid("e90107c4-f792-4569-abb3-353605f01716"),
                new DataHoraOf(LocalDateTime.of(2022, 8, 2, 11, 10)),
                new DinheiroOf("5.00")
            ),
            new PagamentoFake(
                new Uuid("15e32c1b-55ef-4cc6-a9bf-28da74d2309a"),
                new DataHoraOf(LocalDateTime.of(2022, 8, 2, 11, 20)),
                new DinheiroOf("5.00")
            )
        );
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                new RelatorioXml(
                    new LocacoesFake(
                        new EstacionamentoFake(
                            new EntradasFake(
                                pagamentos,
                                new EntradaFake(
                                    pagamentos,
                                    new Uuid(
                                        "d589e997-c61d-47b3-90cf-c5ba23c8b427"
                                    ),
                                    new PlacaOf("ABC1234"),
                                    new DataHoraOf(
                                        LocalDateTime.of(2022, 8, 2, 10, 30)
                                    )
                                ),
                                new EntradaFake(
                                    pagamentos,
                                    new Uuid(
                                        "e90107c4-f792-4569-abb3-353605f01716"
                                    ),
                                    new PlacaOf("DEF5678"),
                                    new DataHoraOf(
                                        LocalDateTime.of(2022, 8, 2, 10, 31)
                                    )
                                ),
                                new EntradaFake(
                                    pagamentos,
                                    new Uuid(
                                        "15e32c1b-55ef-4cc6-a9bf-28da74d2309a"
                                    ),
                                    new PlacaOf("GHI9012"),
                                    new DataHoraOf(
                                        LocalDateTime.of(2022, 8, 2, 10, 32)
                                    )
                                )
                            ),
                            new SaidasFake(
                                new SaidaFake(
                                    new Uuid(
                                        "d589e997-c61d-47b3-90cf-c5ba23c8b427"
                                    ),
                                    new PlacaOf("ABC1234"),
                                    new DataHoraOf(
                                        LocalDateTime.of(2022, 8, 2, 11, 40)
                                    )
                                ),
                                new SaidaFake(
                                    new Uuid(
                                        "e90107c4-f792-4569-abb3-353605f01716"
                                    ),
                                    new PlacaOf("DEF5678"),
                                    new DataHoraOf(
                                        LocalDateTime.of(2022, 8, 2, 11, 15)
                                    )
                                ),
                                new SaidaFake(
                                    new Uuid(
                                        "15e32c1b-55ef-4cc6-a9bf-28da74d2309a"
                                    ),
                                    new PlacaOf("GHI9012"),
                                    new DataHoraOf(
                                        LocalDateTime.of(2022, 8, 2, 11, 22)
                                    )
                                )
                            ),
                            pagamentos,
                            new RegrasFake()
                        )
                    ),
                    new PeriodoOf(
                        LocalDateTime.of(2022, 8, 2, 10, 30),
                        LocalDateTime.of(2022, 8, 2, 11, 40)
                    )
                ).conteudo()
            ),
            XhtmlMatchers.hasXPaths(
                "/locacoes/locacao/placa[text()='ABC1234']",
                "/locacoes/locacao/entrada[text()='02/08/2022 10:30:00']",
                "/locacoes/locacao/saida[text()='02/08/2022 11:40:00']",
                "/locacoes/locacao/valor[text()='R$ 5,00']",
                "/locacoes/locacao/placa[text()='DEF5678']",
                "/locacoes/locacao/entrada[text()='02/08/2022 10:31:00']",
                "/locacoes/locacao/saida[text()='02/08/2022 11:15:00']",
                "/locacoes/locacao/valor[text()='R$ 5,00']",
                "/locacoes/locacao/placa[text()='GHI9012']",
                "/locacoes/locacao/entrada[text()='02/08/2022 10:32:00']",
                "/locacoes/locacao/saida[text()='02/08/2022 11:22:00']",
                "/locacoes/locacao/valor[text()='R$ 5,00']",
                "/locacoes/total[text()='R$ 15,00']"
            )
        );
    }
}
