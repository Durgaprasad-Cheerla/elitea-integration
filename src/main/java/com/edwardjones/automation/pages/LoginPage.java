package com.edwardjones.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Login Page Object Model
 * Contains all locators and methods for Edward Jones Login Page
 * Follows Page Object Model design pattern for maintainability
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 * 
 * Test Case Coverage: SCRUM-184 (TC-SCRUM-183-001)
 * Covers: User Authentication, Field Validation, Security, Performance, Accessibility
 */
public class LoginPage extends BasePage {
    
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    
    // Page Locators - Using multiple locator strategies for robustness
    private final By usernameField = By.id("username");
    private final By usernameFieldAlt = By.cssSelector("input[name='username']");
    private final By usernameFieldXPath = By.xpath("//input[@id='username' or @name='username']");
    
    private final By passwordField = By.id("password");
    private final By passwordFieldAlt = By.cssSelector("input[name='password']");
    private final By passwordFieldXPath = By.xpath("//input[@id='password' or @name='password' or @type='password']");
    
    private final By loginButton = By.id("login-button");
    private final By loginButtonAlt = By.cssSelector("button[type='submit']");
    private final By loginButtonXPath = By.xpath("//button[contains(text(),'Login') or contains(text(),'Sign In')]");
    
    private final By loginForm = By.id("login-form");
    private final By loginFormAlt = By.cssSelector("form.login-form");
    
    private final By loadingIndicator = By.cssSelector(".loading-spinner, .loader");
    private final By errorMessage = By.cssSelector(".error-message, .alert-danger");
    
    private final By forgotPasswordLink = By.linkText("Forgot Password?");
    private final By signUpLink = By.linkText("Sign Up");
    
    /**
     * Constructor
     */
    public LoginPage() {
        super();
        logger.info("LoginPage object initialized");
    }
    
    /**
     * Navigates to Login Page
     */
    public void navigateToLoginPage() {
        String loginUrl = config.getLoginUrl();
        navigateToUrl(loginUrl);
        waitForPageLoad();
        logger.info("Navigated to Login Page: " + loginUrl);
    }
    
