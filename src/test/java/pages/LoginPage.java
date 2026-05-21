package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model class for Edward Jones Login Page
 * This class contains all web elements and actions related to the login page
 * 
 * @author QA Automation Team
 * @version 1.0
 */
public class LoginPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Page URL
    private static final String LOGIN_URL = "https://www.edwardjones.com/login"; // Update with actual URL
    
    // Web Elements using @FindBy annotations for Page Factory
    
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(id = "email")
    private WebElement emailField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(xpath = "//button[contains(text(),'Login') or contains(text(),'Sign In')]")
    private WebElement loginButton;
    
    @FindBy(xpath = "//img[contains(@alt,'Edward Jones') or contains(@class,'logo')]")
    private WebElement edwardJonesLogo;
    
    @FindBy(className = "error-message")
    private WebElement errorMessage;
    
    @FindBy(className = "success-message")
    private WebElement successMessage;
    
    @FindBy(className = "loading-spinner")
    private WebElement loadingSpinner;
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Navigate to the Edward Jones login page
     */
    public void navigateToLoginPage() {
        driver.get(LOGIN_URL);
        waitForPageLoad();
    }
    
    /**
     * Wait for the page to fully load
     */
    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOf(loginButton));
    }
    
    /**
     * Check if username field is visible
     * @return true if username field is displayed
     */
    public boolean isUsernameFieldVisible() {
        try {
            return usernameField.isDisplayed() || emailField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get username field placeholder text
     * @return placeholder text of username field
     */
    public String getUsernameFieldPlaceholder() {
        try {
            return usernameField.getAttribute("placeholder");
        } catch (Exception e) {
            return emailField.getAttribute("placeholder");
        }
    }
    
    /**
     * Get username field type attribute
     * @return type attribute value
     */
    public String getUsernameFieldType() {
        try {
            return usernameField.getAttribute("type");
        } catch (Exception e) {
            return emailField.getAttribute("type");
        }
    }
    
    /**
     * Check if username field is required
     * @return true if required attribute is present
     */
    public boolean isUsernameFieldRequired() {
        try {
            String required = usernameField.getAttribute("required");
            return required != null;
        } catch (Exception e) {
            String required = emailField.getAttribute("required");
            return required != null;
        }
    }
    
    /**
     * Check if password field is visible
     * @return true if password field is displayed
     */
    public boolean isPasswordFieldVisible() {
        return passwordField.isDisplayed();
    }
    
    /**
     * Get password field placeholder text
     * @return placeholder text of password field
     */
    public String getPasswordFieldPlaceholder() {
        return passwordField.getAttribute("placeholder");
    }
    
    /**
     * Get password field type attribute
     * @return type attribute value
     */
    public String getPasswordFieldType() {
        return passwordField.getAttribute("type");
    }
    
    /**
     * Check if password field is required
     * @return true if required attribute is present
     */
    public boolean isPasswordFieldRequired() {
        String required = passwordField.getAttribute("required");
        return required != null;
    }
    
    /**
     * Enter username or email
     * @param username username or email to enter
     */
    public void enterUsername(String username) {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            usernameField.clear();
            usernameField.sendKeys(username);
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOf(emailField));
            emailField.clear();
            emailField.sendKeys(username);
        }
    }
    
    /**
     * Enter password
     * @param password password to enter
     */
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    
    /**
     * Get the value attribute of password field to verify masking
     * @return password field value
     */
    public String getPasswordFieldValue() {
        return passwordField.getAttribute("value");
    }
    
    /**
     * Check if login button is visible
     * @return true if login button is displayed
     */
    public boolean isLoginButtonVisible() {
        return loginButton.isDisplayed();
    }
    
    /**
     * Check if login button is enabled
     * @return true if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }
    
    /**
     * Get login button text
     * @return text of login button
     */
    public String getLoginButtonText() {
        return loginButton.getText();
    }
    
    /**
     * Click login button
     */
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }
    
    /**
     * Check if Edward Jones logo is visible
     * @return true if logo is displayed
     */
    public boolean isLogoVisible() {
        try {
            return edwardJonesLogo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if error message is displayed
     * @return true if error message is visible
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get error message text
     * @return error message text
     */
    public String getErrorMessageText() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }
    
    /**
     * Check if loading spinner is displayed
     * @return true if loading spinner is visible
     */
    public boolean isLoadingSpinnerDisplayed() {
        try {
            return loadingSpinner.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Wait for loading spinner to disappear
     */
    public void waitForLoadingToComplete() {
        try {
            wait.until(ExpectedConditions.invisibilityOf(loadingSpinner));
        } catch (Exception e) {
            // Loading spinner not present, continue
        }
    }
    
    /**
     * Get the background color of login button for contrast checking
     * @return CSS color value
     */
    public String getLoginButtonBackgroundColor() {
        return loginButton.getCssValue("background-color");
    }
    
    /**
     * Get the text color of login button for contrast checking
     * @return CSS color value
     */
    public String getLoginButtonTextColor() {
        return loginButton.getCssValue("color");
    }
    
    /**
     * Get page title
     * @return page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL
     * @return current page URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Perform complete login operation
     * @param username username or email
     * @param password password
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        waitForLoadingToComplete();
    }
    
    /**
     * Check if user is on login page
     * @return true if current URL contains login path
     */
    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("login");
    }
}
