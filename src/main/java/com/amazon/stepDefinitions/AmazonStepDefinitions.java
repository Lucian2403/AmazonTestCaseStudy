package com.amazon.stepDefinitions;

import com.amazon.pages.AmazonHomePage;
import com.amazon.utils.BasePage;
import io.cucumber.java.en.Given;

public class AmazonStepDefinitions extends BasePage {

    @Given("user is on Amazon home page")
    public void verifyHomePagePresence() {
        setUpBrowser();
        System.out.println("TEST!!!");
        AmazonHomePage homePage = new AmazonHomePage();
        homePage.verifyHomePageLogo();
    }
}
