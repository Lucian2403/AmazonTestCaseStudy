package com.amazon.stepDefinitions;

import com.amazon.pages.AmazonHomePage;
import com.amazon.pages.AmazonSearchResultsPage;
import com.amazon.utils.BasePage;
import com.codeborne.selenide.ex.ElementNotFound;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class AmazonStepDefinitions extends BasePage {

    AmazonHomePage homePage = new AmazonHomePage();
    AmazonSearchResultsPage searchResultsPage = new AmazonSearchResultsPage();

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

    @When("the user searches for (.*)$")
    public void searchProduct(String productName) {
        homePage.searchProduct(productName);
    }

    @And("the user adds the cheapest (.*) to the basket$")
    public void addCheapestItemToCart(String productBrand) {
        searchResultsPage.filterByBrand(productBrand);
        searchResultsPage.sortPriceLowToHigh();
        searchResultsPage.checkIfProductAvailable();
        String priceValue = searchResultsPage.getPrice();
        System.out.println("Product Price: " + priceValue);
    }

}
