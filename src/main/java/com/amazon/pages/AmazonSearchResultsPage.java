package com.amazon.pages;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.amazon.utils.BasePage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmazonSearchResultsPage extends BasePage {

    // ELEMENTS //
    public static SelenideElement sortBySelection = $x("//select[@aria-label='Sort by:']");
    public static String filterByBrandOption = "//span[.='%s']//parent::a[contains(@aria-label,'filter')]";
    public static SelenideElement firstSearchResultItem = $x(
            "//div[@data-cel-widget='search_result_1']//span[@data-component-type='s-product-image']/a");
    public static SelenideElement searchResultItem;
    public static SelenideElement outOfStockProduct = $x("//div[@id='outOfStock']");
    public static SelenideElement priceNumberSmall = $x("//span[@id='_price']/span");
    public static SelenideElement priceNumberBig = $x("//div[@id='corePrice_desktop']//span[@class='a-offscreen']");
    public int priceNumber;


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
                    System.out.println("The Product is OUT OF STOCK! Returning to search results...");
                    back();
                } else {
                    System.out.println("The product is AVAILABLE.");
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
                System.out.println("Price: " + priceValue);
            }
            return priceValue;
        } else if (priceNumberBig.isDisplayed()) {
            // Get the price variant with big text
            String price = priceNumberBig.getText();
            priceValue = price.substring(1);
            System.out.println("Price: " + priceValue);
            return priceValue;
        } else {
            System.out.println("No price found.");
            return null;
        }
    }

    public void addToCartProduct() {

    }

}
