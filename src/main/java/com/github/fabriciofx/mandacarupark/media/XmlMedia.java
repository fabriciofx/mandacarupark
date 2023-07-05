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
package com.github.fabriciofx.mandacarupark.media;

import com.github.fabriciofx.mandacarupark.Media;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

public final class XmlMedia implements Media {
    private final Directives directives;

    public XmlMedia() {
        this(new Directives());
    }

    public XmlMedia(final String name) {
        this(new Directives().add(name));
    }

    public XmlMedia(final Directives directives) {
        this.directives = directives;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T select(final String query) {
        final XML xml = new XMLDocument(this.toString());
        return (T) xml.xpath(query).get(0);
    }

    @Override
    public Media begin(final String name) {
        return new XmlMedia(this.directives.add(name));
    }

    @Override
    public Media end(final String name) {
        return new XmlMedia(this.directives.up());
    }

    @Override
    public Media with(final String key, final Object value) {
        return new XmlMedia(this.directives.add(key).set(value).up());
    }

    @Override
    public String toString() {
        try {
            return new Xembler(this.directives).xml();
        } catch (ImpossibleModificationException ex) {
            throw new RuntimeException(ex);
        }
    }
}
