# SCRUM-191: Test Automation for Invalid Login Error Handling

## Test Case Overview
**Test Case ID:** TC-SCRUM-189-003  
**Title:** Verify Error Handling for Invalid Login Credentials  
**Acceptance Criteria:** AC5 - Error Handling for Invalid Credentials

## Purpose
This test automation validates that the Edward Jones Online Access login page properly handles invalid login credentials by:
- Displaying a clear, generic error message
- NOT revealing which specific credential (User ID or Password) is incorrect
- Maintaining security by preventing credential enumeration attacks
- Keeping the user on the login page with accessible fields for retry

## Architecture
This automation framework follows the **Page Object Model (POM)** design pattern with **Cucumber BDD**.

### Project Structure
```
src/test/
├── java/
│   ├── hooks/
│   │   └── Hooks.java                    # Cucumber Before/After hooks
│   ├── pageobjects/
│   │   └── LoginPage.java                # Login page elements and actions
│   ├── runners/
│   │   └── TestRunner.java               # Cucumber test runner
│   ├── stepdefinitions/
│   │   └── LoginErrorHandlingSteps.java  # Gherkin step implementations
│   └── utils/
│       └── DriverManager.java            # WebDriver management (Singleton)
└── resources/
    ├── config.properties                 # Test configuration
    └── features/
        └── SCRUM-191_InvalidLoginErrorHandling.feature  # BDD scenarios
```

## Technology Stack
- **Java:** JDK 11+
- **Selenium WebDriver:** 4.41.0
- **Cucumber:** 7.14.0
- **JUnit:** 5.8.2
- **WebDriverManager:** 6.3.3 (Auto driver management)
- **Maven:** Build and dependency management

## Key Features
1. **Page Object Model (POM):** Clean separation of test logic and page interactions
2. **Cucumber BDD:** Human-readable test scenarios in Gherkin syntax
3. **Robust Element Locators:** Priority-based locator strategy (data-testid → ID → XPath)
4. **Explicit Waits:** WebDriverWait for stable test execution
5. **Screenshot on Failure:** Automatic screenshot capture for failed scenarios
6. **Configurable Browser:** Support for Chrome/Firefox, headless/headed mode
7. **Detailed Assertions:** Comprehensive validation of error handling behavior

## Test Scenarios

### Scenario 1: Verify generic error message for invalid credentials
- **User ID:** InvalidUser999
- **Password:** WrongPass123
- **Validations:**
  - Error message is displayed
  - Error message is generic (doesn't reveal which field is wrong)
  - User remains on login page
  - Input fields are accessible for retry

### Scenario 2: Data-driven testing with multiple invalid combinations
Tests various combinations of invalid credentials to ensure consistent error handling.

## Prerequisites
1. **Java Development Kit (JDK) 11 or higher**
   ```bash
   java -version
   ```

2. **Apache Maven**
   ```bash
   mvn -version
   ```

3. **Chrome or Firefox browser** installed

4. **Internet connection** (for accessing the login page)

## Installation & Setup

### 1. Clone the repository
```bash
git clone <repository-url>
cd <repository-directory>
```

### 2. Checkout the test branch
```bash
git checkout SCRUM-191_Verify-Error-Handling-Invalid-Login
```

### 3. Install dependencies
```bash
mvn clean install -DskipTests
```

## Execution

### Run all tests with tag @SCRUM-191
```bash
mvn test
```

### Run tests with specific browser
```bash
# Chrome (default)
mvn test -Dbrowser=chrome

# Firefox
mvn test -Dbrowser=firefox
```

### Run tests in headless mode
```bash
mvn test -Dheadless=true
```

### Run specific scenario by tag
```bash
mvn test -Dcucumber.filter.tags="@TC-SCRUM-189-003"
```

### Dry run (validate Gherkin without execution)
```bash
mvn test -Dcucumber.options="--dry-run"
```

## Test Reports

After execution, reports are generated in:

### 1. Cucumber HTML Report
```
target/cucumber-reports/cucumber.html
```
Open in browser for detailed test results with steps and screenshots.

### 2. JSON Report
```
target/cucumber-reports/cucumber.json
```
Machine-readable format for CI/CD integration.

### 3. JUnit XML Report
```
target/cucumber-reports/cucumber.xml
```
Compatible with Jenkins, CircleCI, and other CI tools.

### 4. Console Output
Real-time test execution logs in the terminal with:
- Scenario names
- Step execution status
- Pass/Fail results
- Screenshot notifications

## Key Locator Strategy

### Priority 1: data-testid attribute
```java
@FindBy(css = "[data-testid='login-user-input']")
private WebElement userIdField;
```

### Priority 2: ID attribute
```java
@FindBy(id = "login-user")
private WebElement userIdFieldById;
```

### Priority 3: XPath (last resort)
```java
@FindBy(xpath = "//label[text()='Password']/following-sibling::div//input")
private WebElement passwordField;
```

## Error Message Validation Strategy

The automation validates:
1. ✅ Error message is displayed
2. ✅ Error message is NOT empty
3. ✅ Error message contains generic terms: "invalid", "incorrect", "credentials"
4. ❌ Error message does NOT contain "Invalid User ID" alone
5. ❌ Error message does NOT contain "Invalid Password" alone
6. ✅ User remains on login page (URL unchanged)
7. ✅ User ID field is accessible
8. ✅ Password field is accessible

## CI/CD Integration

### Jenkins Pipeline Example
```groovy
stage('SCRUM-191 Tests') {
    steps {
        sh 'mvn clean test -Dbrowser=chrome -Dheadless=true'
    }
    post {
        always {
            cucumber reportTitle: 'SCRUM-191 Test Report',
                    fileIncludePattern: '**/cucumber.json',
                    trendsLimit: 10
        }
    }
}
```

### GitHub Actions Example
```yaml
name: SCRUM-191 Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
      - name: Run tests
        run: mvn test -Dheadless=true
      - name: Publish test results
        uses: EnricoMi/publish-unit-test-result-action@v2
        with:
          files: target/cucumber-reports/cucumber.xml
```

## Troubleshooting

### Issue: WebDriver not found
**Solution:** WebDriverManager automatically downloads drivers. Ensure internet connection.

### Issue: Element not found
**Solution:** 
- Check if page loaded completely
- Increase explicit wait timeout
- Verify locator accuracy against actual page

### Issue: Tests fail in headless mode
**Solution:**
- Some sites block headless browsers
- Try with `--disable-blink-features=AutomationControlled`
- Use headed mode for debugging

### Issue: Screenshot not captured
**Solution:**
- Check `target/screenshots` directory permissions
- Verify Hooks.java is in glue path

## Security Considerations
This test specifically validates **security requirements**:
- Generic error messages prevent **credential enumeration attacks**
- No information leakage about which field is incorrect
- Complies with OWASP security best practices

## Maintenance
- **Locators:** If UI changes, update LoginPage.java locators
- **Test Data:** Modify config.properties for different test data
- **Wait Times:** Adjust timeouts in DriverManager.java if needed
- **Error Messages:** Update validation logic if expected messages change

## Contact
For questions or issues with this automation:
- **Jira Ticket:** SCRUM-191
- **Parent Story:** SCRUM-189
- **Test Case:** TC-SCRUM-189-003

## Version History
- **v1.0** - Initial automation implementation for SCRUM-191
  - Basic error handling validation
  - Data-driven testing support
  - Screenshot on failure
  - Multi-browser support

---

**Last Updated:** 2026-09-28  
**Status:** Ready for Execution  
**Framework:** Selenium + Cucumber + JUnit + Maven
