package com.parabank.stepdefinitions;

import com.parabank.pages.AccountOverviewPage;
import com.parabank.pages.HomePage;
import com.parabank.pages.RegistrationPage;
import com.parabank.utils.ConfigReader;
import com.parabank.utils.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Step Definitions for User Registration Feature
 * JIRA: SCRUM-166, SCRUM-167
 * Test Case: TC001 - Verify Successful User Registration with Valid Data
 * 
 * Contains step implementations for all Cucumber scenarios related to user registration
 * 
 * @author Automation Team
 * @version 1.0
 */
public class UserRegistrationSteps {
    
    private static final Logger logger = LogManager.getLogger(UserRegistrationSteps.class);
    
    private WebDriver driver;
    private HomePage homePage;
    private RegistrationPage registrationPage;
    private AccountOverviewPage accountOverviewPage;
    
    /**
     * Constructor - initializes WebDriver and page objects
     */
    public UserRegistrationSteps() {
        this.driver = DriverManager.getDriver();
        this.homePage = new HomePage(driver);
        this.registrationPage = new RegistrationPage(driver);
        this.accountOverviewPage = new AccountOverviewPage(driver);
    }
    
    // ==================== Background Steps ====================
    
    /**
     * Step: ParaBank application is accessible and running
     * Precondition: Verify the application URL is accessible
     */
    @Given("the ParaBank application is accessible and running")
    public void theParaBankApplicationIsAccessibleAndRunning() {
        logger.info("Step: Verifying ParaBank application is accessible");
        
        String baseUrl = ConfigReader.getBaseUrl();
        logger.info("Base URL from config: {}", baseUrl);
        
        Assert.assertNotNull(baseUrl, "Base URL should not be null in configuration");
        Assert.assertFalse(baseUrl.isEmpty(), "Base URL should not be empty in configuration");
        
        logger.info("ParaBank application URL is configured: {}", baseUrl);
    }
    
    /**
     * Step: User is on ParaBank homepage
     * Precondition: Navigate to ParaBank homepage
     */
    @Given("the user is on ParaBank homepage")
    public void theUserIsOnParaBankHomepage() {
        logger.info("Step: Navigating to ParaBank homepage");
        
        String baseUrl = ConfigReader.getBaseUrl();
        driver.get(baseUrl);
        logger.info("Navigated to ParaBank homepage: {}", baseUrl);
        
        // Verify page is loaded by checking for logo or key element
        Assert.assertTrue(homePage.isLogoDisplayed(), 
                         "ParaBank logo should be displayed on homepage");
        
        logger.info("Successfully loaded ParaBank homepage");
    }
    
    // ==================== When Steps (Actions) ====================
    
    /**
     * Step: User clicks on Register link
     * AC1: User can access the registration page from the homepage
     */
    @When("the user clicks on {string} link in the login panel")
    public void theUserClicksOnLinkInTheLoginPanel(String linkText) {
        logger.info("Step: Clicking on '{}' link in the login panel", linkText);
        
        if (linkText.equalsIgnoreCase("Register")) {
            Assert.assertTrue(homePage.isRegisterLinkVisible(), 
                            "Register link should be visible on homepage");
            homePage.clickRegisterLink();
            logger.info("Successfully clicked on Register link");
        } else {
            logger.error("Unknown link text: {}", linkText);
            Assert.fail("Link '" + linkText + "' is not implemented");
        }
    }
    
    /**
     * Step: User enters registration details from data table
     * Covers test steps 4-14: Enter all registration field values
     */
    @When("the user enters the following registration details:")
    public void theUserEntersTheFollowingRegistrationDetails(io.cucumber.datatable.DataTable dataTable) {
        logger.info("Step: Entering registration details from data table");
        
        // Convert DataTable to Map
        Map<String, String> registrationData = new HashMap<>();
        List<List<String>> rows = dataTable.asLists(String.class);
        
        // Skip header row and process data rows
        for (int i = 1; i < rows.size(); i++) {
            String field = rows.get(i).get(0);
            String value = rows.get(i).get(1);
            registrationData.put(field, value);
            logger.debug("Registration data - {}: {}", field, 
                        field.toLowerCase().contains("password") ? "****" : value);
        }
        
        // Fill the registration form
        registrationPage.fillRegistrationForm(registrationData);
        
        logger.info("Successfully entered all registration details");
    }
    
    /**
     * Step: User enters value in specific field
     */
    @When("the user enters {string} in the {string} field")
    public void theUserEntersInTheField(String value, String fieldName) {
        logger.info("Step: Entering '{}' in the '{}' field", 
                   fieldName.toLowerCase().contains("password") ? "****" : value, 
                   fieldName);
        
        registrationPage.enterFieldValue(fieldName, value);
        
        logger.info("Successfully entered value in '{}' field", fieldName);
    }
    
