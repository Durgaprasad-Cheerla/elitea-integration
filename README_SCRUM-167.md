# Test Automation for SCRUM-167
## TC001 - Verify Successful User Registration with Valid Data

### 📋 Overview
This automation test suite validates the ParaBank user registration functionality, ensuring users can successfully register and access their newly created account.

---

### 🎯 Test Coverage
**JIRA Story:** SCRUM-166 - ParaBank User Registration  
**Test Case ID:** SCRUM-167 / TC001  
**Priority:** High  
**Test Type:** Positive - Functional

### Acceptance Criteria Covered
✅ **AC1:** User can access the registration page from the homepage  
✅ **AC2:** Registration form includes all mandatory fields  
✅ **AC3:** Form validates required fields  
✅ **AC4:** System accepts valid registration data  
✅ **AC5:** User credentials are securely stored  
✅ **AC6:** User is redirected to account overview with welcome message  
✅ **AC7:** System creates a default checking account for new user

---

### 🏗️ Test Automation Architecture

#### Framework Stack
- **Language:** Java 11
- **Build Tool:** Maven
- **Test Framework:** TestNG
- **BDD Framework:** Cucumber (Gherkin)
- **WebDriver:** Selenium 4.41.0
- **Driver Management:** WebDriverManager 6.3.3
- **Logging:** Log4j2
- **Reporting:** Cucumber HTML/JSON/XML Reports

#### Design Pattern
- **Page Object Model (POM):** Separates page elements and actions from test logic
- **Dependency Injection:** PicoContainer for managing test dependencies
- **Configuration Management:** Properties file for environment configuration

---

### 📁 Project Structure

```
src/
├── test/
│   ├── java/
│   │   └── com/
│   │       └── parabank/
│   │           ├── pages/
│   │           │   ├── BasePage.java
│   │           │   ├── HomePage.java
│   │           │   ├── RegistrationPage.java
│   │           │   └── AccountOverviewPage.java
│   │           ├── stepdefinitions/
│   │           │   ├── Hooks.java
│   │           │   └── UserRegistrationSteps.java
│   │           ├── runners/
│   │           │   └── TestRunner.java
│   │           └── utils/
│   │               ├── ConfigReader.java
│   │               └── DriverManager.java
│   └── resources/
│       ├── features/
│       │   └── UserRegistration.feature
│       ├── config.properties
│       └── log4j2.xml
├── pom.xml
└── testng.xml
```

---

### 🧪 Test Scenarios

#### Scenario 1: TC001 - Verify successful user registration with valid data
**Preconditions:**
- ParaBank application is accessible and running
- Browser is open and user is on ParaBank homepage
- Test data username 'testuser123' does not exist in the system
- Database is in a stable state to accept new registrations

**Test Steps:**
1. Navigate to ParaBank homepage
2. Click on 'Register' link in the login panel
3. Verify the registration form is displayed with all required fields
4. Enter 'John' in the 'First Name' field
5. Enter 'Doe' in the 'Last Name' field
6. Enter '123 Main Street' in the 'Address' field
7. Enter 'New York' in the 'City' field
8. Enter 'NY' in the 'State' field
9. Enter '10001' in the 'Zip Code' field
10. Enter '555-1234' in the 'Phone' field
11. Enter '123-45-6789' in the 'SSN' field
12. Enter 'testuser123' in the 'Username' field
13. Enter 'Password@123' in the 'Password' field
14. Enter 'Password@123' in the 'Confirm' field
15. Click the 'Register' button
16. Wait for the system to process the registration

**Expected Results:**
- ✅ 'Register' link is visible and clickable
- ✅ Registration form displays with all required fields
- ✅ Data is entered successfully in each field without errors
- ✅ User is redirected to the Account Overview page
- ✅ Welcome message: "Your account was created successfully. You are now logged in."
- ✅ Username 'testuser123' is displayed in logged-in section
- ✅ At least one default checking account is created
- ✅ Account number and balance are displayed

**Test Data:**
```
First Name: John
Last Name: Doe
Address: 123 Main Street
City: New York
State: NY
Zip Code: 10001
Phone: 555-1234
SSN: 123-45-6789
Username: testuser123
Password: Password@123
Confirm Password: Password@123
```

#### Scenario 2: TC001A - Data-driven registration with multiple datasets
Tests user registration with multiple valid data combinations to ensure robustness.

---

### 🚀 Setup Instructions

#### Prerequisites
1. **Java Development Kit (JDK) 11 or higher**
   ```bash
   java -version
   ```

2. **Apache Maven 3.6 or higher**
   ```bash
   mvn -version
   ```

3. **Supported Browser:**
   - Google Chrome (latest)
   - Mozilla Firefox (latest)
   - Microsoft Edge (latest)
   - Safari (for macOS)

#### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```

2. **Checkout the Test Branch**
   ```bash
   git checkout SCRUM-167_TC001-Verify-Successful-User-Registration
   ```

3. **Install Dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

4. **Configure Test Environment**
   
   Edit `src/test/resources/config.properties`:
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
   ```

---

### ▶️ Execution Instructions

#### Run All Tests
```bash
mvn clean test
```

#### Run Specific Tag
```bash
# Run only SCRUM-167 test
mvn clean test -Dcucumber.filter.tags="@SCRUM-167"

