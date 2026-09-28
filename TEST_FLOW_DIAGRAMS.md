# SCRUM-191 Test Automation Flow Diagrams

## 1. Test Execution Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                      START TEST EXECUTION                        │
│                      mvn test                                    │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                   TestRunner.java                                │
│   - Cucumber JUnit Runner                                        │
│   - Loads feature files from src/test/resources/features        │
│   - Filter by tag: @SCRUM-191                                   │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│              Hooks.java - @Before                                │
│   1. Print scenario name                                         │
│   2. Initialize WebDriver via DriverManager                      │
│   3. Configure browser (Chrome/Firefox)                          │
│   4. Set window size, timeouts, clear cookies                    │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│     Feature: SCRUM-191_InvalidLoginErrorHandling.feature        │
│                                                                   │
│     Scenario: Verify generic error message for invalid creds    │
└────────────────────────────┬────────────────────────────────────┘
                             │
          ┌──────────────────┴──────────────────┐
          │                                      │
          ▼                                      ▼
┌─────────────────────────┐          ┌─────────────────────────┐
│  STEP 1: GIVEN          │          │  LoginErrorHandling     │
│  "user is on login page"│  ───────>│  Steps.java             │
└─────────────────────────┘          └─────────┬───────────────┘
                                               │
                                               ▼
                                     ┌─────────────────────────┐
                                     │  LoginPage.java         │
                                     │  navigateToLoginPage()  │
                                     │  - driver.get(url)      │
                                     │  - wait for page load   │
                                     └─────────┬───────────────┘
                                               │
                                               ▼
                                     ┌─────────────────────────┐
                                     │  DriverManager.getDriver│
                                     │  - Singleton instance   │
                                     │  - ChromeDriver/Firefox │
                                     └─────────────────────────┘

          ┌──────────────────────────────────────┐
          │                                      │
          ▼                                      ▼
┌─────────────────────────┐          ┌─────────────────────────┐
│  STEP 2: WHEN           │          │  LoginErrorHandling     │
│  "user enters invalid   │  ───────>│  Steps.java             │
│   User ID"              │          │  enterUserId(string)    │
└─────────────────────────┘          └─────────┬───────────────┘
                                               │
                                               ▼
                                     ┌─────────────────────────┐
                                     │  LoginPage.java         │
                                     │  enterUserId(userId)    │
                                     │  - wait.until(clickable)│
                                     │  - field.clear()        │
                                     │  - field.sendKeys()     │
                                     └─────────────────────────┘

          ┌──────────────────────────────────────┐
          │                                      │
          ▼                                      ▼
┌─────────────────────────┐          ┌─────────────────────────┐
│  STEP 3: AND            │          │  LoginErrorHandling     │
│  "user enters invalid   │  ───────>│  Steps.java             │
│   password"             │          │  enterPassword(string)  │
└─────────────────────────┘          └─────────┬───────────────┘
                                               │
                                               ▼
                                     ┌─────────────────────────┐
                                     │  LoginPage.java         │
                                     │  enterPassword(pwd)     │
                                     │  - wait.until(clickable)│
                                     │  - field.clear()        │
                                     │  - field.sendKeys()     │
                                     └─────────────────────────┘

          ┌──────────────────────────────────────┐
          │                                      │
          ▼                                      ▼
┌─────────────────────────┐          ┌─────────────────────────┐
│  STEP 4: AND            │          │  LoginErrorHandling     │
│  "user clicks Sign in   │  ───────>│  Steps.java             │
│   button"               │          │  clickSignInButton()    │
└─────────────────────────┘          └─────────┬───────────────┘
                                               │
                                               ▼
                                     ┌─────────────────────────┐
                                     │  LoginPage.java         │
                                     │  clickSignInButton()    │
                                     │  - wait.until(clickable)│
                                     │  - button.click()       │
                                     │  - wait 2s for response │
                                     └─────────────────────────┘

          ┌──────────────────────────────────────┐
          │                                      │
          ▼                                      ▼
┌─────────────────────────┐          ┌─────────────────────────┐
│  STEP 5: THEN           │          │  LoginErrorHandling     │
│  "error message should  │  ───────>│  Steps.java             │
│   be displayed"         │          │  - Assert error visible │
└─────────────────────────┘          └─────────┬───────────────┘
                                               │
                                               ▼
                                     ┌─────────────────────────┐
                                     │  LoginPage.java         │
                                     │  isErrorMessageDisplay()│
                                     │  getErrorMessageText()  │
                                     │  - Multiple locators    │
                                     │  - Robust error finding │
                                     └─────────────────────────┘

          ┌──────────────────────────────────────┐
          │                                      │
          ▼                                      ▼
