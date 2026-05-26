# SCRUM-184: User Authentication Automation Tests

## 📋 Test Case Information

**Jira Ticket:** [SCRUM-184](https://durgaprasad675106.atlassian.net/browse/SCRUM-184)  
**Parent Story:** [SCRUM-183](https://durgaprasad675106.atlassian.net/browse/SCRUM-183)  
**Test Case ID:** TC-SCRUM-183-001  
**Title:** Verify Successful User Authentication with Valid Credentials  
**Priority:** High  
**Type:** Automated - Functional, Security, Performance, Accessibility

---

## 🎯 Test Objective

To verify that a registered Edward Jones user can successfully log in using valid username and password credentials, and is redirected to the authenticated dashboard within the required timeframe with proper session management and security controls in place.

---

## 📂 Project Structure

```
SCRUM-184_Verify-Successful-User-Authentication-Valid-Credentials/
├── src/
│   ├── main/java/com/edwardjones/automation/
│   │   ├── config/
│   │   │   └── ConfigurationManager.java          # Configuration handler
│   │   ├── driver/
│   │   │   └── DriverManager.java                 # WebDriver management
│   │   ├── pages/
│   │   │   ├── BasePage.java                      # Base page object
│   │   │   ├── LoginPage.java                     # Login page object
│   │   │   └── DashboardPage.java                 # Dashboard page object
│   │   ├── utils/
│   │   │   ├── SecurityPerformanceHelper.java     # Security & performance utilities
│   │   │   └── ScreenshotUtil.java                # Screenshot capture utility
│   │   └── context/
│   │       └── TestContext.java                   # Shared test context
│   │
│   └── test/
│       ├── java/com/edwardjones/automation/
│       │   ├── stepdefinitions/
│       │   │   ├── AuthenticationStepDefinitions.java       # Step definitions part 1
│       │   │   ├── AuthenticationStepDefinitionsPart2.java  # Step definitions part 2
│       │   │   └── AuthenticationStepDefinitionsPart3.java  # Step definitions part 3
│       │   ├── hooks/
│       │   │   └── CucumberHooks.java             # Before/After hooks
│       │   └── runners/
│       │       ├── TestRunner.java                # Main test runner
│       │       └── SCRUM184TestRunner.java        # Dedicated SCRUM-184 runner
│       │
│       └── resources/
│           ├── features/
│           │   └── SCRUM-184_UserAuthentication.feature    # Cucumber feature file
│           ├── config/
│           │   └── test.properties                # Test configuration
│           └── log4j2.xml                         # Logging configuration
│
├── pom.xml                                        # Maven dependencies
└── README.md                                      # This file
```

---

## 🛠️ Technology Stack

- **Language:** Java 11
- **Build Tool:** Maven
- **Testing Framework:** Cucumber BDD + JUnit
- **UI Automation:** Selenium WebDriver 4.15.0
- **Driver Management:** WebDriverManager 5.6.2
- **Design Pattern:** Page Object Model (POM)
- **Logging:** Log4j2
- **Reporting:** Cucumber HTML Reports, ExtentReports
- **Assertions:** JUnit Assert

---

## ✅ Test Coverage

### Acceptance Criteria Covered

| AC ID | Acceptance Criteria | Covered |
|-------|---------------------|---------|
| **AC-1** | User Successfully Authenticates with Valid Credentials | ✅ |
| **VAL-1** | Username field validation (mandatory, alphanumeric) | ✅ |
| **VAL-2** | Password field validation (mandatory, masked) | ✅ |
| **VAL-3** | Login button state (enabled only when fields populated) | ✅ |
| **SEC-1** | HTTPS transmission | ✅ |
| **SEC-2** | Password masking and secure transmission | ✅ |
| **SEC-3** | Session token creation and secure storage | ✅ |
| **PERF-1** | Authentication and redirect within 3 seconds | ✅ |
| **ACC-1** | Keyboard navigation (Tab key) | ✅ |
| **ACC-2** | Enter key functionality for form submission | ✅ |

### Test Scenarios Implemented

1. ✅ **Basic Login Page Load** - Verify login page elements
2. ✅ **Field Validation** - Username/password field validation
3. ✅ **Button State Validation** - Login button enabled/disabled states
4. ✅ **Keyboard Navigation** - Tab key navigation between fields
5. ✅ **Password Masking** - Verify password is masked
6. ✅ **Enter Key Submission** - Form submission using Enter key
7. ✅ **Complete Authentication Flow** - Full login with security & performance validation
8. ✅ **Session Persistence** - Session maintained across navigation
9. ✅ **Keyboard-Only Authentication** - Complete login using only keyboard
10. ✅ **Data-Driven Testing** - Multiple user credentials
11. ✅ **Cleanup** - Logout and session cleanup

---

## ⚙️ Prerequisites

### Software Requirements

- **Java JDK 11+** installed
- **Maven 3.6+** installed
- **Chrome/Firefox/Edge** browser installed
- **Git** installed (for version control)

### Environment Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd SCRUM-184_Verify-Successful-User-Authentication-Valid-Credentials
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

---

## 🚀 How to Run Tests

### Run All SCRUM-184 Tests

```bash
mvn clean test -Dtest=SCRUM184TestRunner
```

### Run Specific Scenarios by Tags

```bash
# Run only positive scenarios
mvn clean test -Dcucumber.filter.tags="@PositiveScenario"

# Run only security tests
mvn clean test -Dcucumber.filter.tags="@Security"

# Run only performance tests
mvn clean test -Dcucumber.filter.tags="@PERF-1"

# Run only accessibility tests
mvn clean test -Dcucumber.filter.tags="@ACC-1"
```

### Run with Specific Browser

Edit `src/test/resources/config/test.properties`:
```properties
browser.type=chrome    # or firefox, edge, safari
browser.headless=false # true for headless mode
```

### Run in Headless Mode

```bash
mvn clean test -Dbrowser.headless=true
```

---

## 📊 Test Reports

### Report Locations

After test execution, reports are generated in:

```
target/
├── cucumber-reports/
│   ├── cucumber-html-report.html    # Main Cucumber HTML report
│   ├── cucumber.json                # JSON report (for CI/CD)
│   └── cucumber.xml                 # JUnit XML report
│
├── screenshots/                     # Failure screenshots
│   └── FAILED_*.png
│
└── logs/
    ├── automation.log               # Complete execution log
    ├── errors.log                   # Error logs only
    └── test-execution.log           # Test-specific logs
```

### Open HTML Report

```bash
# Open Cucumber HTML Report
open target/cucumber-reports/cucumber-html-report.html

# Or on Windows
start target/cucumber-reports/cucumber-html-report.html

# Or on Linux
xdg-open target/cucumber-reports/cucumber-html-report.html
```

---

## 🔧 Configuration

### Test Data Configuration

Edit `src/test/resources/config/test.properties`:

```properties
# Application URLs
app.base.url=https://www.edwardjones.com
app.login.url=https://www.edwardjones.com/login
app.dashboard.url=https://www.edwardjones.com/dashboard

# Test User Credentials
test.user.username=testuser001
test.user.password=ValidPass123!

# Performance Thresholds
performance.authentication.max.seconds=3

# Browser Configuration
browser.type=chrome
browser.headless=false
browser.window.maximize=true

# Timeouts
implicit.wait.seconds=10
explicit.wait.seconds=20
page.load.timeout.seconds=30

# Reporting
screenshot.on.failure=true
screenshot.directory=target/screenshots
```

---

## 📝 Test Data

### Valid Test User Credentials

| Username | Password | Description |
|----------|----------|-------------|
| testuser001 | ValidPass123! | Primary test user |
| testuser002 | SecurePass456! | Secondary test user |
| testuser003 | StrongPass789! | Tertiary test user |

**Note:** Update credentials in `test.properties` before execution.

---

## 🐛 Troubleshooting

### Common Issues

#### 1. WebDriver Not Found
**Solution:** WebDriverManager automatically downloads drivers. Ensure internet connection.

#### 2. Browser Not Opening
**Solution:** Check browser is installed and browser.type in config matches installed browser.

#### 3. Element Not Found
**Solution:** Locators may need updating. Check LoginPage.java for locator strategies.

#### 4. Tests Failing on CI/CD
**Solution:** Run in headless mode: `browser.headless=true`

#### 5. Session Token Not Found
**Solution:** Selenium may not access HttpOnly cookies. Check logs for alternate validation.

---

## 📧 Contact & Support

**Automation Team**  
**Project:** Edward Jones User Authentication Tests  
**Jira:** [SCRUM-184](https://durgaprasad675106.atlassian.net/browse/SCRUM-184)

---

## 📜 License

Copyright © 2024 Edward Jones Automation Team. All rights reserved.

---

## 🔄 Version History

| Version | Date | Changes | Author |
|---------|------|---------|--------|
| 1.0.0 | 2024-01-01 | Initial automation code creation for SCRUM-184 | Automation Team |

---

## ✨ Key Features

- ✅ **Page Object Model (POM)** - Maintainable and scalable architecture
- ✅ **Cucumber BDD** - Behavior-driven scenarios in Gherkin syntax
- ✅ **Parallel Execution** - ThreadLocal driver management
- ✅ **Security Validation** - HTTPS, password masking, session tokens
- ✅ **Performance Metrics** - Authentication timing measurements
- ✅ **Accessibility Testing** - Keyboard navigation validation
- ✅ **Comprehensive Reporting** - HTML, JSON, XML reports with screenshots
- ✅ **Flexible Configuration** - Externalized test data and settings
- ✅ **Robust Logging** - Log4j2 with multiple appenders
- ✅ **Cross-Browser Support** - Chrome, Firefox, Edge, Safari
- ✅ **CI/CD Ready** - Maven build with JSON/XML reports

---

**🎉 Happy Testing! 🎉**
