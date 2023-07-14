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
package com.github.fabriciofx.mandacarupark.template;

import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.text.Text;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class HtmlTemplate implements Template {
    private final Text content;

    public HtmlTemplate(final InputStream stream) {
        this(
            () -> {
                try {
                    return new String(
                        stream.readAllBytes(),
                        StandardCharsets.UTF_8
                    );
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        );
    }

    public HtmlTemplate(final String content) {
        this.content = () -> content;
    }

    public HtmlTemplate(final Text text) {
        this.content = text;
    }

    @Override
    public Template create(final String content) {
        return new HtmlTemplate(content);
    }

    @Override
    public Template with(final String key, final Object value) {
        return new HtmlTemplate(
            this.content.asString().replaceAll(
                "\\$\\{" + key + "}",
                value.toString()
            )
        );
    }

    @Override
    public byte[] bytes() {
        return this.toString().getBytes();
    }

    @Override
    public String asString() {
        return this.content.asString();
    }
}
