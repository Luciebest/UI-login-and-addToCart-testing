package com.cegeka.academy.qa.pageobjects;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckoutOverviewPage  extends BasePage{
   // private WebDriver driver;

    @FindBy(id="checkout_summary_container")
    private WebElement cartItemsContainer;
    @FindBy(css = "[data-test=finish]")
    private WebElement finishButton;
    @FindBy(className = "inventory_item_name")
    private List<WebElement> productElements;
    @FindBy(className="inventory_item_price")
    private List<WebElement> productPrices;
    @FindBy(className = "cart_quantity")
    private List<WebElement> productQuantities;

//    public CheckoutOverviewPage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//    }

    public CheckoutFinishPage clickFinishButton() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for finish button to be clickable.");
        }
        return new CheckoutFinishPage(/*driver*/);
    }
    public boolean isCheckoutOverviewContainerDisplayed() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
        try {
            return wait.until(ExpectedConditions.visibilityOf(cartItemsContainer)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    public boolean isProductDisplayed(String productName) {
        List<String> productNames = new ArrayList<>();
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);

        wait.until(ExpectedConditions.visibilityOfAllElements(productElements));
        for (WebElement product : productElements) {
            String name = wait.until(ExpectedConditions.visibilityOf(product)).getText();
            productNames.add(name);
            if (name.equals(productName)) {
                System.out.println("Product name in overview: " + name);
                return true;
            }
        }
        System.out.println("Product not found in overview: " + productName);
        return false;
    }
    public double getProductPrice(String productName) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);

        wait.until(ExpectedConditions.visibilityOfAllElements(productElements));
        for (int i = 0; i < productElements.size(); i++) {

            if (productElements.get(i).getText().equals(productName)) {
                String priceWithSymbol = productPrices.get(i).getText();
                String priceWithoutSymbol = priceWithSymbol.substring(1); // exclude the first character (which is $)
                return Double.parseDouble(priceWithoutSymbol);
            }
        }
        throw new NoSuchElementException("Product not found: " + productName);
    }
    public int getProductQuantity(String productName) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);

        wait.until(ExpectedConditions.visibilityOfAllElements(productElements));
        for (int i = 0; i < productElements.size(); i++) {
            if (productElements.get(i).getText().equals(productName)) {
                WebElement quantityElement = productQuantities.get(i);
                String quantityText = wait.until(ExpectedConditions.visibilityOf(quantityElement)).getText();
                return Integer.parseInt(quantityText);
            }
        }
        throw new NoSuchElementException("Product not found: " + productName);
    }
}
