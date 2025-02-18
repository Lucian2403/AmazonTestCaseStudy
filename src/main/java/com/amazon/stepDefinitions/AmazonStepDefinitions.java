package com.amazon.stepDefinitions;

import com.amazon.pages.AmazonHomePage;
import com.amazon.utils.BasePage;
import com.codeborne.selenide.ex.ElementNotFound;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class AmazonStepDefinitions extends BasePage {

    AmazonHomePage homePage = new AmazonHomePage();

    @Given("user is on Amazon home page")
    public void verifyHomePagePresence() {
        setUpBrowser();
        System.out.println("TEST!!!");
        homePage.verifyHomePageLogo();
    }

    @And("the user deliver address is (.*)$")
    public void changeDeliverAddress(String addressName) {
        try {
            homePage.verifyDeliverAddress(addressName);
        } catch (ElementNotFound ignore) {}
        homePage.changeDeliverAddress(addressName);

    }

    @When("the user searches for Snickers")
    public void searchProduct(String productName) {

    }

}