    /**
     * Verifies login page has loaded successfully
     * 
     * @return true if page loaded, false otherwise
     */
    public boolean isLoginPageLoaded() {
        try {
            boolean isUsernameVisible = isElementDisplayed(usernameField) || 
                                       isElementDisplayed(usernameFieldAlt);
            boolean isPasswordVisible = isElementDisplayed(passwordField) || 
                                       isElementDisplayed(passwordFieldAlt);
            boolean isLoginButtonVisible = isElementDisplayed(loginButton) || 
                                          isElementDisplayed(loginButtonAlt);
            
            boolean pageLoaded = isUsernameVisible && isPasswordVisible && isLoginButtonVisible;
            logger.info("Login page loaded status: " + pageLoaded);
            return pageLoaded;
        } catch (Exception e) {
            logger.error("Error checking if login page loaded: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verifies URL uses HTTPS protocol
     * 
     * @return true if HTTPS, false otherwise
     */
    public boolean isHttpsProtocol() {
        String currentUrl = getCurrentUrl();
        boolean isHttps = currentUrl.startsWith("https://");
        logger.info("HTTPS protocol verification: " + isHttps + " (URL: " + currentUrl + ")");
        return isHttps;
    }
    
    /**
     * Checks if Username field is visible
     * 
     * @return true if visible, false otherwise
     */
    public boolean isUsernameFieldVisible() {
        boolean isVisible = isElementDisplayed(usernameField) || 
                           isElementDisplayed(usernameFieldAlt);
        logger.info("Username field visible: " + isVisible);
        return isVisible;
    }
    
    /**
     * Checks if Password field is visible
     * 
     * @return true if visible, false otherwise
     */
    public boolean isPasswordFieldVisible() {
        boolean isVisible = isElementDisplayed(passwordField) || 
                           isElementDisplayed(passwordFieldAlt);
        logger.info("Password field visible: " + isVisible);
        return isVisible;
    }
    
    /**
     * Checks if Login button is visible
     * 
     * @return true if visible, false otherwise
     */
    public boolean isLoginButtonVisible() {
        boolean isVisible = isElementDisplayed(loginButton) || 
                           isElementDisplayed(loginButtonAlt);
        logger.info("Login button visible: " + isVisible);
        return isVisible;
    }
    
    /**
     * Checks if Login button is enabled/disabled
     * 
     * @return true if enabled, false if disabled
     */
    public boolean isLoginButtonEnabled() {
        try {
            boolean isEnabled = isElementEnabled(loginButton) || 
                               isElementEnabled(loginButtonAlt);
            logger.info("Login button enabled status: " + isEnabled);
            return isEnabled;
        } catch (Exception e) {
            logger.warn("Could not determine login button state: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Clicks on Username field to focus it
     */
    public void clickUsernameField() {
        try {
            click(usernameField);
        } catch (Exception e) {
            logger.warn("Primary locator failed, trying alternate");
            click(usernameFieldAlt);
        }
        logger.info("Clicked on Username field");
    }
    
    /**
     * Clicks on Password field to focus it
     */
    public void clickPasswordField() {
        try {
            click(passwordField);
        } catch (Exception e) {
            logger.warn("Primary locator failed, trying alternate");
            click(passwordFieldAlt);
        }
        logger.info("Clicked on Password field");
    }
    
    /**
     * Enters username into username field
     * 
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        try {
            type(usernameField, username);
        } catch (Exception e) {
            logger.warn("Primary locator failed, trying alternate");
            type(usernameFieldAlt, username);
        }
        logger.info("Entered username: " + username);
    }
    
    /**
     * Enters password into password field
     * 
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        try {
            type(passwordField, password);
        } catch (Exception e) {
            logger.warn("Primary locator failed, trying alternate");
            type(passwordFieldAlt, password);
        }
        logger.info("Entered password: [MASKED]");
    }
    
    /**
     * Gets the type attribute of password field to verify masking
     * 
     * @return Input type (should be "password")
     */
    public String getPasswordFieldType() {
        try {
            String type = getAttribute(passwordField, "type");
            logger.info("Password field type: " + type);
            return type;
        } catch (Exception e) {
            logger.warn("Primary locator failed, trying alternate");
            String type = getAttribute(passwordFieldAlt, "type");
            logger.info("Password field type: " + type);
            return type;
        }
    }
    
    /**
     * Verifies password is masked (type="password")
     * 
     * @return true if masked, false otherwise
     */
    public boolean isPasswordMasked() {
        String type = getPasswordFieldType();
        boolean isMasked = "password".equalsIgnoreCase(type);
        logger.info("Password masked verification: " + isMasked);
        return isMasked;
    }
    
    /**
     * Gets value attribute from password field (should be empty or masked)
     * 
     * @return Password field value
     */
    public String getPasswordFieldValue() {
        try {
            String value = getAttribute(passwordField, "value");
            logger.debug("Password field value retrieved");
            return value;
        } catch (Exception e) {
            logger.warn("Primary locator failed, trying alternate");
            return getAttribute(passwordFieldAlt, "value");
        }
    }
    
    /**
     * Presses Tab key from current focused element
     */
    public void pressTabKey() {
        super.pressTabKey();
    }
    
    /**
     * Presses Enter key to submit form
     */
    public void pressEnterKey() {
        super.pressEnterKey();
    }
    
    /**
     * Sends Enter key to username field
     */
    public void pressEnterOnUsernameField() {
        try {
            sendKey(usernameField, Keys.ENTER);
        } catch (Exception e) {
            sendKey(usernameFieldAlt, Keys.ENTER);
        }
        logger.info("Pressed ENTER on username field");
    }
    
    /**
     * Sends Enter key to password field
     */
    public void pressEnterOnPasswordField() {
        try {
            sendKey(passwordField, Keys.ENTER);
        } catch (Exception e) {
            sendKey(passwordFieldAlt, Keys.ENTER);
        }
        logger.info("Pressed ENTER on password field");
    }
    
    /**
     * Clicks Login button
     */
    public void clickLoginButton() {
        try {
            click(loginButton);
        } catch (Exception e) {
            logger.warn("Primary locator failed, trying alternate");
            click(loginButtonAlt);
        }
        logger.info("Clicked Login button");
    }
    
    /**
     * Checks if loading indicator is displayed after login submission
     * 
     * @return true if loading indicator visible, false otherwise
     */
    public boolean isLoadingIndicatorDisplayed() {
        try {
            boolean isDisplayed = isElementDisplayed(loadingIndicator);
            logger.info("Loading indicator displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.debug("Loading indicator not found (may not be present)");
            return false;
        }
    }
    
    /**
     * Performs complete login with username and password
     * 
     * @param username Username
     * @param password Password
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        logger.info("Performed login for user: " + username);
    }
    
    /**
     * Performs login using keyboard only (Tab and Enter)
     * 
     * @param username Username
     * @param password Password
     */
    public void loginUsingKeyboard(String username, String password) {
        clickUsernameField();
        enterUsername(username);
        pressTabKey();
        enterPassword(password);
        pressEnterKey();
        logger.info("Performed keyboard-only login for user: " + username);
    }
    
    /**
     * Clears username field
     */
    public void clearUsernameField() {
        try {
            WebDriver driver = DriverManager.getDriver();
            driver.findElement(usernameField).clear();
        } catch (Exception e) {
            driver.findElement(usernameFieldAlt).clear();
        }
        logger.info("Cleared username field");
    }
    
    /**
     * Clears password field
     */
    public void clearPasswordField() {
        try {
            driver.findElement(passwordField).clear();
        } catch (Exception e) {
            driver.findElement(passwordFieldAlt).clear();
        }
        logger.info("Cleared password field");
    }
    
    /**
     * Clears both username and password fields
     */
    public void clearLoginFields() {
        clearUsernameField();
        clearPasswordField();
        logger.info("Cleared all login fields");
    }
    
    /**
     * Checks if error message is displayed
     * 
     * @return true if error displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        try {
            boolean isDisplayed = isElementDisplayed(errorMessage);
            logger.info("Error message displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.debug("No error message found");
            return false;
        }
    }
    
    /**
     * Gets error message text
     * 
     * @return Error message text
     */
    public String getErrorMessageText() {
        String message = getText(errorMessage);
        logger.info("Error message: " + message);
        return message;
    }
}
