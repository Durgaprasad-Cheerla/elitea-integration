# 🚀 Quick Start Guide - SCRUM-167 Test Automation

## ⚡ 5-Minute Quick Setup & Execution

### Prerequisites Check
```bash
# Verify Java installation
java -version
# Expected: Java 11 or higher

# Verify Maven installation
mvn -version
# Expected: Maven 3.6 or higher
```

---

## 🎯 Quick Execution Steps

### Step 1: Get the Code
```bash
# Clone and checkout branch
git clone <repository-url>
cd <repository-name>
git checkout SCRUM-167_TC001-Verify-Successful-User-Registration
```

### Step 2: Install Dependencies
```bash
# Download all required dependencies
mvn clean install -DskipTests
```

### Step 3: Run Tests
```bash
# Execute all tests
mvn clean test
```

**That's it!** 🎉 Your tests are running!

---

## 📊 View Results

### Console Output
Watch real-time test execution in your terminal with detailed logs.

### HTML Report
```bash
# After test completion, open:
test-output/cucumber-reports/cucumber-report.html
```

### Screenshots
```bash
# Find screenshots in:
test-output/screenshots/
```

---

## 🎨 Common Commands

### Run Specific Tests
```bash
# Run only this test case
mvn test -Dcucumber.filter.tags="@SCRUM-167"

# Run positive tests only
mvn test -Dcucumber.filter.tags="@Positive"

# Run high priority tests
mvn test -Dcucumber.filter.tags="@HighPriority"
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
mvn test -Dbrowser.headless=true
```

### Clean & Rebuild
```bash
# Clean all build artifacts and re-run
mvn clean test

# Force update dependencies
mvn clean install -U
```

---

## 🔧 Quick Configuration

### Change Application URL
Edit: `src/test/resources/config.properties`
```properties
base.url=https://parabank.parasoft.com/parabank/index.htm
```

### Change Browser
Edit: `src/test/resources/config.properties`
```properties
browser=chrome  # Options: chrome, firefox, edge, safari
```

### Enable/Disable Screenshots
Edit: `src/test/resources/config.properties`
```properties
screenshot.on.failure=true  # true or false
```

---

## 🐛 Quick Troubleshooting

### ❌ Problem: Tests not running
**Solution:**
```bash
# Check Java and Maven versions
java -version
mvn -version

# Reinstall dependencies
mvn clean install -DskipTests
```

### ❌ Problem: Browser not launching
**Solution:**
- Ensure Chrome/Firefox/Edge is installed
- Check internet connection (for driver download)
- Try: `mvn clean install -DskipTests`

### ❌ Problem: ParaBank URL not accessible
**Solution:**
- Check your internet connection
- Verify URL in `config.properties`
- Try accessing manually: https://parabank.parasoft.com/parabank/index.htm

### ❌ Problem: Tests fail with timeout
**Solution:**
Edit `src/test/resources/config.properties`:
```properties
explicit.wait=30  # Increase from 20 to 30
page.load.timeout=60  # Increase from 30 to 60
```

---

## 📁 Project Structure (Quick Reference)

```
📦 parabank-automation
├── 📂 src/test/java/com/parabank/
│   ├── 📂 pages/              # Page Object Models
│   ├── 📂 stepdefinitions/    # Cucumber Step Definitions
│   ├── 📂 runners/            # Test Runners
│   └── 📂 utils/              # Utilities (Config, Driver)
├── 📂 src/test/resources/
│   ├── 📂 features/           # Cucumber Feature Files
│   ├── 📄 config.properties   # Configuration
│   └── 📄 log4j2.xml          # Logging Config
├── 📄 pom.xml                 # Maven Dependencies
├── 📄 testng.xml              # TestNG Configuration
└── 📄 README_SCRUM-167.md     # Detailed Documentation
```

---

## 🎓 What This Test Does

### Test Case: TC001 - User Registration
**Test Flow:**
1. ✅ Open ParaBank homepage
2. ✅ Click "Register" link
3. ✅ Fill registration form with valid data
4. ✅ Submit registration
5. ✅ Verify successful registration
6. ✅ Verify user is logged in
7. ✅ Verify default account is created

**Expected Result:**
- User is registered successfully
- Welcome message is displayed
- Default checking account is created
- User can see account details

---

## 📈 Execution Examples

### Example 1: First Time Setup
```bash
# Complete first-time setup
git checkout SCRUM-167_TC001-Verify-Successful-User-Registration
mvn clean install -DskipTests
mvn clean test
```

### Example 2: Daily Test Execution
```bash
# Quick daily run
mvn clean test -Dcucumber.filter.tags="@SCRUM-167"
```

### Example 3: CI/CD Pipeline
```bash
# Headless execution for CI/CD
mvn clean test -Dbrowser.headless=true -Dcucumber.filter.tags="@Regression"
```

---

## 🔗 Useful Links

- **Detailed Documentation:** [README_SCRUM-167.md](README_SCRUM-167.md)
- **JIRA Test Case:** SCRUM-167
- **JIRA User Story:** SCRUM-166
- **ParaBank App:** https://parabank.parasoft.com/parabank/index.htm

---

## 💡 Pro Tips

1. **Run tests in IntelliJ IDEA:**
   - Right-click on `TestRunner.java` → Run
   - Right-click on `UserRegistration.feature` → Run

2. **Debug a specific scenario:**
   - Add `@Debug` tag to scenario in feature file
   - Run: `mvn test -Dcucumber.filter.tags="@Debug"`

3. **Parallel Execution:**
   - Edit `TestRunner.java`: Change `parallel = true`
   - Edit `testng.xml`: Set `thread-count="3"`

4. **Generate Timestamp-based Username:**
   - Modify test data to use dynamic usernames
   - Prevents conflicts in multiple test runs

---

## ⏱️ Estimated Execution Time

- **Single Scenario (TC001):** ~30-45 seconds
- **All Scenarios (TC001 + TC001A):** ~2-3 minutes
- **Full Regression Suite:** ~5-10 minutes

---

## 🎯 Next Steps

1. ✅ Execute the tests successfully
2. ✅ Review the HTML reports
3. ✅ Check screenshots for failures
4. ✅ Integrate with your CI/CD pipeline
5. ✅ Extend tests for negative scenarios

---

## 📞 Support

Having issues? Check these resources:
1. **Detailed README:** [README_SCRUM-167.md](README_SCRUM-167.md)
2. **Troubleshooting Section:** See above
3. **Logs:** Check `test-output/logs/automation.log`
4. **JIRA:** Comment on SCRUM-167

---

**Happy Testing! 🚀**

*Generated for SCRUM-167 - TC001: Verify Successful User Registration with Valid Data*
