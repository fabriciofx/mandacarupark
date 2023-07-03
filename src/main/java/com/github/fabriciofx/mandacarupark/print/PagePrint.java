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
package com.github.fabriciofx.mandacarupark.print;

import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.Printer;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
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
    public Template print(final Template template) {
        final Pattern find = Pattern.compile(this.regex);
        final Matcher matcher = find.matcher(new String(template.bytes()));
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Printer printer : this.page.content()) {
                Template md = new HtmlTemplate(matcher.group(1));
                md = new HtmlTemplate(printer.print(md));
                sb.append(new String(md.bytes()));
            }
        }
        return new HtmlTemplate(
            new String(template.bytes()).replaceAll(
                this.regex,
                sb.toString()
            )
        );
    }
}
