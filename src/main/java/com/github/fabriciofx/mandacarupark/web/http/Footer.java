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

import com.github.fabriciofx.mandacarupark.pagination.Pages;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import com.github.fabriciofx.mandacarupark.text.SprintfOf;
import com.github.fabriciofx.mandacarupark.text.Text;

public final class Footer<T> implements Text {
    private final Pages<T> pages;
    private final int number;
    private final String url;

    public Footer(final Pages<T> pages, final int number, final String url) {
        this.pages = pages;
        this.number = number;
        this.url = url;
    }

    @Override
    public String asString() {
        return new SprintfOf(
            "<div class=\"center\">\n%s%s%s\t\t</div>\n",
            new Footer.Anterior(this.url, this.number),
            new Footer.Numeros(this.url, this.number, this.pages.count()),
            new Footer.Proximo(this.url, this.number, this.pages.count())
        ).asString();
    }

    static final private class Anterior implements Text {
        private final String url;
        private final int number;

        public Anterior(final String url, final int number) {
            this.url = url;
            this.number = number;
        }

        @Override
        public String asString() {
            final String content;
            if (this.number - 1 >= 1) {
                content = new Sprintf(
                    "\t\t  <a href=\"%s?page=%d\">< Anterior</a>\n",
                    this.url,
                    this.number - 1
                ).asString();
            } else {
                content = "\t\t  < Anterior\n";
            }
            return content;
        }
    }

    static final private class Numeros implements Text {
        private final String url;
        private final int number;
        private final int total;

        public Numeros(final String url, final int number, final int total) {
            this.url = url;
            this.number = number;
            this.total = total;
        }

        @Override
        public String asString() {
            final StringBuilder content = new StringBuilder();
            for (int num = 1; num <= this.total; num++) {
                if (num == this.number) {
                    content.append(num);
                } else {
                    content.append(
                        new Sprintf(
                            "\t\t  <a href=\"%s?page=%d\">%d</a>\n",
                            this.url,
                            num,
                            num
                        ).asString()
                    );
                }
            }
            return content.toString();
        }
    }

    static final private class Proximo implements Text {
        private final String url;
        private final int number;
        private final int total;

        public Proximo(final String url, final int number, final int total) {
            this.url = url;
            this.number = number;
            this.total = total;
        }

        @Override
        public String asString() {
            final String content;
            if (this.number + 1 <= this.total) {
                content = new Sprintf(
                    "\t\t  <a href=\"%s?page=%d\">Próximo ></a>\n",
                    this.url,
                    this.number + 1
                ).asString();
            } else {
                content = "\t\t  Próximo >\n";
            }
            return content;
        }
    }
}
