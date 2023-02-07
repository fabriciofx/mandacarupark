package com.github.fabriciofx.mandacarupark.print;

import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Printer;
import com.github.fabriciofx.mandacarupark.media.HtmlTemplate;
import com.github.fabriciofx.mandacarupark.pagination.Page;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PagePrint<T extends Printer> implements Printer {
    private final String regex;
    private final Page<T> page;

    public PagePrint(final String regex, final Page<T> page) {
        this.regex = regex;
        this.page = page;
    }

    @Override
    public Media print(final Media media) {
        final Pattern find = Pattern.compile(this.regex);
        final Matcher matcher = find.matcher(new String(media.bytes()));
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Printer printer : this.page.content()) {
                Media md = new HtmlTemplate(matcher.group(1));
                md = new HtmlTemplate(printer.print(md));
                sb.append(new String(md.bytes()));
            }
        }
        return new HtmlTemplate(
            new String(media.bytes()).replaceAll(
                this.regex,
                sb.toString()
            )
        );
    }
}
