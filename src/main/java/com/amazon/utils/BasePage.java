package com.amazon.utils;

import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.BeforeAll;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;

public class BasePage {

    private String BASE_URL = "https://www.amazon.com";

    @BeforeAll
    protected void setUpBrowser() {
        DriverManager.configureDriver();
        navigateToMainUrl(BASE_URL);
        maximizeBrowserWindow();
    }

    public static void navigateToMainUrl(String url) {
        open(url);
        System.out.println("Starting Page URL: " + url);
    }

    public static void maximizeBrowserWindow() {
        Selenide.webdriver().driver().getWebDriver().manage().window().maximize();
    }

    public static void waitForPageToLoad(long timeOutInSeconds) {
        try {
            Selenide.Wait().until(webDriver ->
                                          ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
                                                  .equals("complete"));
        } catch (Throwable e) {
            System.out.println("Error occurred while waiting for Page to Load: " + e);
        }
    }

    public static void waitSleep(int timeoutInSeconds) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        CompletableFuture<Void> future = new CompletableFuture<>();
        executor.schedule(() -> future.complete(null), timeoutInSeconds, TimeUnit.SECONDS);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException ignored) {
        } finally {
            executor.shutdown();
        }
    }
}
