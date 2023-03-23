package com.cegeka.academy.qa.configurations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PreDestroy;

@Configuration
@ComponentScan(basePackages = "com.cegeka.academy.qa")
@PropertySource("classpath:framework.properties")

public class FrameworkConfiguration {

    private WebDriver driver;

    @Bean
    WebDriver getDriver(){
        driver = getChrome();
        return driver;
    }

    private ChromeDriver getChrome()
    {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lucia\\developer\\chromedrivers\\chromedriver111\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(chromeOptions);
    }

    @PreDestroy
    public void destroy()
    {
        if (driver != null)  {
            driver.quit();
            System.out.println("Driver quit was called");
        }
        else System.out.println("Cannot quit driver because it's already null");
    }

}
