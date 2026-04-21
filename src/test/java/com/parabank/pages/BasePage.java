package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/**
 * Base Page Object class containing common methods and utilities
 * All page objects inherit from this class
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    
    // Timeout constants
    protected static final int EXPLICIT_WAIT_TIMEOUT = 20;
    protected static final int IMPLICIT_WAIT_TIMEOUT = 10;
    
    /**
     * Constructor to initialize WebDriver and WebDriverWait
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT));
        PageFactory.initElements(driver, this);
        logger.info("Initialized page: {}", this.getClass().getSimpleName());
    }
    
    /**
     * Get the current page title
     * @return String page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.debug("Current page title: {}", title);
        return title;
    }
    
    /**
     * Get the current page URL
     * @return String current URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.debug("Current URL: {}", url);
        return url;
    }
    
    /**
     * Refresh the current page
     */
    public void refreshPage() {
        logger.info("Refreshing the current page");
        driver.navigate().refresh();
    }
    
    /**
     * Navigate back to previous page
     */
    public void navigateBack() {
        logger.info("Navigating back to previous page");
        driver.navigate().back();
    }
}
