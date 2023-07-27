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
package com.github.fabriciofx.mandacarupark;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public final class Chrome implements WebDriver {
    private final Supplier<WebDriver> browser;

    public Chrome() {
        this.browser = new Sticky<>(
            () -> {
                final ChromeDriverService service = new ChromeDriverService
                    .Builder()
                    .withLogOutput(System.out)
                    .build();
                service.sendOutputTo(OutputStream.nullOutputStream());
                final ChromeOptions options = new ChromeOptions();
                options.addArguments("--silent");
                options.addArguments("--headless");
                options.addArguments("--disable-logging");
                options.addArguments("--log-level=3");
                options.setExperimentalOption(
                    "excludeSwitches",
                    Collections.singletonList("enable-logging")
                );
                options.setExperimentalOption(
                    "excludeSwitches",
                    Collections.singletonList("enable-automation")
                );
                return new ChromeDriver(service, options);
            }
        );
    }

    @Override
    public void get(final String url) {
        this.browser.get().get(url);
    }

    @Override
    public String getCurrentUrl() {
        return this.browser.get().getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return this.browser.get().getTitle();
    }

    @Override
    public List<WebElement> findElements(final By by) {
        return this.browser.get().findElements(by);
    }

    @Override
    public WebElement findElement(final By by) {
        return this.browser.get().findElement(by);
    }

    @Override
    public String getPageSource() {
        return this.browser.get().getPageSource();
    }

    @Override
    public void close() {
        this.browser.get().close();
    }

    @Override
    public void quit() {
        this.browser.get().quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return this.browser.get().getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return this.browser.get().getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return this.browser.get().switchTo();
    }

    @Override
    public Navigation navigate() {
        return this.browser.get().navigate();
    }

    @Override
    public Options manage() {
        return this.browser.get().manage();
    }
}