┌─────────────────────────┐          ┌─────────────────────────┐
│  STEP 6-9: AND          │          │  LoginErrorHandling     │
│  Multiple validations:  │  ───────>│  Steps.java             │
│  - Generic error        │          │  - Multiple assertions  │
│  - No field disclosure  │          │  - String validations   │
│  - User on login page   │          │  - URL verification     │
│  - Fields accessible    │          │  - Element checks       │
└─────────────────────────┘          └─────────┬───────────────┘
                                               │
                                               ▼
                                     ┌─────────────────────────┐
                                     │  LoginPage.java         │
                                     │  - doesErrorContain()   │
                                     │  - isOnLoginPage()      │
                                     │  - isFieldAccessible()  │
                                     └─────────────────────────┘

                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│              Hooks.java - @After                                 │
│   1. Check if scenario failed                                    │
│   2. If failed: Capture screenshot                               │
│   3. Attach screenshot to Cucumber report                        │
│   4. Quit WebDriver (driver.quit())                              │
│   5. Print scenario completion message                           │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    GENERATE REPORTS                              │
│   - HTML Report: target/cucumber-reports/cucumber.html          │
│   - JSON Report: target/cucumber-reports/cucumber.json          │
│   - JUnit XML: target/cucumber-reports/cucumber.xml             │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                     TEST EXECUTION COMPLETE                      │
│                     View Reports in Browser                      │
└─────────────────────────────────────────────────────────────────┘
```

---

## 2. Page Object Model (POM) Architecture

```
┌────────────────────────────────────────────────────────────────┐
│                    TEST LAYER (Feature Files)                   │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  Gherkin Scenarios (Business Language)                   │  │
│  │  - Feature: Login Error Handling                         │  │
│  │  - Scenario: Invalid credentials                         │  │
│  │  - Given/When/Then/And steps                             │  │
│  └──────────────────────────────────────────────────────────┘  │
└────────────────────────────┬───────────────────────────────────┘
                             │
                             ▼
┌────────────────────────────────────────────────────────────────┐
│               GLUE LAYER (Step Definitions)                     │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  LoginErrorHandlingSteps.java                            │  │
│  │  - @Given methods                                        │  │
│  │  - @When methods                                         │  │
│  │  - @Then methods                                         │  │
│  │  - @And methods                                          │  │
│  │  - Assert statements                                     │  │
│  │                                                           │  │
│  │  Responsibilities:                                        │  │
│  │  • Map Gherkin steps to Java code                        │  │
│  │  • Call Page Object methods                              │  │
│  │  • Perform assertions                                    │  │
│  │  • No direct WebDriver/locator access                    │  │
│  └──────────────────────────────────────────────────────────┘  │
└────────────────────────────┬───────────────────────────────────┘
                             │
                             ▼
┌────────────────────────────────────────────────────────────────┐
│             PAGE OBJECT LAYER (Page Classes)                    │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  LoginPage.java                                          │  │
│  │                                                           │  │
│  │  Web Elements:                                            │  │
│  │  ├─ @FindBy(data-testid) userIdField                    │  │
│  │  ├─ @FindBy(xpath) passwordField                        │  │
│  │  ├─ @FindBy(xpath) signInButton                         │  │
│  │  ├─ @FindBy(id) pageHeader                              │  │
│  │  └─ By genericErrorMessageLocator                       │  │
│  │                                                           │  │
│  │  Action Methods:                                          │  │
│  │  ├─ navigateToLoginPage(url)                            │  │
│  │  ├─ enterUserId(userId)                                 │  │
│  │  ├─ enterPassword(password)                             │  │
│  │  ├─ clickSignInButton()                                 │  │
│  │  ├─ getErrorMessageText()                               │  │
│  │  ├─ isErrorMessageDisplayed()                           │  │
│  │  ├─ isUserIdFieldAccessible()                           │  │
│  │  └─ isOnLoginPage(url)                                  │  │
│  │                                                           │  │
│  │  Responsibilities:                                        │  │
│  │  • Encapsulate web elements                              │  │
│  │  • Provide action methods                                │  │
│  │  • Handle waits internally                               │  │
│  │  • Return data to step definitions                       │  │
│  └──────────────────────────────────────────────────────────┘  │
└────────────────────────────┬───────────────────────────────────┘
                             │
                             ▼
