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
 * Page Object Model class for Dashboard/Home Page
 * Contains all web elements and actions related to the dashboard after successful login
 * 
 * @author QA Automation Team
 * @version 1.0
 */
public class DashboardPage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Web Elements
    
    @FindBy(xpath = "//h1[contains(text(),'Dashboard') or contains(text(),'Welcome')]")
    private WebElement dashboardHeading;
    
    @FindBy(className = "user-profile")
    private WebElement userProfile;
    
    @FindBy(id = "logout")
    private WebElement logoutButton;
    
    @FindBy(className = "account-summary")
    private WebElement accountSummary;
    
    // Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Wait for dashboard to load
     */
    public void waitForDashboardToLoad() {
        wait.until(ExpectedConditions.or(
            ExpectedConditions.visibilityOf(dashboardHeading),
            ExpectedConditions.urlContains("dashboard"),
            ExpectedConditions.urlContains("home")
        ));
    }
    
    /**
     * Check if dashboard is loaded
     * @return true if dashboard elements are present
     */
    public boolean isDashboardLoaded() {
        try {
            return dashboardHeading.isDisplayed() || 
                   driver.getCurrentUrl().contains("dashboard") ||
                   driver.getCurrentUrl().contains("home");
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("dashboard") ||
                   driver.getCurrentUrl().contains("home") ||
                   !driver.getCurrentUrl().contains("login");
        }
    }
    
    /**
     * Get dashboard heading text
     * @return heading text
     */
    public String getDashboardHeading() {
        wait.until(ExpectedConditions.visibilityOf(dashboardHeading));
        return dashboardHeading.getText();
    }
    
    /**
     * Check if user profile is displayed
     * @return true if user profile is visible
     */
    public boolean isUserProfileDisplayed() {
        try {
            return userProfile.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click logout button
     */
    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
    }
    
    /**
     * Check if account summary is displayed
     * @return true if account summary is visible
     */
    public boolean isAccountSummaryDisplayed() {
        try {
            return accountSummary.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
