package com.cegeka.academy.qa.pageobjects;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CheckoutFinishPage extends BasePage {


    @FindBy(xpath = "//span[@class='title' and text()='Checkout: Complete!']")
    private WebElement checkoutCompleteTitle;

    @FindBy(css = "h2.complete-header")
    private WebElement thankYouMessage;

    @FindBy(css = "[data-test=back-to-products]")
    private WebElement backButton;

//    public CheckoutFinishPage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//    }

    public boolean isCheckoutCompleteContainerDisplayed() {
        return checkoutCompleteTitle.isDisplayed();
    }
    public boolean isThankYouMessageDisplayed() {
        return thankYouMessage.isDisplayed();
    }
    public ProductsPage clickBackHomeButton() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(TimeoutException.class);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(backButton)).click();
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for back button to be clickable.");
        }
        return new ProductsPage(/*driver*/);
    }
}



