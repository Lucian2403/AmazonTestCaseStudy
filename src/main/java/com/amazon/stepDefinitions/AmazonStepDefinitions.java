package com.amazon.stepDefinitions;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;

import com.amazon.pages.AmazonHomePage;
import com.amazon.pages.AmazonProductPage;
import com.amazon.utils.BasePage;
import com.codeborne.selenide.ex.ElementNotFound;
import com.diogonunes.jcolor.Attribute;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import java.util.List;

public class AmazonStepDefinitions extends BasePage {

    AmazonHomePage homePage = new AmazonHomePage();
    AmazonProductPage productPage = new AmazonProductPage();

    @Given("user is on Amazon home page")
    public void verifyHomePagePresence() {
        setUpBrowser();
        homePage.verifyHomePageLogo();
    }

    @And("the user deliver address is (.*)$")
    public void changeDeliverAddress(String addressName) {
        try {
            homePage.verifyDeliverAddress(addressName);
        } catch (ElementNotFound ignore) {}
        homePage.changeDeliverAddress(addressName);

    }

    @When("the user searches and adds the cheapest items to the basket")
    public void processProducts(List<String> products) {
        for (String product : products) {
            homePage.searchProduct(product);
            productPage.addCheapestItemToCart(product);
        }
    }

}
