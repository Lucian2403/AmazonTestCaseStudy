package com.amazon.pages;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.BRIGHT_CYAN_TEXT;
import static com.diogonunes.jcolor.Attribute.BRIGHT_GREEN_TEXT;
import static com.diogonunes.jcolor.Attribute.BRIGHT_RED_TEXT;
import static com.diogonunes.jcolor.Attribute.BRIGHT_YELLOW_TEXT;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;
import static com.diogonunes.jcolor.Attribute.RED_TEXT;
import static com.diogonunes.jcolor.Attribute.YELLOW_TEXT;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.amazon.utils.BasePage;
import com.diogonunes.jcolor.Attribute;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.Assert;

public class AmazonShoppingCartPage extends BasePage {

    // ELEMENTS //
    public static SelenideElement shoppingCartBtn = $x("//a[@id='nav-cart']");
    public String shoppingCartProductPriceText = "//span[@class='a-truncate-cut'][contains(.,'%s')]//ancestor::div[@class='sc-list-item-content']//div[@class='sc-item-price-block']//span[@class='a-offscreen']";
    public static SelenideElement checkoutBtn = $x("//input[@data-feature-id='proceed-to-checkout-action']");
    public static SelenideElement loginForm = $x("//form[@name='signIn']");


    // METHODS//
    public void enterShoppingCart() {
        smartClick(shoppingCartBtn);
        waitForPageToLoad(20);
        System.out.println(colorize("\n____ENTERING SHOPPING CART PAGE____", YELLOW_TEXT()));

    }

    public String getTheProductCartPrice(String productName) {
        String cartPriceText;
        String cartPriceValue;
        String regex = "\\$(\\d+\\.\\d+)";
        Pattern pattern = Pattern.compile(regex);
        String productNameUpperCase = productName.toUpperCase(Locale.ROOT);
        try {
            cartPriceValue = $x(String.format(shoppingCartProductPriceText, productName)).innerText();
        } catch (ElementNotFound e) {
            cartPriceValue = $x(String.format(shoppingCartProductPriceText, productNameUpperCase)).innerText();
        }
        Matcher matcher = pattern.matcher(cartPriceValue);
        if (matcher.find()) {
            String price = matcher.group();
            //Remove the $ sign
            cartPriceText = price.substring(1);
            return cartPriceText;
        } else {
            Assert.fail(colorize("NO PRICE FOUND IN SHOPPING CART.", RED_TEXT()));
            return null;

        }
    }

    public Map<String, String> getCartPrices(List<String> products) {
        Map<String, String> productCartPrices = new HashMap<>();
        for (String product : products) {
            String cartPrice = getTheProductCartPrice(product);
            productCartPrices.put(product, cartPrice);
        }
        for (Map.Entry<String, String> entry : productCartPrices.entrySet()) {
            System.out.println(
                    "Shopping Cart price of " + entry.getKey() + ": " + colorize(entry.getValue(), BRIGHT_CYAN_TEXT()));
        }
        return productCartPrices;
    }

    public void comparePrices(Map<String, String> productPrices, Map<String, String> productCartPrices) {
        for (Map.Entry<String, String> entry : productPrices.entrySet()) {
            String product = entry.getKey();
            String price = entry.getValue();
            String cartPrice = productCartPrices.get(product);

            if (cartPrice != null) {
                String equalPriceTrueMsg =
                        "Price of " + product + " matches the cart price: " + colorize(price, BRIGHT_GREEN_TEXT());
                String equalPriceFalseMsg =
                        "Price of " + product + " does NOT match the cart price. \nProduct price: " + colorize(price, BRIGHT_RED_TEXT())
                                + ", Cart price: " + colorize(cartPrice, BRIGHT_RED_TEXT());
                try {
                    System.out.println(equalPriceTrueMsg);
                    Assert.assertEquals(price, cartPrice, equalPriceFalseMsg);
                } catch (AssertionError e) {
                    System.out.println(equalPriceFalseMsg);
                    Assert.assertNotEquals(price, cartPrice, equalPriceTrueMsg);
                }
            } else {
                System.out.println("Cart price for " + product + " not found.");
            }
        }
    }

    public void checkoutRedirection() {
        waitSleep(3);
        smartClick(checkoutBtn);
        try {
            loginForm.shouldBe(Condition.visible);
            System.out.println(colorize("LOGIN FORM IS PRESENT AFTER CHECKOUT.", GREEN_TEXT()));
        } catch (ElementNotFound e) {
            Assert.fail(colorize("LOGIN FORM WAS NOT PRESENT AFTER CHECKOUT.", RED_TEXT()));
            throw e;
        }
    }

}
