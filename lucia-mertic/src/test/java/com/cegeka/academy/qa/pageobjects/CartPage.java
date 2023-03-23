package com.cegeka.academy.qa.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartPage extends BasePage{

    @FindBy(css = "[data-test='checkout']")
    private WebElement checkoutButton;

    @FindBy(css = ".cart_list")
    private WebElement cartContainer;

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private List<WebElement> productList;

//    public CartPage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//    }

    public CheckoutInfoPage clickCheckoutButton() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();

        return new CheckoutInfoPage(/*driver*/);
    }
    public Boolean isProductDisplayedInCart(String productName) {
        List<String> productNames = new ArrayList<>();
        for (WebElement product : productList) {
            productNames.add(product.getText());
            if (product.getText().equals(productName))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isCartContainerDisplayed() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
        try {
            return wait.until(ExpectedConditions.visibilityOf(cartContainer)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}