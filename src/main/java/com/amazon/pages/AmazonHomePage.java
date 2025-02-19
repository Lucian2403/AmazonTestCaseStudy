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
    public static SelenideElement homePageDeliverAddressBtn = $x("//div[@id='nav-global-location-slot']");
    public static String homeDeliverCountryNameAddress = "//div/span[@id='glow-ingress-line2'][contains(normalize-space(.), '%s')]";
    public static String deliverCountryDropDownOption = "//a[contains(@id,'GLUXCountryList')][contains(text(),'%s')]";
    public static SelenideElement selectDeliverCountryDropDown = $x("//select[@id='GLUXCountryList']");
    public static SelenideElement changeAddressContinueBtn = $x("//input[@id='GLUXConfirmClose']");
    public static SelenideElement changeAddressDoneBtn = $x("//button[@name='glowDoneButton']");
    public static SelenideElement mainSearchBtn = $x("//input[@aria-label='Search Amazon']");
    public static SelenideElement searchBtn = $x("//input[@type='submit']");


    // METHODS//
    public void verifyHomePageLogo() {
        waitForPageToLoad(60);
        if (captchaForm.isDisplayed() || !amazonLogo.isDisplayed()) {
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

    public void verifyDeliverAddress(String addressName) {
        $x(String.format(homeDeliverCountryNameAddress, addressName)).shouldBe(Condition.visible);
        System.out.println("The " + addressName + " is set to be the Deliver Address.");
    }

    public void changeDeliverAddress(String addressName) {
        smartClick(homePageDeliverAddressBtn);
        smartClick(selectDeliverCountryDropDown);
        smartClick($x(String.format(deliverCountryDropDownOption, addressName)));
        try {
            smartClick(changeAddressContinueBtn);
        } catch (ElementNotFound e) {
            smartClick(changeAddressDoneBtn);
        }
        System.out.println("The " + addressName + " was set to be the Deliver Address.");
        waitSleep(4);
    }

    public void searchProduct(String productName) {
        smartSetValue(mainSearchBtn, productName);
        smartClick(searchBtn);
        System.out.println("Searched the '" + productName + "' product.");
    }

}