┌────────────────────────────────────────────────────────────────┐
│                UTILITY LAYER (Support Classes)                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  DriverManager.java                                      │  │
│  │  • Singleton WebDriver instance                          │  │
│  │  • Browser initialization                                │  │
│  │  • Configuration management                              │  │
│  └──────────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  ConfigReader.java                                       │  │
│  │  • Read config.properties                                │  │
│  │  • Provide configuration values                          │  │
│  └──────────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  Hooks.java                                              │  │
│  │  • @Before: Setup WebDriver                              │  │
│  │  • @After: Teardown + Screenshots                        │  │
│  └──────────────────────────────────────────────────────────┘  │
└────────────────────────────┬───────────────────────────────────┘
                             │
                             ▼
┌────────────────────────────────────────────────────────────────┐
│                  SELENIUM WEBDRIVER LAYER                       │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  • ChromeDriver / FirefoxDriver                          │  │
│  │  • WebDriverManager (auto driver setup)                  │  │
│  │  • Browser automation                                    │  │
│  │  • Element interactions                                  │  │
│  └──────────────────────────────────────────────────────────┘  │
└────────────────────────────┬───────────────────────────────────┘
                             │
                             ▼
┌────────────────────────────────────────────────────────────────┐
│               BROWSER (Chrome/Firefox)                          │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  • Renders web application                               │  │
│  │  • Executes JavaScript                                   │  │
│  │  • Edward Jones Login Page                               │  │
│  └──────────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────────┘
```

---

## 3. Locator Priority Strategy

```
┌────────────────────────────────────────────────────────────────┐
│               LOCATOR SELECTION STRATEGY                        │
└────────────────────────────────────────────────────────────────┘

                    ┌─────────────────────┐
                    │  Need to locate     │
                    │  a web element      │
                    └──────────┬──────────┘
                               │
                               ▼
              ┌────────────────────────────────┐
              │  Priority 1: data-testid       │
              │  Most Stable & Reliable        │
              ├────────────────────────────────┤
              │  @FindBy(css =                 │
              │   "[data-testid='element']")   │
              └────────┬───────────────────────┘
                       │
                       ├─ Found? ──> USE IT ✅
                       │
                       └─ Not Found?
                               │
                               ▼
              ┌────────────────────────────────┐
              │  Priority 2: ID Attribute      │
              │  Unique & Stable               │
              ├────────────────────────────────┤
              │  @FindBy(id = "element-id")    │
              └────────┬───────────────────────┘
                       │
                       ├─ Found? ──> USE IT ✅
                       │
                       └─ Not Found?
                               │
                               ▼
              ┌────────────────────────────────┐
              │  Priority 3: ARIA Attributes   │
              │  Accessibility-friendly        │
              ├────────────────────────────────┤
              │  @FindBy(css =                 │
              │   "[aria-label='element']")    │
              └────────┬───────────────────────┘
                       │
                       ├─ Found? ──> USE IT ✅
                       │
                       └─ Not Found?
                               │
                               ▼
              ┌────────────────────────────────┐
              │  Priority 4: CSS Selectors     │
              │  Stable Class Names            │
              ├────────────────────────────────┤
              │  @FindBy(css =                 │
              │   ".unique-class-name")        │
              └────────┬───────────────────────┘
                       │
                       ├─ Found? ──> USE IT ✅
                       │
                       └─ Not Found?
                               │
                               ▼
              ┌────────────────────────────────┐
              │  Priority 5: XPath             │
              │  Last Resort                   │
              ├────────────────────────────────┤
              │  @FindBy(xpath =               │
              │   "//label[text()='...']/...")│
              └────────┬───────────────────────┘
                       │
                       └──> USE IT ⚠️

⚠️  NEVER USE:
    ❌ Absolute XPath: /html/body/div[1]/div[2]/...
    ❌ Index-based: //div[3]/span[2]
    ❌ Generated IDs: id="auto-gen-123456"
```

---

## 4. Error Message Validation Flow

```
┌────────────────────────────────────────────────────────────────┐
│         ERROR MESSAGE VALIDATION WORKFLOW                       │
└────────────────────────────────────────────────────────────────┘

User enters invalid credentials ──> Click Sign In
                                         │
                                         ▼
                              ┌──────────────────┐
                              │  Wait 2 seconds  │
                              │  (System response│
                              │   time)          │
                              └─────────┬────────┘
                                        │
                                        ▼
                     ┌──────────────────────────────────┐
                     │  Check if error is displayed     │
                     │  isErrorMessageDisplayed()       │
                     └─────────┬────────────────────────┘
                               │
                    ┌──────────┴─────────────┐
                    ▼                        ▼
          ┌─────────────────┐      ┌─────────────────┐
          │  YES - Pass ✅  │      │   NO - Fail ❌  │
          └────────┬────────┘      └─────────────────┘
                   │
                   ▼
        ┌──────────────────────┐
        │  Get error message   │
        │  text content        │
        └──────────┬───────────┘
                   │
                   ▼
        ┌──────────────────────────────────┐
        │  VALIDATION 1:                   │
        │  Is error message NOT empty?     │
        └──────────┬───────────────────────┘
                   │
        ┌──────────┴─────────────┐
        ▼                        ▼
 ┌──────────┐          ┌──────────────┐
 │ Pass ✅  │          │  Fail ❌     │
 └────┬─────┘          └──────────────┘
      │
      ▼
