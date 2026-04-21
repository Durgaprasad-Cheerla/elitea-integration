# ParaBank Test Automation Framework

## Project Overview
This is a comprehensive **Selenium + Cucumber + TestNG** automation framework for testing the **ParaBank** web application. The framework follows **Page Object Model (POM)** design pattern and implements **Behavior-Driven Development (BDD)** using Cucumber.

## JIRA Reference
- **Parent User Story**: SCRUM-166 - User Registration
- **Test Case**: SCRUM-167 - TC001 - Verify Successful User Registration with Valid Data

## Technology Stack
- **Language**: Java 11
- **Build Tool**: Maven
- **Test Framework**: TestNG 7.12.0
- **BDD Framework**: Cucumber 7.14.0
- **UI Automation**: Selenium WebDriver 4.41.0
- **Driver Management**: WebDriverManager 6.3.3
- **Reporting**: Cucumber HTML Reports, ExtentReports 5.1.1
- **Logging**: Log4j2 2.22.0

## Project Structure
```
parabank-test-automation/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── parabank/
│       │           ├── pages/              # Page Object Model classes
│       │           │   ├── BasePage.java
│       │           │   ├── HomePage.java
│       │           │   ├── RegistrationPage.java
│       │           │   └── AccountOverviewPage.java
│       │           ├── stepdefinitions/    # Cucumber step definitions
│       │           │   ├── Hooks.java
│       │           │   └── UserRegistrationSteps.java
│       │           ├── runners/            # TestNG runner classes
│       │           │   └── TestRunner.java
│       │           └── utils/              # Utility classes
│       │               ├── DriverManager.java
│       │               └── ConfigReader.java
│       └── resources/
│           ├── features/                   # Cucumber feature files
│           │   └── UserRegistration.feature
│           ├── config.properties           # Configuration properties
│           └── log4j2.xml                  # Logging configuration
├── test-output/                            # Test execution output
│   ├── cucumber-reports/                   # Cucumber HTML reports
│   ├── screenshots/                        # Test screenshots
│   ├── logs/                               # Test execution logs
│   └── reports/                            # ExtentReports
├── pom.xml                                 # Maven dependencies
├── testng.xml                              # TestNG suite configuration
└── README.md                               # Project documentation
```

## Features Implemented

### Test Case: TC001 - Verify Successful User Registration with Valid Data
**Acceptance Criteria Covered:**
- ✅ AC1: User can access the registration page from the homepage
- ✅ AC2: Registration form includes all required fields
- ✅ AC3: All fields are mandatory with validation messages
- ✅ AC4: Password and Confirm Password fields must match
- ✅ AC5: Username must be unique
- ✅ AC6: User is redirected to account overview with welcome message
- ✅ AC7: System creates a default checking account
- ✅ AC8: Error messages for invalid data formats

**Test Steps Automated:**
1. Navigate to ParaBank homepage
2. Click on 'Register' link
3. Verify registration form is displayed with all fields
4. Enter valid data in all fields (First Name, Last Name, Address, City, State, Zip Code, Phone, SSN, Username, Password, Confirm Password)
5. Click 'Register' button
6. Verify user is redirected to Account Overview page
7. Verify success message is displayed
8. Verify username is displayed in logged-in section
9. Verify at least one default account is created
10. Verify account number and balance are displayed

## Prerequisites
- Java JDK 11 or higher installed
- Maven 3.6+ installed
- Chrome/Firefox/Edge browser installed
- Internet connection (for first-time dependency download)

## Configuration

### config.properties
```properties
# Application URL
base.url=https://parabank.parasoft.com/parabank/index.htm

# Browser Configuration
browser=chrome
browser.headless=false
browser.maximize=true

# Timeouts
implicit.wait=10
explicit.wait=20
page.load.timeout=30

# Screenshot Configuration
screenshot.on.failure=true
screenshot.folder=test-output/screenshots

# Logging
log.level=INFO
```

## How to Run Tests

### 1. Clone the Repository
```bash
git clone <repository-url>
cd parabank-test-automation
```

### 2. Install Dependencies
```bash
mvn clean install -DskipTests
```

### 3. Run All Tests
```bash
mvn clean test
```

