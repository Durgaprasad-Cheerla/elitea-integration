# Edward Jones Login Page - Test Automation Framework

## 📋 Overview

This is a comprehensive Test Automation Framework for **Edward Jones Login Page** using:
- **Java** - Programming Language
- **Selenium WebDriver** - Browser Automation
- **Cucumber (BDD)** - Behavior Driven Development
- **JUnit** - Test Execution Framework
- **Maven** - Dependency Management
- **Page Object Model (POM)** - Design Pattern

## 🎯 Test Cases Covered

| Test Case ID | Jira Key | Description | Status |
|-------------|----------|-------------|---------|
| TC001 | SCRUM-175 | Verify Login Form Display and Layout Elements | ✅ Complete |
| TC002 | SCRUM-176 | Verify Successful User Authentication Flow | ✅ Complete |
| TC003 | SCRUM-177 | Verify Empty Field Validation | ✅ Complete |
| TC004 | SCRUM-178 | Verify Failed Login Error Handling | 🔄 In Progress |
| TC005 | SCRUM-179 | Verify Rate Limiting and Account Security | 🔄 In Progress |
| TC006 | SCRUM-180 | Verify Responsive Design Across Multiple Devices | 🔄 In Progress |
| TC007 | SCRUM-181 | Verify Accessibility Compliance (WCAG 2.1 AA) | 🔄 In Progress |
| TC008 | SCRUM-182 | Verify Page Load Performance and Security | 🔄 In Progress |

## 📁 Project Structure

```
edward-jones-automation/
├── src/
│   └── test/
│       ├── java/
│       │   ├── pages/                     # Page Object Model classes
│       │   │   ├── LoginPage.java
│       │   │   ├── DashboardPage.java
│       │   │   └── ...
│       │   ├── stepdefinitions/           # Cucumber Step Definitions
│       │   │   ├── TC001_LoginFormDisplaySteps.java
│       │   │   ├── TC002_SuccessfulAuthenticationSteps.java
│       │   │   └── ...
│       │   ├── runners/                   # Cucumber Test Runners
│       │   │   ├── TC001_LoginFormDisplayRunner.java
│       │   │   └── ...
│       │   └── utils/                     # Utility Classes
│       │       ├── DriverManager.java
│       │       ├── AccessibilityUtils.java
│       │       ├── Hooks.java
│       │       └── ...
│       └── resources/
│           └── features/                  # Cucumber Feature Files (Gherkin)
│               ├── TC001_LoginFormDisplay.feature
│               ├── TC002_SuccessfulAuthentication.feature
│               ├── TC003_EmptyFieldValidation.feature
│               └── ...
├── target/                                # Generated reports and artifacts
│   └── cucumber-reports/
├── pom.xml                                # Maven configuration
└── README.md                              # This file
```

## 🚀 Getting Started

### Prerequisites

1. **Java JDK 11 or higher**
   ```bash
   java -version
   ```

2. **Maven 3.6 or higher**
   ```bash
   mvn -version
   ```

3. **Chrome/Firefox/Edge Browser** (latest version)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd edward-jones-automation
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

## 🧪 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Case
```bash
# TC001 - Login Form Display
mvn test -Dtest=TC001_LoginFormDisplayRunner

# TC002 - Successful Authentication
mvn test -Dtest=TC002_SuccessfulAuthenticationRunner
```

### Run by Tags
```bash
# Run smoke tests only
mvn test -Dcucumber.filter.tags="@smoke"

# Run regression tests
mvn test -Dcucumber.filter.tags="@regression"

# Run specific test case
mvn test -Dcucumber.filter.tags="@tc001"
```

### Run with Different Browsers
```bash
# Chrome (default)
mvn test -Dbrowser=chrome

# Firefox
mvn test -Dbrowser=firefox

# Edge
mvn test -Dbrowser=edge

# Headless mode
mvn test -Dheadless=true
```

### Run with Custom Configuration
```bash
mvn test \
  -Dbrowser=chrome \
  -Dheadless=false \
  -Dlogin.url=https://www.edwardjones.com/login \
  -Dtest.username=testuser@edwardjones.com \
  -Dtest.password=ValidPass123!
```

## 📊 Test Reports

After test execution, reports are generated in multiple formats:

### Cucumber HTML Report
```
target/cucumber-reports/<TestCase>.html
```

### Cucumber JSON Report
```
target/cucumber-reports/<TestCase>.json
```

### Cucumber XML Report (JUnit format)
```
target/cucumber-reports/<TestCase>.xml
```

### View Reports
Open the HTML report in your browser:
```bash
# On macOS/Linux
open target/cucumber-reports/TC001_LoginFormDisplay.html

# On Windows
start target/cucumber-reports/TC001_LoginFormDisplay.html
```

