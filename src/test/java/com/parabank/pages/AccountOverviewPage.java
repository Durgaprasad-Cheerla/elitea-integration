package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object Model for ParaBank Account Overview Page
 * Contains elements and methods related to account overview functionality
 * 
 * JIRA: SCRUM-166, SCRUM-167
 * Test Case: TC001 - Verify Successful User Registration with Valid Data
 * 
 * Covers:
 * - AC6: User is redirected to account overview page with welcome message
 * - AC7: System creates a default checking account for new user
 * 
 * @author Automation Team
 * @version 1.0
 */
public class AccountOverviewPage extends BasePage {
    
    // ==================== Page Elements ====================
    
    @FindBy(xpath = "//h1[@class='title']")
    private WebElement pageTitle;
    
    @FindBy(xpath = "//div[@id='rightPanel']//p")
    private WebElement successMessage;
    
    @FindBy(xpath = "//p[@class='smallText']")
    private WebElement welcomeUserMessage;
    
    @FindBy(xpath = "//a[contains(text(),'Log Out')]")
    private WebElement logoutLink;
    
    @FindBy(xpath = "//table[@id='accountTable']")
    private WebElement accountTable;
    
    @FindBy(xpath = "//table[@id='accountTable']//tbody/tr")
    private List<WebElement> accountRows;
    
    @FindBy(xpath = "//table[@id='accountTable']//tbody/tr/td[1]/a")
    private List<WebElement> accountNumbers;
    
    @FindBy(xpath = "//table[@id='accountTable']//tbody/tr/td[2]")
    private List<WebElement> accountBalances;
    
    @FindBy(xpath = "//table[@id='accountTable']//tbody/tr/td[3]")
    private List<WebElement> accountAvailableAmounts;
    
    @FindBy(xpath = "//b[contains(text(),'Total')]")
    private WebElement totalBalanceLabel;
    
    @FindBy(xpath = "//b[contains(text(),'Total')]/following-sibling::*")
    private WebElement totalBalanceValue;
    
    // ==================== Constructor ====================
    
    /**
     * Constructor to initialize AccountOverviewPage
     * @param driver WebDriver instance
     */
    public AccountOverviewPage(WebDriver driver) {
        super(driver);
    }
    
    // ==================== Page Verification Methods ====================
    
