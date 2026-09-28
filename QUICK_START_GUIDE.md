# SCRUM-191 Test Automation - Quick Start Guide

## 📋 Summary
Automated test suite for validating error handling on Edward Jones Online Access login page when invalid credentials are provided. This ensures the system displays generic error messages to prevent credential enumeration attacks.

---

## 🎯 Test Objective
**Jira Ticket:** SCRUM-191  
**Test Case ID:** TC-SCRUM-189-003  
**Acceptance Criteria:** AC5 - Error Handling for Invalid Credentials

**What we're testing:**
- ✅ Generic error message is displayed (not revealing which field is wrong)
- ✅ User remains on login page after failed attempt
- ✅ Input fields remain accessible for retry
- ✅ No credential enumeration vulnerability

---

## 🏗️ Framework Architecture

```
┌─────────────────────────────────────────────────────┐
│         Cucumber Feature Files (BDD)                │
│         - Gherkin Scenarios                         │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────────┐
│         Step Definitions                            │
│         - Maps Gherkin steps to Java methods        │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────────┐
│         Page Objects (POM)                          │
│         - LoginPage.java                            │
│         - Encapsulates web elements & actions       │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────────┐
│         WebDriver (Selenium)                        │
│         - DriverManager (Singleton)                 │
│         - Browser automation                        │
└─────────────────────────────────────────────────────┘
```

---

## 📦 Generated Files

### 1. **Feature File** (BDD Scenarios)
**Location:** `src/test/resources/features/SCRUM-191_InvalidLoginErrorHandling.feature`
- Contains Gherkin scenarios derived from acceptance criteria
- Includes data-driven scenario outline for multiple test cases

### 2. **Page Object**
**Location:** `src/test/java/pageobjects/LoginPage.java`
- Encapsulates all login page web elements
- Uses priority-based locator strategy (data-testid → ID → XPath)
- Contains action methods (enterUserId, clickSignInButton, etc.)
- Includes validation methods (isErrorMessageDisplayed, etc.)

### 3. **Step Definitions**
**Location:** `src/test/java/stepdefinitions/LoginErrorHandlingSteps.java`
- Maps each Gherkin step to Java implementation
- Contains comprehensive assertions
- Implements error message validation logic

### 4. **Driver Manager**
**Location:** `src/test/java/utils/DriverManager.java`
- Singleton pattern for WebDriver management
- Supports Chrome and Firefox
- Configurable headless/headed mode
- Auto driver setup via WebDriverManager

### 5. **Hooks**
**Location:** `src/test/java/hooks/Hooks.java`
- @Before: Setup WebDriver before each scenario
- @After: Teardown + screenshot capture on failure

### 6. **Test Runner**
**Location:** `src/test/java/runners/TestRunner.java`
- JUnit + Cucumber runner configuration
- Executes tests with @SCRUM-191 tag
- Generates HTML, JSON, XML reports

### 7. **Configuration**
**Location:** `src/test/resources/config.properties`
- Centralized test configuration
- URLs, timeouts, test data

### 8. **Config Reader Utility**
**Location:** `src/test/java/utils/ConfigReader.java`
- Reads configuration from properties file
- Provides typed getter methods

---

## 🚀 Quick Execution Steps

### Step 1: Verify Prerequisites
```bash
# Check Java version (must be 11+)
java -version

# Check Maven version
mvn -version
```

### Step 2: Install Dependencies
```bash
# Install all required dependencies
mvn clean install -DskipTests
```

### Step 3: Run Tests
```bash
# Run with default settings (Chrome, headed mode)
mvn test

# Run in headless mode (for CI/CD)
mvn test -Dheadless=true

# Run with Firefox
mvn test -Dbrowser=firefox
```

### Step 4: View Reports
```bash
# Open HTML report in browser
open target/cucumber-reports/cucumber.html

# Or on Windows
start target/cucumber-reports/cucumber.html
```

---

## 🧪 Test Scenarios Included

### Scenario 1: Primary Invalid Login Test
```gherkin
Given the user is on the Edward Jones Online Access login page
When the user enters invalid User ID "InvalidUser999"
And the user enters invalid password "WrongPass123"
And the user clicks the Sign in button
Then a clear error message should be displayed
And the error message should be generic
And the user should remain on the login page
And both input fields should be accessible for retry
```

### Scenario 2: Data-Driven Tests
Tests multiple combinations:
- Invalid User ID + Invalid Password
- Valid-looking User ID + Invalid Password
- Empty User ID + Invalid Password
- Invalid User ID + Empty Password

---

## 🔍 Key Validations

