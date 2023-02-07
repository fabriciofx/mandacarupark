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
        this.browser = new Cached<>(
            () -> {
                final ChromeDriverService service = new ChromeDriverService
                    .Builder()
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