    /**
     * Verify if user is on Account Overview page
     * Covers AC6: User is redirected to account overview page
     * @return true if on Account Overview page, false otherwise
     */
    public boolean isOnAccountOverviewPage() {
        logger.info("Verifying if user is on Account Overview page");
        try {
            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            boolean isOnPage = pageTitle.getText().contains("Accounts Overview");
            logger.info("On Account Overview page: {}", isOnPage);
            return isOnPage;
        } catch (Exception e) {
            logger.error("Error while checking Account Overview page: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Get the page title text
     * @return String page title
     */
    public String getPageTitleText() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        String title = pageTitle.getText();
        logger.debug("Account Overview page title: {}", title);
        return title;
    }
    
    /**
     * Get the success message displayed after registration
     * Covers AC6: Welcome message is displayed
     * @return String success message text
     */
    public String getSuccessMessage() {
        logger.info("Getting success message");
        try {
            wait.until(ExpectedConditions.visibilityOf(successMessage));
            String message = successMessage.getText();
            logger.info("Success message: {}", message);
            return message;
        } catch (Exception e) {
            logger.error("Error while getting success message: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Verify if success message is displayed
     * @param expectedMessage Expected success message
     * @return true if message matches, false otherwise
     */
    public boolean isSuccessMessageDisplayed(String expectedMessage) {
        logger.info("Verifying success message: {}", expectedMessage);
        String actualMessage = getSuccessMessage();
        boolean isDisplayed = actualMessage.contains(expectedMessage);
        logger.info("Success message verification result: {}", isDisplayed);
        return isDisplayed;
    }
    
    /**
     * Get the logged-in username from welcome message
     * Covers verification: Username is displayed in logged-in user section
     * @return String username
     */
    public String getLoggedInUsername() {
        logger.info("Getting logged-in username");
        try {
            wait.until(ExpectedConditions.visibilityOf(welcomeUserMessage));
            String welcomeText = welcomeUserMessage.getText();
            // Extract username from "Welcome John Doe" format
            String username = welcomeText.replace("Welcome", "").trim();
            logger.info("Logged-in username: {}", username);
            return username;
        } catch (Exception e) {
            logger.error("Error while getting logged-in username: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Verify if specific username is displayed
     * @param expectedUsername Expected username
     * @return true if username matches, false otherwise
     */
    public boolean isUsernameDisplayed(String expectedUsername) {
        logger.info("Verifying if username '{}' is displayed", expectedUsername);
        String displayedUsername = getLoggedInUsername();
        boolean isDisplayed = displayedUsername.contains(expectedUsername);
        logger.info("Username verification result: {}", isDisplayed);
        return isDisplayed;
    }
    
    // ==================== Account Information Methods ====================
    
    /**
     * Verify if at least one account is created
     * Covers AC7: System creates a default checking account
     * @return true if at least one account exists, false otherwise
     */
    public boolean isAtLeastOneAccountCreated() {
        logger.info("Verifying if at least one account is created");
        try {
            wait.until(ExpectedConditions.visibilityOf(accountTable));
            int accountCount = accountRows.size();
            boolean hasAccounts = accountCount >= 1;
            logger.info("Number of accounts created: {}. Has at least one account: {}", 
                       accountCount, hasAccounts);
            return hasAccounts;
        } catch (Exception e) {
            logger.error("Error while checking account creation: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Get the count of accounts displayed
     * @return int number of accounts
     */
    public int getAccountCount() {
        logger.info("Getting account count");
        try {
            wait.until(ExpectedConditions.visibilityOf(accountTable));
            int count = accountRows.size();
            logger.info("Account count: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Error while getting account count: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Verify if account number is displayed
     * Covers verification: Account number is displayed
     * @return true if account number is displayed, false otherwise
     */
    public boolean isAccountNumberDisplayed() {
        logger.info("Verifying if account number is displayed");
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(accountNumbers));
            boolean isDisplayed = !accountNumbers.isEmpty() && 
                                 accountNumbers.get(0).isDisplayed();
            logger.info("Account number displayed: {}", isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Error while checking account number display: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Verify if account balance is displayed
     * Covers verification: Account balance is displayed
     * @return true if balance is displayed, false otherwise
     */
    public boolean isAccountBalanceDisplayed() {
        logger.info("Verifying if account balance is displayed");
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(accountBalances));
            boolean isDisplayed = !accountBalances.isEmpty() && 
                                 accountBalances.get(0).isDisplayed();
            logger.info("Account balance displayed: {}", isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Error while checking account balance display: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Get the first account number
     * @return String account number
     */
    public String getFirstAccountNumber() {
        logger.info("Getting first account number");
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(accountNumbers));
            String accountNumber = accountNumbers.get(0).getText();
            logger.info("First account number: {}", accountNumber);
            return accountNumber;
        } catch (Exception e) {
            logger.error("Error while getting first account number: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Get the first account balance
     * @return String account balance
     */
    public String getFirstAccountBalance() {
        logger.info("Getting first account balance");
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(accountBalances));
            String balance = accountBalances.get(0).getText();
            logger.info("First account balance: {}", balance);
            return balance;
        } catch (Exception e) {
            logger.error("Error while getting first account balance: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Verify if account table is displayed
     * @return true if account table is displayed, false otherwise
     */
    public boolean isAccountTableDisplayed() {
        logger.info("Verifying if account table is displayed");
        try {
            boolean isDisplayed = accountTable.isDisplayed();
            logger.info("Account table displayed: {}", isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Error while checking account table display: {}", e.getMessage());
            return false;
        }
    }
    
    // ==================== Action Methods ====================
    
    /**
     * Click on logout link to logout from the application
     */
    public void logout() {
        logger.info("Clicking logout link");
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutLink.click();
        logger.info("Logout successful");
    }
    
    /**
     * Click on a specific account number link
     * @param accountNumber Account number to click
     */
    public void clickAccountNumber(String accountNumber) {
        logger.info("Clicking on account number: {}", accountNumber);
        for (WebElement account : accountNumbers) {
            if (account.getText().equals(accountNumber)) {
                account.click();
                logger.info("Clicked on account number: {}", accountNumber);
                return;
            }
        }
        logger.warn("Account number '{}' not found", accountNumber);
    }
}
