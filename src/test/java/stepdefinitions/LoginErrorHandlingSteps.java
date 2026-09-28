package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.Assert;
import pageobjects.LoginPage;
import utils.DriverManager;

/**
 * Step Definitions for SCRUM-191: Verify Error Handling for Invalid Login Credentials
 * 
 * This class maps Cucumber Gherkin steps to Java methods and implements
 * the test logic for invalid login credential error handling.
 */
public class LoginErrorHandlingSteps {
    
    private LoginPage loginPage;
    private String loginPageUrl = "https://onlineaccess.edwardjones.com/app/oa-login";
    
    public LoginErrorHandlingSteps() {
        this.loginPage = new LoginPage(DriverManager.getDriver());
    }
    
    @Given("the user is on the Edward Jones Online Access login page")
    public void theUserIsOnTheEdwardJonesOnlineAccessLoginPage() {
        loginPage.navigateToLoginPage(loginPageUrl);
        
        // Verify page is loaded successfully
        Assert.assertTrue(
            "Login page should be loaded successfully",
            loginPage.isPageLoaded()
        );
        
        // Verify page header
        String headerText = loginPage.getPageHeaderText();
        Assert.assertTrue(
            "Page header should contain 'Welcome to Online Access'",
            headerText.contains("Welcome to Online Access")
        );
    }
    
    @When("the user enters invalid User ID {string}")
    public void theUserEntersInvalidUserID(String userId) {
        // Handle empty string scenario
        if (userId.equals("''") || userId.isEmpty()) {
            userId = "";
        }
        loginPage.enterUserId(userId);
    }
    
    @And("the user enters invalid password {string}")
    public void theUserEntersInvalidPassword(String password) {
        // Handle empty string scenario
        if (password.equals("''") || password.isEmpty()) {
            password = "";
        }
        loginPage.enterPassword(password);
    }
    
    @And("the user clicks the Sign in button")
    public void theUserClicksTheSignInButton() {
        loginPage.clickSignInButton();
        
        // Wait for system response (2 seconds as per test step 5)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Then("a clear error message should be displayed indicating authentication failure")
    public void aClearErrorMessageShouldBeDisplayedIndicatingAuthenticationFailure() {
        Assert.assertTrue(
            "Error message should be displayed after invalid login attempt",
            loginPage.isErrorMessageDisplayed()
        );
        
        String errorMessage = loginPage.getErrorMessageText();
        Assert.assertFalse(
            "Error message should not be empty",
            errorMessage.isEmpty()
        );
        
        System.out.println("Error message displayed: " + errorMessage);
    }
    
    @And("the error message should be generic and not reveal specific field information")
    public void theErrorMessageShouldBeGenericAndNotRevealSpecificFieldInformation() {
        String errorMessage = loginPage.getErrorMessageText();
        
        // Verify error message contains generic phrases
        boolean isGeneric = errorMessage.toLowerCase().contains("invalid") ||
                          errorMessage.toLowerCase().contains("incorrect") ||
                          errorMessage.toLowerCase().contains("credentials") ||
                          errorMessage.toLowerCase().contains("user id or password") ||
                          errorMessage.toLowerCase().contains("authentication failed");
        
        Assert.assertTrue(
            "Error message should contain generic authentication failure text. Actual message: " + errorMessage,
            isGeneric
        );
    }
    
    @And("the error message should NOT contain {string} separately")
    public void theErrorMessageShouldNOTContainSeparately(String prohibitedText) {
        String errorMessage = loginPage.getErrorMessageText();
        
        // Check that error does NOT reveal which specific field is wrong
        Assert.assertFalse(
            "Error message should NOT contain '" + prohibitedText + "' separately. " +
            "This would reveal which credential is incorrect. Actual message: " + errorMessage,
            loginPage.doesErrorMessageContain(prohibitedText + " separately") ||
            (loginPage.doesErrorMessageContain(prohibitedText) && 
             !loginPage.doesErrorMessageContain("User ID or Password"))
        );
    }
    
    @And("the user should remain on the login page")
    public void theUserShouldRemainOnTheLoginPage() {
        Assert.assertTrue(
            "User should remain on login page after failed login attempt. Current URL: " + 
            loginPage.getCurrentUrl(),
            loginPage.isOnLoginPage(loginPageUrl)
        );
    }
    
    @And("the User ID field should be accessible for retry")
    public void theUserIDFieldShouldBeAccessibleForRetry() {
        Assert.assertTrue(
            "User ID field should be accessible (displayed and enabled) for retry",
            loginPage.isUserIdFieldAccessible()
        );
    }
    
    @And("the Password field should be accessible for retry")
    public void thePasswordFieldShouldBeAccessibleForRetry() {
        Assert.assertTrue(
            "Password field should be accessible (displayed and enabled) for retry",
            loginPage.isPasswordFieldAccessible()
        );
    }
}
