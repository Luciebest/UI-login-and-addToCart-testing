package com.cegeka.academy.qa.tests.steps;

import com.cegeka.academy.qa.configurations.FrameworkConfiguration;
import com.cegeka.academy.qa.pageobjects.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = FrameworkConfiguration.class)
public class BaseSteps {

    @Autowired
    protected LoginPage loginPage;

    @Autowired
    protected ProductsPage productsPage;

    @Autowired
    protected CartPage cartPage;

    @Autowired
    protected CheckoutFinishPage checkoutFinishPage;

    @Autowired
    protected CheckoutInfoPage checkoutInfoPage;

    @Autowired
    protected CheckoutOverviewPage checkoutOverviewPage;

}
