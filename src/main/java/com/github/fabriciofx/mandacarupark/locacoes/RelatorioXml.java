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

import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Relatorio;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.media.MapMedia;
import org.xembly.Directives;
import org.xembly.Xembler;

public final class RelatorioXml implements Relatorio {
    private final Locacoes locacoes;
    private final Periodo periodo;

    public RelatorioXml(final Locacoes locacoes, final Periodo periodo) {
        this.locacoes = locacoes;
        this.periodo = periodo;
    }

    @Override
    public String conteudo() throws Exception {
        final Directives directives = new Directives().add("locacoes");
        Dinheiro total = new DinheiroOf("0.00");
        for (final Locacao locacao : this.locacoes.pages(30, this.periodo).page(0).content()) {
            final Media media = locacao.sobre(new MapMedia());
            final Dinheiro valor = media.select("valor");
            total = new DinheiroOf(total.quantia().add(valor.quantia()));
            directives.add("locacao")
                .add("placa").set(media.select("placa")).up()
                .add("entrada").set(media.select("entrada")).up()
                .add("saida").set(media.select("saida")).up()
                .add("valor").set(valor).up()
                .up();
        }
        directives.add("total").set(total).up();
        return new Xembler(directives).xml();
    }
}
