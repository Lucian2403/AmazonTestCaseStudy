package com.amazon.pages;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.back;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.CYAN_TEXT;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;
import static com.diogonunes.jcolor.Attribute.YELLOW_TEXT;

import com.amazon.utils.BasePage;
import com.codeborne.selenide.SelenideElement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmazonProductPage extends BasePage {

    // ELEMENTS //
    public static SelenideElement sortBySelection = $x("//select[@aria-label='Sort by:']");
    public static String filterByBrandOption = "//span[.='%s']//parent::a[contains(@aria-label,'filter')]";
    public static SelenideElement searchResultItem;
    public static SelenideElement outOfStockProduct = $x("//div[@id='outOfStock']");
    public static SelenideElement priceNumberSmall = $x("//span[@id='_price']/span");
    public static SelenideElement priceNumberBig = $x("//div[@id='corePrice_desktop']//span[@class='a-offscreen']");
    public static SelenideElement allBuyingOptionsBtn = $x("//span[@id='buybox-see-all-buying-choices']//a");
    public static SelenideElement newOfferPrice = $x("//span[@id='aod-price-1']//span[@class='aok-offscreen']");
    public static SelenideElement addToCartBigBtn = $x("//input[@id='add-to-cart-button']");
    public static SelenideElement addToCartSmallBtn = $x("//input[@name='submit.addToCart']");
    public static SelenideElement addedSuccessMsg = $x("//div[@id='aod-offer-added-to-cart-1']//div[@class='a-alert-content']");

    AmazonHomePage homePage = new AmazonHomePage();

    // METHODS//
    public void filterByBrand(String productBrand) {
        smartClick($x(String.format(filterByBrandOption, productBrand)));
    }

    public void sortPriceLowToHigh() {
        waitSleep(2);
        sortBySelection.selectOption("Price: Low to High");
    }

    public void checkIfProductAvailable() {
        waitSleep(2);
        int productNumber = 1;
        while (true) {
            String searchResultDynamic = String.format(
                    "//div[@data-cel-widget='search_result_%d']//span[@data-component-type='s-product-image']/a",
                    productNumber);
            searchResultItem = $x(searchResultDynamic);
            if (searchResultItem.exists()) {
                System.out.println("Selecting the product number " + productNumber);
                smartClick(searchResultItem);
                // Check if the product is out of stock
                if (outOfStockProduct.isDisplayed()) {
                    System.out.println(colorize("The Product is OUT OF STOCK! Returning to search results...", CYAN_TEXT()));
                    back();
                } else {
                    System.out.println(colorize("The product is AVAILABLE.", GREEN_TEXT()));
                    break;
                }
            }
            productNumber++;
        }
    }

    public String getPrice() {
        waitForPageToLoad(10);
        String priceValue = "";
        String regex = "\\$(\\d+\\.\\d+)";
        Pattern pattern = Pattern.compile(regex);
        if (priceNumberSmall.isDisplayed()) {
            // Get the price variant with small text
            String textPriceSmall = priceNumberSmall.getText();
            Matcher matcher = pattern.matcher(textPriceSmall);
            if (matcher.find()) {
                String price = matcher.group();
                //Remove the $ sign
                priceValue = price.substring(1);
                System.out.println("Initial Price: " + priceValue);
            }
            return priceValue;
        } else if (priceNumberBig.exists()) {
            // Get the price variant with big text
            String price = priceNumberBig.innerText();
            priceValue = price.substring(1);
            System.out.println("Price: " + priceValue);
            return priceValue;
        } else {
            System.out.println("No price found.");
            return null;
        }
    }

    public String checkTheOfferPrice() {
        waitSleep(3);
        String mainPrice = getPrice();
        // Check if the new offer price is the same as the main price
        String newPriceValue = "";
        String regex = "\\$(\\d+\\.\\d+)";
        Pattern pattern = Pattern.compile(regex);
        String newTextPrice = newOfferPrice.innerText();
        Matcher matcher = pattern.matcher(newTextPrice);
        if (matcher.find()) {
            String price = matcher.group();
            //Remove the $ sign
            newPriceValue = price.substring(1);
            System.out.println("New Offer Price: " + newPriceValue);
            if (mainPrice.equals(newPriceValue)) {
                System.out.println(colorize("THE MAIN PRICE IS THE SAME AS THE NEW OFFER PRICE.", YELLOW_TEXT()));
            } else {
                System.out.println(colorize("THE MAIN PRICE IS DIFFERENT FROM THE NEW OFFER PRICE. UPDATING THE PRODUCT PRICE...", YELLOW_TEXT()));
            }
            return newPriceValue;
        } else {
            return null;
        }
    }

    public String checkTheOfferPriceAndAddToCart() {
        String mainPrice = getPrice();
        if (allBuyingOptionsBtn.isDisplayed()) {
            smartClick(allBuyingOptionsBtn);
            String newPriceValue = checkTheOfferPrice();
            waitSleep(4);
            smartClick(addToCartSmallBtn);
            // Verify the text "Added" is present
            assertElementEquals(addedSuccessMsg, "Added");
            return newPriceValue;
        } else {
            System.out.println("No new offer price found.");
            smartClick(addToCartBigBtn);
            return mainPrice;
        }
    }

    //MAIN METHOD FOR THIS PAGE
    public void addCheapestItemToCart(String productBrand) {
        filterByBrand(productBrand);
        sortPriceLowToHigh();
        checkIfProductAvailable();
        String priceValue = getPrice();
        System.out.println(colorize(productBrand + " Main Price: " + priceValue, GREEN_TEXT()));
        String updatedProductPrice = checkTheOfferPriceAndAddToCart();
        System.out.println(colorize(productBrand + " UPDATED FINAL PRICE: " + updatedProductPrice, GREEN_TEXT()));
        System.out.println(colorize("The product " + productBrand + " was added to cart.", GREEN_TEXT()));
        // Navigate to home page
        navigateToMainUrl(BASE_URL);
        homePage.verifyHomePageLogo();
    }

}
