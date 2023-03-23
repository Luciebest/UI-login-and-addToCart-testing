package com.cegeka.academy.qa.pageobjects;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ser.Serializers;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CheckoutInfoPage extends BasePage {

    @FindBy(css = "[data-test=firstName]")
    private WebElement firstNameInput;
    @FindBy(css = "[data-test=lastName]")
    private WebElement lastNameInput;
    @FindBy(css = "[data-test=postalCode]")
    private WebElement postalCodeInput;
    @FindBy(css = "[data-test=continue]")
    private WebElement continueButton;
    @FindBy(id="checkout_info_container")
    private WebElement checkoutInfoContainer;

//    public CheckoutInfoPage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//    }

    public void enterFirstName(String firstName) {
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        postalCodeInput.sendKeys(postalCode);
    }

    public CheckoutOverviewPage clickContinueButton() {
        try {
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(250))
                    .ignoring(WebDriverException.class);
            wait.until((ExpectedConditions.elementToBeClickable(continueButton))).click();
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for continue button to be clickable.");
        }
        return new CheckoutOverviewPage(/*driver*/);
    }
    public boolean isCheckoutInfoContainerDisplayed() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(8000))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(WebDriverException.class);
        try {
            return wait.until(ExpectedConditions.visibilityOf(checkoutInfoContainer)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void  enterCheckoutInfo (String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }
}
