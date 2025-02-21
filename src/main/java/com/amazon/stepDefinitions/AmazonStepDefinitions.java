package com.amazon.stepDefinitions;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.BRIGHT_CYAN_TEXT;

import com.amazon.pages.AmazonHomePage;
import com.amazon.pages.AmazonProductPage;
import com.amazon.pages.AmazonShoppingCartPage;
import com.amazon.utils.BasePage;
import com.codeborne.selenide.ex.ElementNotFound;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmazonStepDefinitions extends BasePage {

    AmazonHomePage homePage = new AmazonHomePage();
    AmazonProductPage productPage = new AmazonProductPage();
    AmazonShoppingCartPage shoppingCartPage = new AmazonShoppingCartPage();
    private final Map<String, String> productPrices = new HashMap<>();

    @Given("user is on Amazon home page")
    public void verifyHomePagePresence() {
        setUpBrowser();
        homePage.verifyHomePageLogo();
    }

    @And("the user deliver address is (.*)$")
    public void changeDeliverAddress(String addressName) {
        try {
            homePage.verifyDeliverAddress(addressName);
        } catch (ElementNotFound ignore) {
        }
        homePage.changeDeliverAddress(addressName);

    }

    @When("the user searches and adds the cheapest items to the basket")
    public Map<String, String> processProducts(List<String> products) {
        for (String product : products) {
            homePage.searchProduct(product);
            String price = productPage.addCheapestItemToCart(product);
            productPrices.put(product, price);
        }
        for (Map.Entry<String, String> entry : productPrices.entrySet()) {
            System.out.println(
                    "Product page price of " + entry.getKey() + ": " + colorize(entry.getValue(), BRIGHT_CYAN_TEXT()));
        }
        return productPrices;
    }

    @And("the user enters the shopping cart page")
    public void enterShoppingCart() {
        shoppingCartPage.enterShoppingCart();
    }

    @Then("the basket should display the correct product's prices")
    public void verifyAndCompareShoppingCartPrices(List<String> products) {
        Map<String, String> productCartPrices = shoppingCartPage.getCartPrices(products);
        shoppingCartPage.comparePrices(productPrices, productCartPrices);
    }

    @And("the user should be redirected to the registration page on checkout")
    public void redirectToRegistrationPage() {
        shoppingCartPage.checkoutRedirection();
    }

}