## 🏗️ Framework Architecture

### Page Object Model (POM)

Each page has a corresponding Page class that encapsulates:
- **Web Elements** - Using @FindBy annotations
- **Page Actions** - Methods to interact with elements
- **Validations** - Methods to verify page state

Example:
```java
public class LoginPage {
    @FindBy(id = "username")
    private WebElement usernameField;
    
    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }
}
```

### Cucumber BDD

Feature files written in Gherkin syntax for business-readable test scenarios:

```gherkin
Feature: Login Form Display
  Scenario: Verify login form elements
    When the user navigates to the login page
    Then the username field should be visible
    And the password field should be visible
```

### Step Definitions

Java methods that implement Cucumber steps:

```java
@When("the user navigates to the login page")
public void navigateToLoginPage() {
    loginPage.navigateToLoginPage();
}
```

## 🔧 Configuration

### System Properties

| Property | Description | Default Value |
|----------|-------------|---------------|
| `browser` | Browser to use (chrome/firefox/edge) | chrome |
| `headless` | Run in headless mode (true/false) | false |
| `login.url` | Edward Jones login page URL | https://www.edwardjones.com/login |
| `test.username` | Test account username | testuser@edwardjones.com |
| `test.password` | Test account password | ValidPass123! |

### Setting Properties

**Via Maven command line:**
```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

**Via IDE (IntelliJ/Eclipse):**
Add to Run Configuration VM arguments:
```
-Dbrowser=chrome -Dheadless=false
```

## 📝 Writing New Tests

### 1. Create Feature File

Create a new `.feature` file in `src/test/resources/features/`:

```gherkin
@regression @mytag
Feature: My New Feature
  Scenario: My test scenario
    Given precondition
    When action
    Then expected result
```

### 2. Create Step Definitions

Create step definition class in `src/test/java/stepdefinitions/`:

```java
public class MySteps {
    @Given("precondition")
    public void precondition() {
        // Implementation
    }
    
    @When("action")
    public void action() {
        // Implementation
    }
    
    @Then("expected result")
    public void expectedResult() {
        // Assertion
    }
}
```

### 3. Create Test Runner

Create runner class in `src/test/java/runners/`:

```java
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/MyFeature.feature",
    glue = {"stepdefinitions", "utils"},
    plugin = {"pretty", "html:target/cucumber-reports/MyTest.html"}
)
public class MyTestRunner {
}
```

## 🐛 Debugging

### Enable Verbose Logging
Add to step definitions:
```java
System.out.println("Debug: Current URL = " + driver.getCurrentUrl());
```

### Take Screenshots
Screenshots are automatically captured for failed scenarios in the Hooks class.

### Browser Developer Tools
Run in non-headless mode and add breakpoints to inspect elements:
```bash
mvn test -Dheadless=false
```

## 🔐 Security Best Practices

1. **Never commit credentials** - Use environment variables or secure vaults
2. **HTTPS Only** - All tests verify HTTPS protocol usage
3. **Password Masking** - Verify passwords are masked in UI
4. **Session Management** - Tests verify secure session establishment
5. **CSRF Protection** - Tests verify CSRF tokens are present

## ✨ Best Practices

1. **Independent Tests** - Each test should be independent
2. **Clean Data** - Use @Before and @After hooks for setup/cleanup
3. **Meaningful Names** - Use descriptive names for tests and methods
4. **DRY Principle** - Don't repeat code, use utility methods
5. **Assertions** - Always include clear assertion messages
6. **Wait Strategies** - Use explicit waits, avoid Thread.sleep()

## 🆘 Troubleshooting

### Issue: WebDriver not found
**Solution:** WebDriverManager auto-downloads drivers. Ensure internet connection.

### Issue: Element not found
**Solution:** 
- Verify element locators are correct
- Add explicit waits
- Check if element is in iframe

### Issue: Tests failing intermittently
**Solution:**
- Increase timeout values
- Add proper waits (ExpectedConditions)
- Check network stability

### Issue: Browser not launching
**Solution:**
- Verify browser is installed
- Update browser to latest version
- Check WebDriverManager logs

## 📞 Support

For questions or issues:
- Create a Jira ticket in the SCRUM project
- Contact QA Automation Team
- Check confluence documentation

## 📄 License

Internal Use Only - Edward Jones QA Team

## 👥 Contributors

- QA Automation Team
- Generated for SCRUM-170 Test Cases

---

**Last Updated:** 2024
**Version:** 1.0.0
**Jira Epic:** SCRUM-170 - Implement Edward Jones Login Page with Authentication and Error Handling