    /**
     * Step: User clicks on Register button
     * Test step 15: Click the 'Register' button
     */
    @When("the user clicks on {string} button")
    public void theUserClicksOnButton(String buttonName) {
        logger.info("Step: Clicking on '{}' button", buttonName);
        
        if (buttonName.equalsIgnoreCase("Register")) {
            registrationPage.clickRegisterButton();
            logger.info("Successfully clicked on Register button");
            
            // Wait for page transition
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.error("Interrupted while waiting for page load", e);
            }
        } else {
            logger.error("Unknown button: {}", buttonName);
            Assert.fail("Button '" + buttonName + "' is not implemented");
        }
    }
    
    // ==================== Then Steps (Assertions) ====================
    
    /**
     * Step: Verify registration form is displayed
     * AC2: Registration form is displayed
     */
    @Then("the registration form should be displayed")
    public void theRegistrationFormShouldBeDisplayed() {
        logger.info("Step: Verifying registration form is displayed");
        
        Assert.assertTrue(registrationPage.isRegistrationFormDisplayed(), 
                         "Registration form should be displayed");
        
        logger.info("Registration form is displayed successfully");
    }
    
    /**
     * Step: Verify registration form contains all required fields
     * AC2: Registration form includes all mandatory fields
     */
    @Then("the registration form should contain the following fields:")
    public void theRegistrationFormShouldContainTheFollowingFields(io.cucumber.datatable.DataTable dataTable) {
        logger.info("Step: Verifying registration form contains all required fields");
        
        List<String> expectedFields = dataTable.asList(String.class);
        
        // Verify all required fields are present
        for (String fieldName : expectedFields) {
            boolean isPresent = registrationPage.isFieldPresent(fieldName);
            logger.debug("Field '{}' present: {}", fieldName, isPresent);
            Assert.assertTrue(isPresent, 
                            "Field '" + fieldName + "' should be present on registration form");
        }
        
        logger.info("All required fields are present on registration form");
    }
    
    /**
     * Step: Verify user is redirected to specific page
     * AC6: User is redirected to account overview page
     */
    @Then("the user should be redirected to the {string} page")
    public void theUserShouldBeRedirectedToThePage(String pageName) {
        logger.info("Step: Verifying user is redirected to '{}' page", pageName);
        
        if (pageName.equalsIgnoreCase("Account Overview")) {
            Assert.assertTrue(accountOverviewPage.isOnAccountOverviewPage(), 
                            "User should be redirected to Account Overview page");
            logger.info("Successfully redirected to Account Overview page");
        } else {
            logger.error("Unknown page name: {}", pageName);
            Assert.fail("Page verification for '" + pageName + "' is not implemented");
        }
    }
    
    /**
     * Step: Verify success message is displayed
     * AC6: Welcome message is displayed after successful registration
     */
    @Then("a success message {string} should be displayed")
    public void aSuccessMessageShouldBeDisplayed(String expectedMessage) {
        logger.info("Step: Verifying success message is displayed");
        
        boolean isDisplayed = accountOverviewPage.isSuccessMessageDisplayed(expectedMessage);
        Assert.assertTrue(isDisplayed, 
                         "Success message '" + expectedMessage + "' should be displayed");
        
        logger.info("Success message is displayed correctly");
    }
    
    /**
     * Step: Verify username is displayed in logged-in section
     * Verification: Username is displayed for logged-in user
     */
    @Then("the username {string} should be displayed in the logged-in user section")
    public void theUsernameShouldBeDisplayedInTheLoggedInUserSection(String expectedUsername) {
        logger.info("Step: Verifying username '{}' is displayed", expectedUsername);
        
        boolean isDisplayed = accountOverviewPage.isUsernameDisplayed(expectedUsername);
        Assert.assertTrue(isDisplayed, 
                         "Username '" + expectedUsername + "' should be displayed in logged-in section");
        
        logger.info("Username is displayed correctly in logged-in section");
    }
    
    /**
     * Step: Verify at least one default checking account is created
     * AC7: System creates a default checking account for new user
     */
    @Then("at least one default checking account should be created")
    public void atLeastOneDefaultCheckingAccountShouldBeCreated() {
        logger.info("Step: Verifying at least one default account is created");
        
        Assert.assertTrue(accountOverviewPage.isAtLeastOneAccountCreated(), 
                         "At least one default checking account should be created");
        
        int accountCount = accountOverviewPage.getAccountCount();
        logger.info("Number of accounts created: {}", accountCount);
    }
    
    /**
     * Step: Verify account number and balance are displayed
     * Verification: Account details are displayed for newly created account
     */
    @Then("the account number and balance should be displayed for the newly created account")
    public void theAccountNumberAndBalanceShouldBeDisplayedForTheNewlyCreatedAccount() {
        logger.info("Step: Verifying account number and balance are displayed");
        
        Assert.assertTrue(accountOverviewPage.isAccountNumberDisplayed(), 
                         "Account number should be displayed");
        Assert.assertTrue(accountOverviewPage.isAccountBalanceDisplayed(), 
                         "Account balance should be displayed");
        
        String accountNumber = accountOverviewPage.getFirstAccountNumber();
        String balance = accountOverviewPage.getFirstAccountBalance();
        
        logger.info("Account Number: {}", accountNumber);
        logger.info("Account Balance: {}", balance);
        
        Assert.assertFalse(accountNumber.isEmpty(), "Account number should not be empty");
        Assert.assertFalse(balance.isEmpty(), "Account balance should not be empty");
        
        logger.info("Account number and balance are displayed correctly");
    }
    
    // ==================== And Steps (Additional Verifications) ====================
    
    /**
     * Additional verification step - can be used for any And condition
     */
    @And("the account table should be visible")
    public void theAccountTableShouldBeVisible() {
        logger.info("Step: Verifying account table is visible");
        
        Assert.assertTrue(accountOverviewPage.isAccountTableDisplayed(), 
                         "Account table should be visible");
        
        logger.info("Account table is visible");
    }
}
