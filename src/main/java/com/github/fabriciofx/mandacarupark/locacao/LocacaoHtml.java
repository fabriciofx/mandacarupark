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
package com.github.fabriciofx.mandacarupark.locacao;

import com.github.fabriciofx.mandacarupark.Locacao;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Html;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.media.MemMedia;

public final class LocacaoHtml implements Locacao, Html {
    private final Locacao origin;
    private final Template template;

    public LocacaoHtml(final Locacao locacao, final Template template) {
        this.origin = locacao;
        this.template = template;
    }

    @Override
    public Media sobre(final Media media) {
        return this.origin.sobre(media);
    }

    @Override
    public String html() {
        final Media media = this.sobre(new MemMedia());
        return this.template.with("placa", media.select("placa"))
            .with("entrada", media.select("entrada"))
            .with("saida", media.select("saida"))
            .with("valor", media.select("valor"))
            .asString();
    }
}
