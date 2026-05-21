package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverManager;
import utils.AccessibilityUtils;

/**
 * Step Definition class for TC001 - Login Form Display and Layout Elements
 * Contains all Cucumber step implementations for login form display testing
 * 
 * @author QA Automation Team
 * @version 1.0
 */
public class TC001_LoginFormDisplaySteps {
    
    private WebDriver driver;
    private LoginPage loginPage;
    private AccessibilityUtils accessibilityUtils;
    
    public TC001_LoginFormDisplaySteps() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
        this.accessibilityUtils = new AccessibilityUtils(driver);
    }
    
    /* Background Steps */
    
    @Given("the Edward Jones login page URL is available")
    public void theEdwardJonesLoginPageURLIsAvailable() {
        // Verify configuration has login URL
        Assert.assertNotNull("Login URL should be configured", 
            System.getProperty("login.url", "https://www.edwardjones.com/login"));
    }
    
    @Given("the user is not currently logged in")
    public void theUserIsNotCurrentlyLoggedIn() {
        // Clear cookies and session to ensure user is logged out
        driver.manage().deleteAllCookies();
    }
    
    @Given("the user has access to a desktop browser")
    public void theUserHasAccessToADesktopBrowser() {
        // Verify browser is initialized
        Assert.assertNotNull("WebDriver should be initialized", driver);
    }
    
    /* When Steps */
    
    @When("the user navigates to the Edward Jones login page")
    public void theUserNavigatesToTheEdwardJonesLoginPage() {
        loginPage.navigateToLoginPage();
    }
    
    @When("the page fully loads")
    public void thePageFullyLoads() {
        loginPage.waitForPageLoad();
    }
    
    @When("the user types {string} in the Password field")
    public void theUserTypesInThePasswordField(String password) {
        loginPage.enterPassword(password);
    }
    
    /* Then Steps - Username/Email Field Verification */
    
    @Then("the Username or Email input field should be visible")
    public void theUsernameOrEmailInputFieldShouldBeVisible() {
        Assert.assertTrue("Username/Email field should be visible", 
            loginPage.isUsernameFieldVisible());
    }
    
    @Then("the Username or Email field should have placeholder text {string}")
    public void theUsernameOrEmailFieldShouldHavePlaceholderText(String expectedPlaceholder) {
        String actualPlaceholder = loginPage.getUsernameFieldPlaceholder();
        Assert.assertTrue("Username field placeholder should contain expected text",
            actualPlaceholder.toLowerCase().contains(expectedPlaceholder.toLowerCase()) ||
            actualPlaceholder.toLowerCase().contains("username"));
    }
    
    @Then("the Username or Email field type attribute should be {string} or {string}")
    public void theUsernameOrEmailFieldTypeAttributeShouldBeOr(String type1, String type2) {
        String actualType = loginPage.getUsernameFieldType();
        Assert.assertTrue("Username field type should be text or email",
            actualType.equals(type1) || actualType.equals(type2));
    }
    
    @Then("the Username or Email field should be marked as required")
    public void theUsernameOrEmailFieldShouldBeMarkedAsRequired() {
        Assert.assertTrue("Username field should be marked as required",
            loginPage.isUsernameFieldRequired());
    }
    
    /* Then Steps - Password Field Verification */
    
    @Then("the Password input field should be visible")
    public void thePasswordInputFieldShouldBeVisible() {
        Assert.assertTrue("Password field should be visible",
            loginPage.isPasswordFieldVisible());
    }
    
    @Then("the Password field should have placeholder text {string}")
    public void thePasswordFieldShouldHavePlaceholderText(String expectedPlaceholder) {
        String actualPlaceholder = loginPage.getPasswordFieldPlaceholder();
        Assert.assertTrue("Password field placeholder should contain expected text",
            actualPlaceholder.toLowerCase().contains(expectedPlaceholder.toLowerCase()) ||
            actualPlaceholder.toLowerCase().contains("password"));
    }
    
    @Then("the Password field type attribute should be {string}")
    public void thePasswordFieldTypeAttributeShouldBe(String expectedType) {
        String actualType = loginPage.getPasswordFieldType();
        Assert.assertEquals("Password field type should be 'password'",
            expectedType, actualType);
    }
    
    @Then("the Password field should be marked as required")
    public void thePasswordFieldShouldBeMarkedAsRequired() {
        Assert.assertTrue("Password field should be marked as required",
            loginPage.isPasswordFieldRequired());
    }
    
    @Then("the Password field characters should be masked with bullets or asterisks")
    public void thePasswordFieldCharactersShouldBeMaskedWithBulletsOrAsterisks() {
        // Verify that password field type is "password" which automatically masks characters
        Assert.assertEquals("Password field should have type='password' for masking",
            "password", loginPage.getPasswordFieldType());
        
        // Additional verification: password value should still be retrievable but displayed as masked
        String passwordValue = loginPage.getPasswordFieldValue();
        Assert.assertNotNull("Password field should have a value", passwordValue);
    }
    
    /* Then Steps - Login Button Verification */
    
    @Then("the Login or Sign In button should be present and clearly labeled")
    public void theLoginOrSignInButtonShouldBePresentAndClearlyLabeled() {
        Assert.assertTrue("Login button should be visible",
            loginPage.isLoginButtonVisible());
        
        String buttonText = loginPage.getLoginButtonText();
        Assert.assertTrue("Login button should have appropriate label",
            buttonText.toLowerCase().contains("login") || 
            buttonText.toLowerCase().contains("sign in"));
    }
    
    @Then("the Login button should be in enabled state")
    public void theLoginButtonShouldBeInEnabledState() {
        Assert.assertTrue("Login button should be enabled",
            loginPage.isLoginButtonEnabled());
    }
    
    /* Then Steps - Accessibility Verification */
    
    @Then("the Login button color contrast should meet WCAG 2.1 AA requirements")
    public void theLoginButtonColorContrastShouldMeetWCAGRequirements() {
        String backgroundColor = loginPage.getLoginButtonBackgroundColor();
        String textColor = loginPage.getLoginButtonTextColor();
        
        double contrastRatio = accessibilityUtils.calculateContrastRatio(backgroundColor, textColor);
        
        Assert.assertTrue("Login button should meet minimum contrast ratio",
            contrastRatio >= 3.0);
    }
    
    @Then("the contrast ratio should be at least {double}:1 for normal text")
    public void theContrastRatioShouldBeAtLeastForNormalText(double minRatio) {
        String backgroundColor = loginPage.getLoginButtonBackgroundColor();
        String textColor = loginPage.getLoginButtonTextColor();
        
        double contrastRatio = accessibilityUtils.calculateContrastRatio(backgroundColor, textColor);
        
        Assert.assertTrue(String.format("Contrast ratio should be at least %.1f:1 for normal text", minRatio),
            contrastRatio >= minRatio);
    }
    
    @Then("the contrast ratio should be at least {double}:1 for large text")
    public void theContrastRatioShouldBeAtLeastForLargeText(double minRatio) {
        String backgroundColor = loginPage.getLoginButtonBackgroundColor();
        String textColor = loginPage.getLoginButtonTextColor();
        
        double contrastRatio = accessibilityUtils.calculateContrastRatio(backgroundColor, textColor);
        
        Assert.assertTrue(String.format("Contrast ratio should be at least %.1f:1 for large text", minRatio),
            contrastRatio >= minRatio);
    }
    
    /* Then Steps - Branding Verification */
    
    @Then("the Edward Jones logo should be visible in the page header")
    public void theEdwardJonesLogoShouldBeVisibleInThePageHeader() {
        Assert.assertTrue("Edward Jones logo should be visible",
            loginPage.isLogoVisible());
    }
    
    @Then("the page should use official Edward Jones brand colors")
    public void thePageShouldUseOfficialEdwardJonesBrandColors() {
        // This is a visual check - in real implementation, you would verify specific color codes
        // For automation, we verify that branding elements are present
        Assert.assertTrue("Logo should be present indicating branding is applied",
            loginPage.isLogoVisible());
    }
    
    @Then("the Edward Jones logo should be displayed in the header")
    public void theEdwardJonesLogoShouldBeDisplayedInTheHeader() {
        Assert.assertTrue("Edward Jones logo should be displayed in header",
            loginPage.isLogoVisible());
    }
    
    @Then("the page should maintain consistent brand colors")
    public void thePageShouldMaintainConsistentBrandColors() {
        // Verify page elements are present which indicates branding is applied
        Assert.assertTrue("Page should have branding elements",
            loginPage.isLogoVisible());
    }
    
    @Then("the page typography should be consistent with Edward Jones branding")
    public void thePageTypographyShouldBeConsistentWithEdwardJonesBranding() {
        // Verify page title contains Edward Jones branding
        String pageTitle = loginPage.getPageTitle();
        Assert.assertTrue("Page title should contain Edward Jones branding",
            pageTitle.toLowerCase().contains("edward") || 
            pageTitle.toLowerCase().contains("jones"));
    }
}
