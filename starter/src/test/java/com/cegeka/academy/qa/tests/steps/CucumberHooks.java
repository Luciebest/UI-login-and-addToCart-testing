package com.cegeka.academy.qa.tests.steps;

import com.cegeka.academy.qa.tests.webdrivers.WebDriverSelector;
import io.cucumber.java.AfterAll;

import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class CucumberHooks {

    private WebDriver driver = WebDriverSelector.getInstance();

    @Before
    public void setupBeforeScenario() {
        System.out.println("Perform before scenario cucumber hook");
        System.out.println("Driver instance " +  driver);
    }

    @AfterAll
    public static void finishAll() {
        System.out.println("Perform finish after all cucumber hook");
        WebDriverSelector.getInstance().quit();
    }
}
