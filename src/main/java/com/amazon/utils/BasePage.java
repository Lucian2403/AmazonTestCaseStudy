package com.amazon.utils;

import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.BeforeAll;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;

public class BasePage {

    private final String BASE_URL = "https://www.amazon.com";

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

    public static void smartClick(SelenideElement target) {
        try {
            target.shouldBe(Condition.exist)
                    .shouldBe(Condition.enabled)
                    .shouldBe(Condition.visible)
                    .hover();
            target.click();
        } catch (Exception | Error e) {
            System.out.println(e.getMessage());
            System.out.println("Click on " + target.toString() + " with JS executor");
            clickWithJS(target);
        }
    }

    public void smartSetValue(SelenideElement target, String stringValue) {
        try {
            target.should(Condition.exist)
                    .shouldBe(interactable);
            target.setValue(stringValue);
        } catch (Exception | Error e) {
            System.out.println("Failed to write on " + target.toString());
            System.out.println(e.getMessage());
            executeJavaScript("arguments[0].value='{}'", stringValue, $(target));
        }

    }

    public static void clickWithJS(SelenideElement element) {
        executeJavaScript("arguments[0].click();", element);
    }
}
