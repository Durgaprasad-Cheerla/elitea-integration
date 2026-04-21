package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page Object Model for ParaBank Registration Page
 * Contains all elements and methods for user registration functionality
 * 
 * JIRA: SCRUM-166, SCRUM-167
 * Test Case: TC001 - Verify Successful User Registration with Valid Data
 * 
 * @author Automation Team
 * @version 1.0
 */
public class RegistrationPage extends BasePage {
    
    // ==================== Page Elements ====================
    
    // Personal Information Fields
    @FindBy(id = "customer.firstName")
    private WebElement firstNameInput;
    
    @FindBy(id = "customer.lastName")
    private WebElement lastNameInput;
    
    @FindBy(id = "customer.address.street")
    private WebElement addressInput;
    
    @FindBy(id = "customer.address.city")
    private WebElement cityInput;
    
    @FindBy(id = "customer.address.state")
    private WebElement stateInput;
    
    @FindBy(id = "customer.address.zipCode")
    private WebElement zipCodeInput;
    
    @FindBy(id = "customer.phoneNumber")
    private WebElement phoneInput;
    
    @FindBy(id = "customer.ssn")
    private WebElement ssnInput;
    
    // Login Credentials Fields
    @FindBy(id = "customer.username")
    private WebElement usernameInput;
    
    @FindBy(id = "customer.password")
    private WebElement passwordInput;
    
    @FindBy(id = "repeatedPassword")
    private WebElement confirmPasswordInput;
    
    // Buttons
    @FindBy(xpath = "//input[@value='Register']")
    private WebElement registerButton;
    
    // Validation and Success Messages
    @FindBy(id = "customer.firstName.errors")
    private WebElement firstNameError;
    
    @FindBy(id = "customer.lastName.errors")
    private WebElement lastNameError;
    
    @FindBy(id = "customer.address.street.errors")
    private WebElement addressError;
    
    @FindBy(id = "customer.address.city.errors")
    private WebElement cityError;
    
    @FindBy(id = "customer.address.state.errors")
    private WebElement stateError;
    
    @FindBy(id = "customer.address.zipCode.errors")
    private WebElement zipCodeError;
    
    @FindBy(id = "customer.ssn.errors")
    private WebElement ssnError;
    
    @FindBy(id = "customer.username.errors")
    private WebElement usernameError;
    
    @FindBy(id = "customer.password.errors")
    private WebElement passwordError;
    
    @FindBy(id = "repeatedPassword.errors")
    private WebElement confirmPasswordError;
    
    @FindBy(xpath = "//div[@id='rightPanel']//h1[@class='title']")
    private WebElement pageTitle;
    
    // ==================== Constructor ====================
    
