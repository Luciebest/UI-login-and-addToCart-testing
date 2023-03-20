package com.cegeka.academy.qa.tests.steps;

import com.cegeka.academy.qa.pageobjects.*;
import com.cegeka.academy.qa.tests.webdrivers.WebDriverSelector;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class SauceLabSteps {

    public WebDriver driver = WebDriverSelector.getInstance();
    private LoginPage loginPage = new LoginPage(driver);
    private ProductsPage productsPage;

    @Given("Login page is opened")
    public void loginPageIsOpened() {
        driver.get("https://www.saucedemo.com/");
    }

    @When("Username and password {string} and {string} are entered")
    public void usernameAndPasswordAndAreEntered(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @Then("Products page is displayed")
    public void productsPageIsDisplayed() {
        Assert.assertTrue("Products are not displayed", productsPage.isProductsContainerDisplayed());
    }

    @And("Login is attempted")
    public void loginIsAttempted() {
        productsPage = loginPage.clickLoginButton();
    }


}
