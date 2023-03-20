package com.cegeka.academy.qa.tests.webdrivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverSelector {

    private static WebDriver instance;

    public static WebDriver getInstance() {
        String browserType = System.getProperty("browser", "chrome");
        return switch (browserType) {
            case "chrome" -> getChromeDriver();
            default -> throw new RuntimeException("BrowserType is not supported" + browserType);
        };
    }

    private static WebDriver getChromeDriver() {
//        TODO Modifica pentru path-ul de pe sistemul tau
        System.setProperty("webdriver.chrome.driver", "/Users/User/chromedrivers/chromedriver111");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        if (instance == null || ((ChromeDriver) instance).getSessionId() == null) {
            instance = new ChromeDriver(chromeOptions);
        }
        return instance;
    }
}
