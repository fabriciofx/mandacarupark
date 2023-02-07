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
        final StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"center\">\n");
        if (this.number - 1 >= 1) {
            sb.append(
                new Sprintf(
                    "\t\t  <a href=\"%s?page=%d\">< Anterior</a>\n",
                    this.url,
                    this.number - 1
                ).asString()
            );
        } else {
            sb.append("\t\t  < Anterior\n");
        }
        for (int num = 1; num <= this.pages.count(); num++) {
            if (num == this.number) {
                sb.append(num);
            } else {
                sb.append(
                    new Sprintf(
                        "\t\t  <a href=\"%s?page=%d\">%d</a>\n",
                        this.url,
                        num,
                        num
                    ).asString()
                );
            }
        }
        if (this.number + 1 <= this.pages.count()) {
            sb.append(
                new Sprintf(
                    "\t\t  <a href=\"%s?page=%d\">Próximo ></a>\n",
                    this.url,
                    this.number + 1
                ).asString()
            );
        } else {
            sb.append("\t\t  Próximo >\n");
        }
        sb.append("\t\t</div>\n");
        return sb.toString();
    }
}
