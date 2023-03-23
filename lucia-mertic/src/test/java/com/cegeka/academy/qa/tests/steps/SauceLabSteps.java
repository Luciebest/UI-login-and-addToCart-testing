package com.cegeka.academy.qa.tests.steps;

import com.cegeka.academy.qa.pageobjects.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class SauceLabSteps extends BaseSteps{

    @Autowired
    private OrderItem orderItem;

    @Given("Login page is opened")
    public void loginPageIsOpened()
    {
        loginPage.goToStartPage();
    }

    @When("Username and password {string} and {string} are entered")
    public void usernameAndPasswordAndAreEntered(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }
    @And("Login is attempted")
    public void loginIsAttempted() {
        loginPage.clickLoginButton();
    }

    @Then("Products page is displayed")
    public void productsPageIsDisplayed() {
        Assert.assertTrue("Products are not displayed", productsPage.isProductsContainerDisplayed());
    }

    @When("User adds to cart product {string}")
    public void userAddsToCart(String productName) {
        //already existing code that you wrote at step 2 to add product to cart
        //...
        productsPage.clickAddToCartButton(productName);

        //TODO
        //set the productName on the orderItem that you will use in another step to make assertions
        orderItem.setName(productName);

        //TODO
        //get  productPrice using selenium and use Double.parseDouble("price of type string from selenium") to convert to double
        //you will need to follow the same pattern with page classes. There should not be driver in Steps classes.

        double productPrice = productsPage.getProductPrice(productName);

        //set price and quantity
        orderItem.setPrice(productPrice);
        orderItem.setQuantity(1);
    }

    @Then("The product with name {string} is displayed in the cart")
    public void productIsDisplayedInCart(String productName) {
        //cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isProductDisplayedInCart(productName));
    }

    @When("User clicks on the cart icon")
    public void userClicksOnCartIcon() {
        productsPage.clickCartIcon();

    }

    @Then("Cart page is displayed")
    public void cartPageIsDisplayed() {
        Assert.assertTrue(cartPage.isCartContainerDisplayed());
    }

    @When("User clicks on the checkout button")
    public void userClicksOnCheckoutButton() {
                cartPage.clickCheckoutButton();
    }

    @Then("Checkout Info page is displayed")
    public void checkoutInfoPageIsDisplayed() {
        Assert.assertTrue(checkoutInfoPage.isCheckoutInfoContainerDisplayed());
    }
    @When("User enters checkout info {string} {string} {string}")
    public void userEntersCheckoutInfo(String checkoutFirstName, String checkoutLastName, String postalCode) {
        checkoutInfoPage.enterCheckoutInfo(checkoutFirstName, checkoutLastName, postalCode);
    }

    @And("User continues on Checkout Info page")
    public void userContinuesOnCheckoutInfoPage() {
        checkoutInfoPage.clickContinueButton();
    }

    @Then("Checkout Overview is displayed")
    public void checkoutOverviewIsDisplayed() {
        Assert.assertTrue(checkoutOverviewPage.isCheckoutOverviewContainerDisplayed());
    }


    @And("Users checks product is found on the Checkout Overview page")
    public void usersChecksProductIsFoundOnTheCheckoutOverviewPage() {
        String expectedProductName = orderItem.getName();
        double expectedProductPrice = orderItem.getPrice();
        int expectedProductQuantity = orderItem.getQuantity();
        //TODO add assertions
        //...
        // Check that the product is displayed on the checkout overview page
        Assert.assertTrue("Product is not displayed on checkout overview page", checkoutOverviewPage.isProductDisplayed(expectedProductName));

        // Check the product details on the checkout overview page
        double actualProductPrice = checkoutOverviewPage.getProductPrice(expectedProductName);
        Assert.assertEquals("Product price is incorrect on checkout overview page",expectedProductPrice, actualProductPrice,0);

        int actualProductQuantity = checkoutOverviewPage.getProductQuantity(expectedProductName);
        Assert.assertEquals("Product quantity is incorrect on checkout overview page",expectedProductQuantity, actualProductQuantity);

    }

    @When("User finishes on Checkout Overview page")
    public void userFinishesOnCheckoutOverviewPage() {
                checkoutOverviewPage.clickFinishButton();
    }

    @Then("Checkout Complete page is displayed")
    public void checkoutCompletePageIsDisplayed() {
        Assert.assertTrue(checkoutFinishPage.isCheckoutCompleteContainerDisplayed());
    }

    @And("\"Thank you for your order!\" is displayed on the page")
    public void thankYouForYourOrderIsDisplayed() {
        Assert.assertTrue(checkoutFinishPage.isThankYouMessageDisplayed());
    }

    @When("User goes back to Products page")
    public void userGoesBackToProductsPage() {
                checkoutFinishPage.clickBackHomeButton();
    }


}
