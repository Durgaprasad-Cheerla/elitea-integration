package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object Model for ParaBank Home Page
 * Contains elements and methods related to home page functionality
 * 
 * JIRA: SCRUM-166, SCRUM-167
 * 
 * @author Automation Team
 * @version 1.0
 */
public class HomePage extends BasePage {
    
    // ==================== Page Elements ====================
    
    @FindBy(linkText = "Register")
    private WebElement registerLink;
    
    @FindBy(name = "username")
    private WebElement usernameInput;
    
    @FindBy(name = "password")
    private WebElement passwordInput;
    
    @FindBy(xpath = "//input[@value='Log In']")
    private WebElement loginButton;
    
    @FindBy(css = ".logo")
    private WebElement parabankLogo;
    
    @FindBy(xpath = "//p[@class='smallText']")
    private WebElement welcomeMessage;
    
    // ==================== Constructor ====================
    
    /**
     * Constructor to initialize HomePage
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    // ==================== Page Actions ====================
    
    /**
     * Click on the Register link to navigate to registration page
     * Covers AC1: User can access the registration page from the homepage
     */
    public void clickRegisterLink() {
        logger.info("Clicking on Register link");
        wait.until(ExpectedConditions.elementToBeClickable(registerLink));
        registerLink.click();
        logger.info("Successfully clicked on Register link");
    }
    
    /**
     * Verify if Register link is visible on the home page
     * @return true if Register link is displayed, false otherwise
     */
    public boolean isRegisterLinkVisible() {
        boolean isVisible = registerLink.isDisplayed();
        logger.debug("Register link visibility: {}", isVisible);
        return isVisible;
    }
    
    /**
     * Perform login with username and password
     * @param username User's username
     * @param password User's password
     */
    public void login(String username, String password) {
        logger.info("Attempting to login with username: {}", username);
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
        logger.info("Login attempt completed");
    }
    
    /**
     * Verify if ParaBank logo is displayed on home page
     * @return true if logo is displayed, false otherwise
     */
    public boolean isLogoDisplayed() {
        boolean isDisplayed = parabankLogo.isDisplayed();
        logger.debug("ParaBank logo displayed: {}", isDisplayed);
        return isDisplayed;
    }
    
    /**
     * Get the welcome message text from home page
     * @return String welcome message text
     */
    public String getWelcomeMessage() {
        wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
        String message = welcomeMessage.getText();
        logger.debug("Welcome message: {}", message);
        return message;
    }
}
