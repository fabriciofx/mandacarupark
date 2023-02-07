package com.github.fabriciofx.mandacarupark.web.http;

import com.github.fabriciofx.mandacarupark.pagination.Pages;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
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
        return new Sprintf(
            "<div class=\"center\">\n%s%s%s\t\t</div>\n",
            new Footer.Anterior(this.url, this.number).asString(),
            new Footer.Numeros(
                this.url, this.number, this.pages.count()
            ).asString(),
            new Footer.Proximo(
                this.url, this.number, this.pages.count()
            ).asString()
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