    /**
     * Constructor to initialize RegistrationPage
     * @param driver WebDriver instance
     */
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }
    
    // ==================== Page Verification Methods ====================
    
    /**
     * Verify if registration form is displayed
     * Covers AC2: Registration form includes all required fields
     * @return true if registration form is displayed, false otherwise
     */
    public boolean isRegistrationFormDisplayed() {
        logger.info("Verifying if registration form is displayed");
        try {
            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            boolean isDisplayed = pageTitle.getText().contains("Signing up is easy!");
            logger.info("Registration form displayed: {}", isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Error while checking registration form visibility: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Verify all required fields are present on the registration form
     * Covers AC2: Registration form includes all mandatory fields
     * @return true if all fields are present, false otherwise
     */
    public boolean areAllFieldsPresent() {
        logger.info("Verifying all required fields are present");
        try {
            boolean allFieldsPresent = firstNameInput.isDisplayed() &&
                    lastNameInput.isDisplayed() &&
                    addressInput.isDisplayed() &&
                    cityInput.isDisplayed() &&
                    stateInput.isDisplayed() &&
                    zipCodeInput.isDisplayed() &&
                    phoneInput.isDisplayed() &&
                    ssnInput.isDisplayed() &&
                    usernameInput.isDisplayed() &&
                    passwordInput.isDisplayed() &&
                    confirmPasswordInput.isDisplayed();
            
            logger.info("All required fields present: {}", allFieldsPresent);
            return allFieldsPresent;
        } catch (Exception e) {
            logger.error("Error while checking field presence: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Verify if specific field is present on the form
     * @param fieldName Name of the field to verify
     * @return true if field is present, false otherwise
     */
    public boolean isFieldPresent(String fieldName) {
        logger.debug("Checking if field '{}' is present", fieldName);
        try {
            WebElement field = getFieldElement(fieldName);
            boolean isPresent = field != null && field.isDisplayed();
            logger.debug("Field '{}' present: {}", fieldName, isPresent);
            return isPresent;
        } catch (Exception e) {
            logger.error("Field '{}' not found: {}", fieldName, e.getMessage());
            return false;
        }
    }
    
    // ==================== Data Entry Methods ====================
    
    /**
     * Enter first name in the registration form
     * @param firstName First name value
     */
    public void enterFirstName(String firstName) {
        logger.info("Entering first name: {}", firstName);
        wait.until(ExpectedConditions.visibilityOf(firstNameInput));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }
    
    /**
     * Enter last name in the registration form
     * @param lastName Last name value
     */
    public void enterLastName(String lastName) {
        logger.info("Entering last name: {}", lastName);
        wait.until(ExpectedConditions.visibilityOf(lastNameInput));
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }
    
    /**
     * Enter address in the registration form
     * @param address Address value
     */
    public void enterAddress(String address) {
        logger.info("Entering address: {}", address);
        wait.until(ExpectedConditions.visibilityOf(addressInput));
        addressInput.clear();
        addressInput.sendKeys(address);
    }
    
    /**
     * Enter city in the registration form
     * @param city City value
     */
    public void enterCity(String city) {
        logger.info("Entering city: {}", city);
        wait.until(ExpectedConditions.visibilityOf(cityInput));
        cityInput.clear();
        cityInput.sendKeys(city);
    }
    
    /**
     * Enter state in the registration form
     * @param state State value
     */
    public void enterState(String state) {
        logger.info("Entering state: {}", state);
        wait.until(ExpectedConditions.visibilityOf(stateInput));
        stateInput.clear();
        stateInput.sendKeys(state);
    }
    
    /**
     * Enter zip code in the registration form
     * @param zipCode Zip code value
     */
    public void enterZipCode(String zipCode) {
        logger.info("Entering zip code: {}", zipCode);
        wait.until(ExpectedConditions.visibilityOf(zipCodeInput));
        zipCodeInput.clear();
        zipCodeInput.sendKeys(zipCode);
    }
    
    /**
     * Enter phone number in the registration form
     * @param phone Phone number value
     */
    public void enterPhone(String phone) {
        logger.info("Entering phone: {}", phone);
        wait.until(ExpectedConditions.visibilityOf(phoneInput));
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }
    
    /**
     * Enter SSN in the registration form
     * @param ssn SSN value
     */
    public void enterSSN(String ssn) {
        logger.info("Entering SSN: {}", ssn);
        wait.until(ExpectedConditions.visibilityOf(ssnInput));
        ssnInput.clear();
        ssnInput.sendKeys(ssn);
    }
    
    /**
     * Enter username in the registration form
     * @param username Username value
     */
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }
    
    /**
     * Enter password in the registration form
     * @param password Password value
     */
    public void enterPassword(String password) {
        logger.info("Entering password");
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }
    
    /**
     * Enter confirm password in the registration form
     * @param confirmPassword Confirm password value
     */
    public void enterConfirmPassword(String confirmPassword) {
        logger.info("Entering confirm password");
        wait.until(ExpectedConditions.visibilityOf(confirmPasswordInput));
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(confirmPassword);
    }
    
    /**
     * Enter value in a specific field by field name
     * @param fieldName Name of the field
     * @param value Value to enter
     */
    public void enterFieldValue(String fieldName, String value) {
        logger.info("Entering value '{}' in field '{}'", value, fieldName);
        WebElement field = getFieldElement(fieldName);
        if (field != null) {
            wait.until(ExpectedConditions.visibilityOf(field));
            field.clear();
            field.sendKeys(value);
        } else {
            logger.error("Field '{}' not found", fieldName);
            throw new RuntimeException("Field '" + fieldName + "' not found on registration page");
        }
    }
    
    /**
     * Fill all registration details from a map
     * @param registrationData Map containing field names and values
     */
    public void fillRegistrationForm(Map<String, String> registrationData) {
        logger.info("Filling registration form with provided data");
        
        if (registrationData.containsKey("First Name")) {
            enterFirstName(registrationData.get("First Name"));
        }
        if (registrationData.containsKey("Last Name")) {
            enterLastName(registrationData.get("Last Name"));
        }
        if (registrationData.containsKey("Address")) {
            enterAddress(registrationData.get("Address"));
        }
        if (registrationData.containsKey("City")) {
            enterCity(registrationData.get("City"));
        }
        if (registrationData.containsKey("State")) {
            enterState(registrationData.get("State"));
        }
        if (registrationData.containsKey("Zip Code")) {
            enterZipCode(registrationData.get("Zip Code"));
        }
        if (registrationData.containsKey("Phone")) {
            enterPhone(registrationData.get("Phone"));
        }
        if (registrationData.containsKey("SSN")) {
            enterSSN(registrationData.get("SSN"));
        }
        if (registrationData.containsKey("Username")) {
            enterUsername(registrationData.get("Username"));
        }
        if (registrationData.containsKey("Password")) {
            enterPassword(registrationData.get("Password"));
        }
        if (registrationData.containsKey("Confirm Password")) {
            enterConfirmPassword(registrationData.get("Confirm Password"));
        }
        
        logger.info("Registration form filled successfully");
    }
    
    // ==================== Action Methods ====================
    
    /**
     * Click the Register button to submit the registration form
     * Covers test step 15: Click the 'Register' button
     */
    public void clickRegisterButton() {
        logger.info("Clicking Register button");
        wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        registerButton.click();
        logger.info("Register button clicked");
    }
    
    /**
     * Complete the entire registration process
     * @param registrationData Map containing all registration field values
     */
    public void registerUser(Map<String, String> registrationData) {
        logger.info("Starting user registration process");
        fillRegistrationForm(registrationData);
        clickRegisterButton();
        logger.info("User registration process completed");
    }
    
    // ==================== Helper Methods ====================
    
    /**
     * Get WebElement for a field by its display name
     * @param fieldName Display name of the field
     * @return WebElement corresponding to the field name
     */
    private WebElement getFieldElement(String fieldName) {
        switch (fieldName.toLowerCase().trim()) {
            case "first name":
                return firstNameInput;
            case "last name":
                return lastNameInput;
            case "address":
                return addressInput;
            case "city":
                return cityInput;
            case "state":
                return stateInput;
            case "zip code":
                return zipCodeInput;
            case "phone":
                return phoneInput;
            case "ssn":
                return ssnInput;
            case "username":
                return usernameInput;
            case "password":
                return passwordInput;
            case "confirm password":
                return confirmPasswordInput;
            default:
                logger.error("Unknown field name: {}", fieldName);
                return null;
        }
    }
    
    /**
     * Get page title text
     * @return String page title
     */
    public String getPageTitle() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        String title = pageTitle.getText();
        logger.debug("Registration page title: {}", title);
        return title;
    }
}