┌───────────────────────────────────────┐
│  VALIDATION 2:                        │
│  Does error contain generic terms?    │
│  - "invalid"                          │
│  - "incorrect"                        │
│  - "credentials"                      │
│  - "user id or password"              │
└──────────┬────────────────────────────┘
           │
┌──────────┴─────────────┐
▼                        ▼
┌──────────┐    ┌──────────────┐
│ Pass ✅  │    │  Fail ❌     │
└────┬─────┘    └──────────────┘
     │
     ▼
┌──────────────────────────────────────────┐
│  VALIDATION 3 (Security Critical):       │
│  Error does NOT reveal specific field?   │
│                                           │
│  ❌ "Invalid User ID" (alone)            │
│  ❌ "Invalid Password" (alone)           │
│  ✅ "Invalid User ID or Password"        │
│  ✅ "Credentials are incorrect"          │
└──────────┬───────────────────────────────┘
           │
┌──────────┴─────────────┐
▼                        ▼
┌──────────┐    ┌──────────────┐
│ Pass ✅  │    │  Fail ❌     │
└────┬─────┘    └──────────────┘
     │
     ▼
┌──────────────────────────────────┐
│  VALIDATION 4:                   │
│  User remains on login page?     │
│  Check URL contains "oa-login"   │
└──────────┬───────────────────────┘
           │
┌──────────┴─────────────┐
▼                        ▼
┌──────────┐    ┌──────────────┐
│ Pass ✅  │    │  Fail ❌     │
└────┬─────┘    └──────────────┘
     │
     ▼
┌──────────────────────────────────┐
│  VALIDATION 5:                   │
│  User ID field accessible?       │
│  - isDisplayed()                 │
│  - isEnabled()                   │
└──────────┬───────────────────────┘
           │
┌──────────┴─────────────┐
▼                        ▼
┌──────────┐    ┌──────────────┐
│ Pass ✅  │    │  Fail ❌     │
└────┬─────┘    └──────────────┘
     │
     ▼
┌──────────────────────────────────┐
│  VALIDATION 6:                   │
│  Password field accessible?      │
│  - isDisplayed()                 │
│  - isEnabled()                   │
└──────────┬───────────────────────┘
           │
┌──────────┴─────────────┐
▼                        ▼
┌──────────────┐  ┌──────────────┐
│ ALL PASS ✅  │  │  FAIL ❌     │
│ Scenario OK  │  │  See Report  │
└──────────────┘  └──────────────┘
```

---

## 5. WebDriver Lifecycle (Singleton Pattern)

```
┌────────────────────────────────────────────────────────────────┐
│              WEBDRIVER LIFECYCLE MANAGEMENT                     │
└────────────────────────────────────────────────────────────────┘

Test Execution Starts
         │
         ▼
┌────────────────────┐
│  Hooks.java        │
│  @Before Method    │
└─────────┬──────────┘
          │
          ▼
┌─────────────────────────────────────────────┐
│  DriverManager.getDriver()                  │
│  (First call - driver is null)              │
└─────────┬───────────────────────────────────┘
          │
          ▼
