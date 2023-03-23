package com.cegeka.academy.qa.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class ProductsPage extends BasePage{

    @FindBy(id = "inventory_container")
    private WebElement products;
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;
    @FindBy(className="inventory_item_name")
    private List<WebElement> productNames;
    @FindBy(className="btn_inventory")
    private List<WebElement> addToCartButtons;
    @FindBy(className="inventory_item_price")
    private List<WebElement> productPrices;

   // public ProductsPage(WebDriver driver) {
    //    super(driver);
    //}

    public boolean isProductsContainerDisplayed() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
        try {
            return wait.until(ExpectedConditions.visibilityOf(products)).isDisplayed();
        } catch (TimeoutException ex) {
            Assert.fail("Timeout waiting for products container to be displayed.");
            return false;
        }
    }

    public void clickAddToCartButton(String productName) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);

        for(int i=0; i<productNames.size(); i++){
            if(productNames.get(i).getText().equals(productName)){
                addToCartButtons.get(i).click();
                System.out.println("Product name in cart: " + productNames.get(i).getText());
                return ;
            }
        }
        Assert.fail("Product with name '" + productName + "' not found.");
    }

    public CartPage clickCartIcon() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
        return new CartPage(/*driver*/);
    }

    public double getProductPrice(String productName) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).getText().equals(productName)) {
                String priceWithSymbol = productPrices.get(i).getText();
                String priceWithoutSymbol = priceWithSymbol.substring(1); // exclude the first character (which is $)
                return Double.parseDouble(priceWithoutSymbol);
            }
        }
        Assert.fail("Product with name '" + productName + "' not found.");
        return 0.0;
    }

}