### 4. Run Specific Test by Tag
```bash
# Run only SCRUM-167 test case
mvn clean test -Dcucumber.filter.tags="@SCRUM-167"

# Run all regression tests
mvn clean test -Dcucumber.filter.tags="@Regression"

# Run high priority tests
mvn clean test -Dcucumber.filter.tags="@HighPriority"
```

### 5. Run Tests with Specific Browser
```bash
# Run with Chrome (default)
mvn clean test

# Run with Firefox
mvn clean test -Dbrowser=firefox

# Run with Edge
mvn clean test -Dbrowser=edge
```

### 6. Run in Headless Mode
Edit `config.properties`:
```properties
browser.headless=true
```

### 7. Run via TestNG XML
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

## Reports

### Cucumber HTML Report
After test execution, open:
```
test-output/cucumber-reports/cucumber-report.html
```

### Cucumber JSON Report
```
test-output/cucumber-reports/cucumber-report.json
```

### Screenshots
Screenshots are automatically captured on test failure and saved to:
```
test-output/screenshots/
```

### Logs
Detailed execution logs are available at:
```
test-output/logs/automation.log
```

## Page Object Model (POM) Structure

### BasePage.java
- Common methods for all pages
- WebDriver and WebDriverWait initialization
- Logging utilities

### HomePage.java
- Methods for ParaBank homepage interactions
- Click Register link
- Login functionality
- Logo verification

### RegistrationPage.java
- Complete registration form interaction methods
- Field presence verification
- Data entry methods for all registration fields
- Form submission

### AccountOverviewPage.java
- Account overview page verification
- Success message validation
- Username display verification
- Account creation verification
- Account details retrieval

## Step Definitions

### UserRegistrationSteps.java
Contains all Cucumber step implementations:
- Background steps (Given)
- Action steps (When)
- Assertion steps (Then)
- Additional verification steps (And)

### Hooks.java
- **@Before**: Initialize WebDriver before each scenario
- **@After**: Quit WebDriver and capture screenshots after each scenario
- **@AfterStep**: Capture screenshots on step failure

## Utilities

### DriverManager.java
- WebDriver initialization for multiple browsers
- Browser configuration with ChromeOptions, FirefoxOptions, etc.
- Thread-safe WebDriver management
- Automatic driver management using WebDriverManager

### ConfigReader.java
- Read configuration from config.properties
- Centralized configuration management
- Utility methods for common config values

## Best Practices Implemented

✅ **Page Object Model (POM)** - Separation of page elements and test logic  
✅ **Behavior-Driven Development (BDD)** - Gherkin scenarios for business readability  
✅ **Data-Driven Testing** - Scenario Outlines with Examples  
✅ **Logging** - Comprehensive logging using Log4j2  
✅ **Screenshot Capture** - Automatic screenshots on failure  
✅ **Assertions** - TestNG assertions for validation  
✅ **Waits** - Explicit and implicit waits for synchronization  
✅ **Configuration Management** - Externalized configuration  
✅ **Reporting** - Multiple report formats (HTML, JSON, XML)  
✅ **Clean Code** - Meaningful names, comments, and documentation  

## Troubleshooting

### Issue: WebDriver not found
**Solution**: WebDriverManager automatically downloads drivers. Ensure internet connection is available on first run.

### Issue: Test fails with "Element not found"
**Solution**: Increase wait times in config.properties:
```properties
implicit.wait=15
explicit.wait=30
```

### Issue: Browser not launching
**Solution**: 
- Verify browser is installed
- Check browser version compatibility
- Try running in headless mode

### Issue: Port already in use
**Solution**: Close all browser instances and try again.

## Future Enhancements

- [ ] Implement ExtentReports for advanced reporting
- [ ] Add negative test scenarios (invalid data, mismatched passwords)
- [ ] Implement parallel test execution
- [ ] Add API test integration for data cleanup
- [ ] Implement cross-browser testing matrix
- [ ] Add Allure reporting
- [ ] Implement CI/CD pipeline integration (Jenkins/GitHub Actions)
- [ ] Add Docker support for containerized execution

## Contributors
- Automation Team

## License
This project is for educational and testing purposes.

## Contact
For issues or questions, please create a JIRA ticket or contact the automation team.

---

**Last Updated**: 2024-01-01  
**Version**: 1.0  
**JIRA**: SCRUM-166, SCRUM-167
