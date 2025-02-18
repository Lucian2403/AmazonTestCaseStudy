package com.amazon.pages;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.amazon.utils.BasePage;

public class AmazonHomePage extends BasePage {

    // ELEMENTS //
    public static SelenideElement amazonLogo = $x("//div[@id='navbar']//div[@id='nav-logo']");
    public static SelenideElement captchaForm = $x("//form[@action='/errors/validateCaptcha']");
    public static SelenideElement captchaReload = $x("//a[.='Try different image']");


    // METHODS//
    public void verifyHomePageLogo() {
        waitForPageToLoad(60);
        if (captchaForm.isDisplayed()) {
            waitSleep(4);
            refresh();
        }
        try {
            amazonLogo.shouldBe(Condition.exist)
                    .shouldBe(Condition.visible);
        } catch (ElementNotFound | Exception e) {
            waitSleep(4);
            captchaReload.click();
        }
        amazonLogo.shouldBe(Condition.exist)
                .shouldBe(Condition.visible);
        System.out.println("Entered Amazon Home Page.");
    }
}
