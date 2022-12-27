/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022 Fabrício Barros Cabral
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
package com.github.fabriciofx.mandacarupark.web.browser;

import com.github.fabriciofx.mandacarupark.web.Browser;
import com.github.fabriciofx.mandacarupark.web.command.Wait;
import java.io.IOException;
import java.net.URI;

public final class LinuxBrowser implements Browser {
    private final static String[] NAMES = {
        "chromium", "google-chrome", "firefox", "mozilla-firefox",
        "mozilla", "konqueror", "netscape", "opera", "midori"
    };

    @Override
    public boolean match(final String name) {
        return name.toLowerCase().contains("linux");
    }

    @Override
    public void open(final URI uri) throws IOException {
        for (final String name : LinuxBrowser.NAMES) {
            try {
                new Wait(
                    String.format(
                        "%s %s",
                        name,
                        uri.toURL().toString()
                    )
                ).exec();
                break;
            } catch (final IOException ex) {
                throw ex;
            }
        }
    }
}