┌─────────────────────────────────────────────┐
│  Check: if (driver == null)                 │
└─────────┬───────────────────────────────────┘
          │
          ├─ Yes (first time)
          │         │
          │         ▼
          │  ┌──────────────────────────────┐
          │  │  initializeDriver()          │
          │  └───────────┬──────────────────┘
          │              │
          │              ▼
          │  ┌──────────────────────────────┐
          │  │  Read browser config         │
          │  │  - System.getProperty()      │
          │  │  - chrome (default)          │
          │  │  - firefox                   │
          │  └───────────┬──────────────────┘
          │              │
          │              ▼
          │  ┌──────────────────────────────┐
          │  │  WebDriverManager setup      │
          │  │  - Auto-download driver      │
          │  │  - Version compatibility     │
          │  └───────────┬──────────────────┘
          │              │
          │              ▼
          │  ┌──────────────────────────────┐
          │  │  Set browser options         │
          │  │  - ChromeOptions             │
          │  │  - Headless mode?            │
          │  │  - Window size               │
          │  │  - Security flags            │
          │  └───────────┬──────────────────┘
          │              │
          │              ▼
          │  ┌──────────────────────────────┐
          │  │  new ChromeDriver(options)   │
          │  │  OR new FirefoxDriver()      │
          │  └───────────┬──────────────────┘
          │              │
          │              ▼
          │  ┌──────────────────────────────┐
          │  │  Configure driver            │
          │  │  - maximize window           │
          │  │  - set timeouts              │
          │  │  - delete cookies            │
          │  └───────────┬──────────────────┘
          │              │
          │              ▼
          │  ┌──────────────────────────────┐
          │  │  Store in static variable    │
          │  │  private static driver = ... │
          │  └───────────┬──────────────────┘
          │              │
          └──────────────┴──> Return driver instance
                               │
          ┌────────────────────┘
          │
          ├─ No (already initialized)
          │         │
          │         ▼
          │  ┌──────────────────────────────┐
          │  │  Return existing driver      │
          │  │  (Singleton pattern)         │
          │  └───────────┬──────────────────┘
          │              │
          └──────────────┴──> Return driver instance
                               │
                               ▼
                    ┌──────────────────────┐
                    │  Test uses driver    │
                    │  for automation      │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │  Test scenario ends  │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │  Hooks.java          │
                    │  @After Method       │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────────────┐
                    │  Scenario failed?            │
                    └──────┬──────────────┬────────┘
                           │              │
                      YES  │              │  NO
                           ▼              ▼
                ┌──────────────────┐  ┌────────────┐
                │  Capture         │  │  Skip      │
                │  Screenshot      │  │  Screenshot│
                └──────┬───────────┘  └─────┬──────┘
                       │                    │
                       └─────────┬──────────┘
                                 │
                                 ▼
                      ┌──────────────────────┐
                      │  DriverManager       │
                      │  .quitDriver()       │
                      └──────────┬───────────┘
                                 │
                                 ▼
                      ┌──────────────────────┐
                      │  driver.quit()       │
                      │  driver = null       │
                      └──────────┬───────────┘
                                 │
                                 ▼
                      ┌──────────────────────┐
                      │  Browser closes      │
                      │  Resources freed     │
                      └──────────────────────┘

Next Scenario?
  │
  └──> Cycle repeats (new driver instance created)
```

---

## 6. Data-Driven Testing Flow

```
┌────────────────────────────────────────────────────────────────┐
│          DATA-DRIVEN TESTING WITH SCENARIO OUTLINE             │
└────────────────────────────────────────────────────────────────┘

Feature File:
┌─────────────────────────────────────────────────────────────┐
│  Scenario Outline: Invalid credential combinations          │
│    When user enters invalid User ID "<userId>"              │
│    And user enters invalid password "<password>"            │
│    And user clicks Sign in button                           │
│    Then error message should be displayed                   │
│                                                              │
│  Examples:                                                   │
│    | userId         | password     |                        │
│    | InvalidUser999 | WrongPass123 |  ◄── Iteration 1      │
│    | TestUser123    | InvalidPass  |  ◄── Iteration 2      │
│    | ''             | WrongPass123 |  ◄── Iteration 3      │
│    | InvalidUser999 | ''           |  ◄── Iteration 4      │
└─────────────────────────────────────────────────────────────┘
                          │
                          ▼
        ┌─────────────────────────────────────┐
        │  Cucumber Reads Examples Table      │
        └─────────────────┬───────────────────┘
                          │
          ┌───────────────┼───────────────┐
          │               │               │
          ▼               ▼               ▼
    ┌─────────┐     ┌─────────┐     ┌─────────┐
    │ Row 1   │     │ Row 2   │ ... │ Row 4   │
    └────┬────┘     └────┬────┘     └────┬────┘
         │               │               │
         ▼               ▼               ▼
    Execute          Execute         Execute
    Scenario         Scenario        Scenario
    with             with            with
    data 1           data 2          data 4

Each execution:
    1. @Before hook (new driver)
    2. Navigate to page
    3. Enter userId from table
    4. Enter password from table
    5. Click Sign in
    6. Validate error
    7. @After hook (quit driver)

Result:
    ✅ Iteration 1: PASSED
    ✅ Iteration 2: PASSED
    ✅ Iteration 3: PASSED
    ✅ Iteration 4: PASSED
    
    Total: 4 scenarios executed
    Coverage: Multiple invalid credential combinations
```

---

**Last Updated:** 2026-09-28  
**Framework:** Selenium + Cucumber + JUnit + Maven  
**Design Pattern:** Page Object Model (POM)  
**Status:** Production Ready ✅