### Security Validations
✅ **Generic Error Message:** 
- Must contain terms like "invalid", "incorrect", "credentials"
- Must NOT say "Invalid User ID" alone
- Must NOT say "Invalid Password" alone

✅ **No Credential Enumeration:**
- Error doesn't reveal which field is wrong
- Prevents attackers from determining valid usernames

✅ **Session Security:**
- No session created on failed login
- No redirection occurs

✅ **User Experience:**
- User stays on login page
- Fields remain accessible for retry

---

## 📊 Expected Test Results

### ✅ Passing Criteria
All assertions pass:
1. Error message displayed within 2 seconds
2. Error message is generic (e.g., "Invalid User ID or Password")
3. Current URL contains "oa-login"
4. User ID field is enabled and displayed
5. Password field is enabled and displayed

### ❌ Failure Scenarios
Test will fail if:
- No error message appears
- Error specifically says "Invalid User ID" (without "or Password")
- Error specifically says "Invalid Password" (without "or User ID")
- User is redirected away from login page
- Input fields become disabled

---

## 🎯 Locator Strategy (Anti-Hallucination)

All locators are derived from the actual UI screenshot provided in SCRUM-191:

### User ID Field
```java
// Priority 1: data-testid attribute (most stable)
@FindBy(css = "[data-testid='login-user-input']")

// Priority 2: ID attribute
@FindBy(id = "login-user")
```

### Password Field
```java
// XPath based on visible label
@FindBy(xpath = "//label[text()='Password']/following-sibling::div//input")
```

### Sign In Button
```java
// Button text matching
@FindBy(xpath = "//button[contains(text(),'Log In') or contains(text(),'Sign in')]")
```

### Page Header
```java
// Exact ID from DOM inspection
@FindBy(id = "credentials-form-header")
```

---

## 🛠️ Troubleshooting

### Problem: Tests fail immediately
**Solution:** Check if URL is accessible
```bash
curl -I https://onlineaccess.edwardjones.com/app/oa-login
```

### Problem: Element not found
**Solution:** 
- Verify page loaded completely
- Check if locators match current UI
- Increase wait time in DriverManager

### Problem: Headless mode fails
**Solution:** Some sites detect headless browsers
```bash
# Run in headed mode for debugging
mvn test -Dheadless=false
```

### Problem: WebDriver version mismatch
**Solution:** WebDriverManager auto-downloads correct version
- Ensure internet connection
- Clear cached drivers: `~/.cache/selenium/`

---

## 📈 CI/CD Integration

### Jenkins Pipeline Snippet
```groovy
stage('SCRUM-191 Automation') {
    steps {
        sh 'mvn clean test -Dheadless=true -Dcucumber.filter.tags=@SCRUM-191'
    }
    post {
        always {
            publishHTML([
                reportDir: 'target/cucumber-reports',
                reportFiles: 'cucumber.html',
                reportName: 'SCRUM-191 Test Report'
            ])
        }
    }
}
```

### GitHub Actions Snippet
```yaml
- name: Run SCRUM-191 Tests
  run: mvn test -Dheadless=true
- name: Upload Test Report
  uses: actions/upload-artifact@v3
  with:
    name: cucumber-report
    path: target/cucumber-reports/
```

---

## 📞 Support & Maintenance

### When UI Changes
Update `LoginPage.java` with new locators:
1. Inspect element in DevTools
2. Use priority: data-testid → ID → XPath
3. Update @FindBy annotations
4. Test locally before commit

### When Test Data Changes
Update `config.properties`:
```properties
invalid.userId=NewInvalidUser
invalid.password=NewInvalidPass
```

### When Expected Errors Change
Update assertions in `LoginErrorHandlingSteps.java`:
```java
boolean isGeneric = errorMessage.contains("new expected text");
```

---

## ✅ Success Checklist

- [ ] All dependencies installed (`mvn clean install`)
- [ ] Tests execute without compilation errors
- [ ] Browser launches successfully
- [ ] Login page loads
- [ ] Error message is captured and validated
- [ ] All assertions pass
- [ ] HTML report generated
- [ ] Screenshots captured on failure

---

## 📚 Additional Resources

- **Jira Ticket:** [SCRUM-191](https://durgaprasad675106.atlassian.net/browse/SCRUM-191)
- **Full README:** See `SCRUM-191_TEST_AUTOMATION_README.md`
- **Selenium Docs:** https://www.selenium.dev/documentation/
- **Cucumber Docs:** https://cucumber.io/docs/cucumber/

---

**Created:** 2026-09-28  
**Status:** ✅ Ready for Execution  
**Framework:** Selenium 4.41 + Cucumber 7.14 + JUnit 5.8 + Maven
