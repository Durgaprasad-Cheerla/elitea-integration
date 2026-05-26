package com.edwardjones.automation.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.edwardjones.automation.driver.DriverManager;
import com.edwardjones.automation.config.ConfigurationManager;

/**
 * Base Page - Parent class for all Page Object Models
 * Contains common methods and utilities for interacting with web elements
 * Implements explicit waits, JavaScript execution, and keyboard interactions
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 */
public class BasePage {
    
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;
    protected ConfigurationManager config;
    
    /**
     * Constructor - Initializes driver and utility objects
     */
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.config = ConfigurationManager.getInstance();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWaitSeconds()));
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
    }
    
    /**
     * Waits for element to be visible and returns it
     * 
     * @param locator Element locator
     * @return Visible WebElement
     */
    protected WebElement waitForElementVisible(By locator) {
        logger.debug("Waiting for element to be visible: " + locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Waits for element to be clickable and returns it
     * 
     * @param locator Element locator
     * @return Clickable WebElement
     */
    protected WebElement waitForElementClickable(By locator) {
        logger.debug("Waiting for element to be clickable: " + locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Waits for element to be present in DOM
     * 
     * @param locator Element locator
     * @return WebElement present in DOM
     */
    protected WebElement waitForElementPresent(By locator) {
        logger.debug("Waiting for element to be present: " + locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Waits for all elements to be visible
     * 
     * @param locator Element locator
     * @return List of visible WebElements
     */
    protected List<WebElement> waitForElementsVisible(By locator) {
        logger.debug("Waiting for elements to be visible: " + locator);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    /**
     * Finds element with explicit wait for visibility
     * 
     * @param locator Element locator
     * @return WebElement
     */
    protected WebElement findElement(By locator) {
        return waitForElementVisible(locator);
    }
    
    /**
     * Finds multiple elements with explicit wait
     * 
     * @param locator Element locator
     * @return List of WebElements
     */
    protected List<WebElement> findElements(By locator) {
        return waitForElementsVisible(locator);
    }
    
    /**
     * Clicks element after waiting for it to be clickable
     * 
     * @param locator Element locator
     */
    protected void click(By locator) {
        WebElement element = waitForElementClickable(locator);
        element.click();
        logger.info("Clicked element: " + locator);
    }
    
    /**
     * Types text into input field after clearing it
     * 
     * @param locator Element locator
     * @param text Text to type
     */
    protected void type(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
        logger.info("Typed text into element: " + locator);
    }
    
    /**
     * Types text without clearing field first
     * 
     * @param locator Element locator
     * @param text Text to type
     */
    protected void typeWithoutClear(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.sendKeys(text);
        logger.info("Typed text (without clear) into element: " + locator);
    }
    
    /**
     * Gets text from element
     * 
     * @param locator Element locator
     * @return Element text
     */
    protected String getText(By locator) {
        WebElement element = waitForElementVisible(locator);
        String text = element.getText();
        logger.debug("Retrieved text from element: " + text);
        return text;
    }
    
    /**
     * Gets attribute value from element
     * 
     * @param locator Element locator
     * @param attribute Attribute name
     * @return Attribute value
     */
    protected String getAttribute(By locator, String attribute) {
        WebElement element = waitForElementVisible(locator);
        String value = element.getAttribute(attribute);
        logger.debug("Retrieved attribute '" + attribute + "' from element: " + value);
        return value;
    }
    
    /**
     * Checks if element is displayed
     * 
     * @param locator Element locator
     * @return true if element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            WebElement element = waitForElementVisible(locator);
            boolean isDisplayed = element.isDisplayed();
            logger.debug("Element displayed status: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.debug("Element not displayed: " + locator);
            return false;
        }
    }
    
    /**
     * Checks if element is enabled
     * 
     * @param locator Element locator
     * @return true if element is enabled, false otherwise
     */
    protected boolean isElementEnabled(By locator) {
        try {
            WebElement element = waitForElementPresent(locator);
            boolean isEnabled = element.isEnabled();
            logger.debug("Element enabled status: " + isEnabled);
            return isEnabled;
        } catch (Exception e) {
            logger.debug("Element not found or not enabled: " + locator);
            return false;
        }
    }
    
    /**
     * Checks if element is selected (for checkboxes/radio buttons)
     * 
     * @param locator Element locator
     * @return true if element is selected, false otherwise
     */
    protected boolean isElementSelected(By locator) {
        WebElement element = waitForElementPresent(locator);
        boolean isSelected = element.isSelected();
        logger.debug("Element selected status: " + isSelected);
        return isSelected;
    }
    
    /**
     * Waits for URL to contain specific text
     * 
     * @param urlFragment URL text to wait for
     */
    protected void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
        logger.info("URL contains: " + urlFragment);
    }
    
    /**
     * Waits for URL to be exact match
     * 
     * @param url Expected URL
     */
    protected void waitForUrl(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
        logger.info("URL is: " + url);
    }
    
    /**
     * Gets current page URL
     * 
     * @return Current URL
     */
    protected String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.debug("Current URL: " + url);
        return url;
    }
    
    /**
     * Gets current page title
     * 
     * @return Page title
     */
    protected String getPageTitle() {
        String title = driver.getTitle();
        logger.debug("Page title: " + title);
        return title;
    }
    
    /**
     * Sends keyboard key to element
     * 
     * @param locator Element locator
     * @param key Keyboard key
     */
    protected void sendKey(By locator, Keys key) {
        WebElement element = waitForElementVisible(locator);
        element.sendKeys(key);
        logger.info("Sent key '" + key + "' to element: " + locator);
    }
    
    /**
     * Sends keyboard key using Actions class
     * 
     * @param key Keyboard key
     */
    protected void pressKey(Keys key) {
        actions.sendKeys(key).perform();
        logger.info("Pressed key: " + key);
    }
    
    /**
     * Presses Tab key to move focus
     */
    protected void pressTabKey() {
        actions.sendKeys(Keys.TAB).perform();
        logger.info("Pressed TAB key");
    }
    
    /**
     * Presses Enter key
     */
    protected void pressEnterKey() {
        actions.sendKeys(Keys.ENTER).perform();
        logger.info("Pressed ENTER key");
    }
    
    /**
     * Scrolls element into view using JavaScript
     * 
     * @param locator Element locator
     */
    protected void scrollToElement(By locator) {
        WebElement element = waitForElementPresent(locator);
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled to element: " + locator);
    }
    
    /**
     * Clicks element using JavaScript
     * 
     * @param locator Element locator
     */
    protected void jsClick(By locator) {
        WebElement element = waitForElementPresent(locator);
        jsExecutor.executeScript("arguments[0].click();", element);
        logger.info("JavaScript clicked element: " + locator);
    }
    
    /**
     * Executes JavaScript code
     * 
     * @param script JavaScript code
     * @return Script execution result
     */
    protected Object executeJavaScript(String script) {
        return jsExecutor.executeScript(script);
    }
    
    /**
     * Executes JavaScript code with arguments
     * 
     * @param script JavaScript code
     * @param args Script arguments
     * @return Script execution result
     */
    protected Object executeJavaScript(String script, Object... args) {
        return jsExecutor.executeScript(script, args);
    }
    
    /**
     * Waits for page to load completely
     */
    protected void waitForPageLoad() {
        wait.until(webDriver -> 
            jsExecutor.executeScript("return document.readyState").equals("complete"));
        logger.info("Page loaded completely");
    }
    
    /**
     * Refreshes current page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
        logger.info("Page refreshed");
    }
    
    /**
     * Navigates to URL
     * 
     * @param url Target URL
     */
    protected void navigateToUrl(String url) {
        driver.get(url);
        logger.info("Navigated to URL: " + url);
    }
}
