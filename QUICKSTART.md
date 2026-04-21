# Quick Start Guide - ParaBank Automation

## For Developers & QA Engineers

### 🚀 Quick Setup (5 minutes)

#### 1. Prerequisites Check
```bash
# Verify Java version (needs 11+)
java -version

# Verify Maven version (needs 3.6+)
mvn -version
```

#### 2. Clone and Setup
```bash
# Clone repository
git clone <repository-url>
cd elitea-integration

# Checkout automation branch
git checkout SCRUM-167_TC001-Verify-Successful-User-Registration

# Install dependencies
mvn clean install -DskipTests
```

#### 3. Run Your First Test
```bash
# Run all tests
mvn clean test

# OR run specific test case
mvn clean test -Dcucumber.filter.tags="@SCRUM-167"
```

#### 4. View Results
- Open: `test-output/cucumber-reports/cucumber-report.html` in browser
- Check screenshots: `test-output/screenshots/`
- View logs: `test-output/logs/automation.log`

---

## 📋 Common Commands

### Run Tests by Tag
```bash
# Run regression tests
mvn test -Dcucumber.filter.tags="@Regression"

# Run high priority tests
mvn test -Dcucumber.filter.tags="@HighPriority"

# Run specific feature
mvn test -Dcucumber.filter.tags="@UserRegistration"

# Run positive test cases only
mvn test -Dcucumber.filter.tags="@Positive"
```

### Run Tests with Different Browsers
Edit `src/test/resources/config.properties`:
```properties
browser=chrome    # Options: chrome, firefox, edge, safari
```

Or pass as parameter:
```bash
mvn test -Dbrowser=firefox
```

### Run in Headless Mode
Edit `config.properties`:
```properties
browser.headless=true
```

---

## 🧪 Test Scenarios Available

### TC001 - Successful User Registration
**Tag**: `@SCRUM-167`, `@TC001`  
**Type**: Positive functional test  
**Priority**: High  

**Covers**:
- ✅ Accessing registration page
- ✅ Filling all registration fields with valid data
- ✅ Successful account creation
- ✅ Welcome message verification
- ✅ Default account creation

**Test Data**:
```
Username: testuser123
Password: Password@123
First Name: John
Last Name: Doe
Address: 123 Main Street
City: New York
State: NY
Zip: 10001
Phone: 555-1234
SSN: 123-45-6789
```

### TC001A - Data Driven Registration Tests
**Tag**: `@TC001A`  
**Type**: Data-driven positive tests  
**Includes**: 3 different data sets

---

## 📁 Important Files

### Configuration
- **config.properties** - All test configurations
- **log4j2.xml** - Logging configuration
- **testng.xml** - TestNG suite setup

### Test Code
- **UserRegistration.feature** - BDD scenarios
- **UserRegistrationSteps.java** - Step implementations
- **RegistrationPage.java** - Page object for registration
- **TestRunner.java** - Test executor

---

## 🛠️ Troubleshooting

### Problem: Tests not running
**Solution**:
```bash
# Clean and rebuild
mvn clean compile test-compile
mvn test
```

### Problem: Browser not launching
**Solution**:
1. Check browser is installed
2. Update WebDriverManager:
```bash
mvn clean install -U
```

### Problem: Element not found errors
**Solution**: Increase wait times in `config.properties`:
```properties
implicit.wait=15
explicit.wait=30
```

### Problem: Port 4444 already in use
**Solution**: Kill all browser processes:
```bash
# Windows
taskkill /F /IM chrome.exe /T
taskkill /F /IM chromedriver.exe /T

# Mac/Linux
pkill chrome
pkill chromedriver
```

---

## 📊 Understanding Reports

### Cucumber HTML Report
- **Location**: `test-output/cucumber-reports/cucumber-report.html`
- **Contains**: Scenario results, steps status, execution time
- **Best for**: Quick visual overview

### Screenshots
- **Location**: `test-output/screenshots/`
- **Naming**: `ScenarioName_STATUS_Timestamp.png`
- **Captured**: On failure (configurable)

### Logs
- **Location**: `test-output/logs/automation.log`
- **Contains**: Detailed execution logs, debug info
- **Best for**: Debugging issues

---

## 🎯 Best Practices

### Before Running Tests
1. ✅ Ensure ParaBank is accessible: https://parabank.parasoft.com/parabank
2. ✅ Close all browser instances
3. ✅ Check test data doesn't already exist
4. ✅ Review configuration in config.properties

### After Test Execution
1. ✅ Review reports for failures
2. ✅ Check screenshots for visual issues
3. ✅ Review logs for errors
4. ✅ Clean up test data if needed

### For CI/CD Integration
```bash
# Run in headless mode
mvn clean test -Dbrowser.headless=true

# Generate reports
mvn site

# Skip tests during build
mvn clean install -DskipTests
```

---

## 💡 Tips for Test Development

### Adding New Test Scenarios
1. Write Gherkin scenario in `UserRegistration.feature`
2. Run test to get undefined step suggestions
3. Implement steps in `UserRegistrationSteps.java`
4. Add page methods in relevant Page Object class
5. Tag appropriately (@Regression, @Priority, etc.)

### Adding New Pages
1. Create new page class extending `BasePage`
2. Define page elements using `@FindBy`
3. Implement page-specific methods
4. Initialize in step definition class

### Debugging Tests
```java
// Add in step definition for debugging
logger.info("Debug: Current URL = " + driver.getCurrentUrl());
logger.info("Debug: Page source = " + driver.getPageSource());

// Take screenshot manually
((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
```

---

## 📞 Need Help?

- **JIRA Issues**: SCRUM-166, SCRUM-167
- **Documentation**: See README.md for complete details
- **Logs**: Check `test-output/logs/automation.log`
- **Team**: Contact QA Automation Team

---

## ✅ Checklist for Test Execution

- [ ] Java 11+ installed
- [ ] Maven 3.6+ installed
- [ ] Browser (Chrome/Firefox/Edge) installed
- [ ] Dependencies downloaded (`mvn clean install`)
- [ ] Configuration reviewed (`config.properties`)
- [ ] ParaBank application accessible
- [ ] Test branch checked out
- [ ] Previous browser instances closed

**Ready to run**: `mvn clean test`

---

*Last Updated*: 2024-01-01  
*JIRA*: SCRUM-167  
*Branch*: SCRUM-167_TC001-Verify-Successful-User-Registration
