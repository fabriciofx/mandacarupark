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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public final class Browsers {
    private final List<Browser> browsers;

    public Browsers(final CountDownLatch cdl) {
        this(
            new Sync(cdl, new WindowsBrowser()),
            new Sync(cdl, new LinuxBrowser()),
            new Sync(cdl, new MacOsBrowser()),
            new Sync(cdl, new Win32Browser())
        );
    }

    public Browsers(final Browser... browser) {
        this(Arrays.asList(browser));
    }

    public Browsers(final List<Browser> browser) {
        this.browsers = browser;
    }

    public Browser browser() {
        final String name = System.getProperty("os.name", "linux");
        for (final Browser browser : this.browsers) {
            if (browser.match(name)) {
                return browser;
            }
        }
        throw new IllegalArgumentException("invalid operating system");
    }
}