# Run all User Registration tests
mvn clean test -Dcucumber.filter.tags="@UserRegistration"

# Run High Priority tests
mvn clean test -Dcucumber.filter.tags="@HighPriority"

# Run Positive tests only
mvn clean test -Dcucumber.filter.tags="@Positive"
```

#### Run with Specific Browser
```bash
# Chrome
mvn clean test -Dbrowser=chrome

# Firefox
mvn clean test -Dbrowser=firefox

# Edge
mvn clean test -Dbrowser=edge
```

#### Run in Headless Mode
```bash
mvn clean test -Dbrowser.headless=true
```

#### Run using TestNG XML
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

---

### 📊 Test Reports

#### Cucumber HTML Report
After test execution, find the reports at:
- **Location:** `test-output/cucumber-reports/cucumber-report.html`
- **Open in Browser:** Double-click the HTML file

#### Console Output
Real-time test execution logs are displayed in the console with color-coded status.

#### Screenshots
- **Success Screenshots:** `test-output/screenshots/*_PASSED_*.png`
- **Failure Screenshots:** `test-output/screenshots/*_FAILED_*.png`

#### Logs
- **Location:** Console output and Log4j2 configuration
- **Log Level:** Configurable via `log4j2.xml`

---

### 🔧 Configuration Files

#### 1. config.properties
Contains all environment and test configuration:
- Application URL
- Browser settings
- Timeout values
- Screenshot and report paths

#### 2. testng.xml
TestNG suite configuration:
- Test suite name
- Parallel execution settings
- Test classes to execute

#### 3. log4j2.xml
Logging configuration:
- Log levels (INFO, DEBUG, ERROR)
- Log format and output

---

### 📝 Key Features

#### 1. **Page Object Model (POM)**
- **BasePage.java:** Common methods and utilities
- **HomePage.java:** Home page elements and actions
- **RegistrationPage.java:** Registration form elements and methods
- **AccountOverviewPage.java:** Account overview page verifications

#### 2. **Step Definitions**
- **UserRegistrationSteps.java:** Implements all Gherkin steps
- Clear logging for each step
- Comprehensive assertions
- Meaningful error messages

#### 3. **Hooks**
- **Before Hook:** Browser initialization, test setup
- **After Hook:** Screenshot capture, browser cleanup
- **AfterStep Hook:** Screenshot on step failure

#### 4. **Utilities**
- **DriverManager:** WebDriver lifecycle management
- **ConfigReader:** Configuration property management
- Support for multiple browsers
- Automatic driver setup via WebDriverManager

#### 5. **BDD Feature Files**
- Gherkin syntax for business-readable tests
- Scenario Outline for data-driven testing
- Tags for test categorization and filtering

---

### 🐛 Troubleshooting

#### Issue: Browser driver not found
**Solution:** WebDriverManager automatically downloads drivers. Ensure internet connectivity.

#### Issue: Element not found
**Solution:** Check if timeouts are sufficient in `config.properties`. Increase `explicit.wait` if needed.

#### Issue: Tests failing with "StaleElementReferenceException"
**Solution:** Add explicit waits in page objects before interacting with elements.

#### Issue: Screenshots not generated
**Solution:** Verify `screenshot.folder` path in config.properties and ensure write permissions.

#### Issue: ParaBank application not accessible
**Solution:** Check if `base.url` is correct and the ParaBank website is up.

---

### 🔐 Test Data Management

**Important:** Each test run should use a unique username to avoid conflicts.

**Recommended Approach:**
- Use timestamp-based usernames: `testuser_<timestamp>`
- Implement test data cleanup after execution
- Use database rollback for test data isolation

---

### 📈 Continuous Integration

#### Jenkins Integration
```groovy
pipeline {
    stages {
        stage('Checkout') {
            steps {
                git branch: 'SCRUM-167_TC001-Verify-Successful-User-Registration',
                    url: '<repository-url>'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Publish Reports') {
            steps {
                cucumber reportTitle: 'Cucumber Report',
                         fileIncludePattern: '**/cucumber-report.json'
            }
        }
    }
}
```

---

### 👥 Team & Contacts

**Automation Team**  
**JIRA:** [SCRUM-167](https://your-jira-instance/browse/SCRUM-167)  
**Parent Story:** [SCRUM-166](https://your-jira-instance/browse/SCRUM-166)

---

### 📅 Version History

| Version | Date       | Author          | Changes                          |
|---------|------------|-----------------|----------------------------------|
| 1.0     | 2024-01-01 | Automation Team | Initial test automation creation |

---

### ✅ Best Practices Followed

1. ✅ Page Object Model design pattern
2. ✅ BDD with Cucumber for business-readable tests
3. ✅ Comprehensive logging with Log4j2
4. ✅ Explicit waits for element synchronization
5. ✅ Screenshot capture on failure
6. ✅ Externalized configuration
7. ✅ Multiple browser support
8. ✅ Parallel execution ready
9. ✅ CI/CD integration ready
10. ✅ Detailed inline documentation

---

### 📚 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [TestNG Documentation](https://testng.org/doc/documentation-main.html)
- [ParaBank Application](https://parabank.parasoft.com/parabank/index.htm)

---

**Note:** This test suite is designed for the ParaBank demo application. Ensure the application is accessible before running tests.
