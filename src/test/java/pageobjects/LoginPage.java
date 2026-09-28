package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model class for Edward Jones Online Access Login Page
 * URL: https://onlineaccess.edwardjones.com/app/oa-login
 * 
 * This class encapsulates all web elements and interactions on the login page
 * following the Page Object Model design pattern.
 */
public class LoginPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators - Using data-testid as Priority 1, ID as fallback
    
    @FindBy(css = "[data-testid='login-user-input']")
    private WebElement userIdField;
    
    // Alternative locator if data-testid is not available
    @FindBy(id = "login-user")
    private WebElement userIdFieldById;
    
    @FindBy(xpath = "//label[text()='Password']/following-sibling::div//input[@type='password' or @type='text']")
    private WebElement passwordField;
    
    @FindBy(xpath = "//button[contains(text(),'Log In') or contains(text(),'Sign in')]")
    private WebElement signInButton;
    
    @FindBy(id = "credentials-form-header")
    private WebElement pageHeader;
    
    // Error message locators - Multiple strategies for robust identification
    @FindBy(xpath = "//div[contains(@class,'error') or contains(@class,'alert') or contains(@class,'message')]")
    private WebElement errorMessageContainer;
    
    @FindBy(xpath = "//span[contains(@class,'mat-error') or contains(@class,'error-message')]")
    private WebElement errorMessageText;
    
    // Generic error message locator for any visible error
    private By genericErrorMessageLocator = By.xpath(
        "//div[contains(@class,'mat-error') or contains(@class,'error') or " +
        "contains(@class,'alert-danger') or contains(@class,'invalid-feedback')] |" +
        "//span[contains(@class,'mat-error')] | " +
        "//mat-error | " +
        "//div[@role='alert']"
    );
    
    @FindBy(xpath = "//a[contains(text(),'Find your user ID')]")
    private WebElement findUserIdLink;
    
    @FindBy(xpath = "//a[contains(text(),'Reset your password')]")
    private WebElement resetPasswordLink;
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Navigate to the login page
     * @param url - Login page URL
     */
    public void navigateToLoginPage(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOf(pageHeader));
    }
    
    /**
     * Enter User ID in the User ID field
     * @param userId - User ID to enter
     */
    public void enterUserId(String userId) {
        wait.until(ExpectedConditions.elementToBeClickable(userIdField));
        userIdField.clear();
        userIdField.sendKeys(userId);
    }
    
    /**
     * Enter password in the Password field
     * @param password - Password to enter
     */
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    
    /**
     * Click the Sign In button
     */
    public void clickSignInButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
    }
    
    /**
     * Get the page header text
     * @return String - Header text
     */
    public String getPageHeaderText() {
        wait.until(ExpectedConditions.visibilityOf(pageHeader));
        return pageHeader.getText();
    }
    
    /**
     * Check if error message is displayed
     * @return boolean - true if error message is visible
     */
    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(genericErrorMessageLocator));
            return driver.findElements(genericErrorMessageLocator).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get error message text
     * @return String - Error message content
     */
    public String getErrorMessageText() {
        try {
            WebElement errorElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(genericErrorMessageLocator)
            );
            return errorElement.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Verify if error message contains specific text
     * @param text - Text to search for in error message
     * @return boolean - true if error message contains the text
     */
    public boolean doesErrorMessageContain(String text) {
        String errorMessage = getErrorMessageText();
        return errorMessage.toLowerCase().contains(text.toLowerCase());
    }
    
    /**
     * Check if User ID field is accessible (enabled and displayed)
     * @return boolean - true if field is accessible
     */
    public boolean isUserIdFieldAccessible() {
        try {
            return userIdField.isDisplayed() && userIdField.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if Password field is accessible (enabled and displayed)
     * @return boolean - true if field is accessible
     */
    public boolean isPasswordFieldAccessible() {
        try {
            return passwordField.isDisplayed() && passwordField.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get current page URL
     * @return String - Current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Verify user is still on login page
     * @param expectedUrl - Expected login page URL
     * @return boolean - true if on login page
     */
    public boolean isOnLoginPage(String expectedUrl) {
        String currentUrl = getCurrentUrl();
        return currentUrl.equals(expectedUrl) || currentUrl.contains("oa-login");
    }
    
    /**
     * Check if page is loaded successfully
     * @return boolean - true if page is loaded
     */
    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageHeader));
            wait.until(ExpectedConditions.visibilityOf(userIdField));
            wait.until(ExpectedConditions.visibilityOf(passwordField));
            wait.until(ExpectedConditions.elementToBeClickable(signInButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
