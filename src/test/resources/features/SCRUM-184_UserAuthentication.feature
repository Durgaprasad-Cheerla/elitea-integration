@SCRUM-184 @Authentication @Regression @High
Feature: SCRUM-184 - Verify Successful User Authentication with Valid Credentials
  As a registered Edward Jones user
  I want to log in to the system using my username and password
  So that I can access my authenticated dashboard and account features securely

  Background:
    Given the Edward Jones login page is accessible
    And the test user account exists with valid credentials
    And the user is not currently logged in

  @PositiveScenario @AC-1 @Functional
  Scenario: TC-SCRUM-183-001 - Successful authentication with valid credentials
    Given I navigate to the Edward Jones login page
    Then the login page should load successfully
    And the URL should use HTTPS protocol
    And the Username field should be visible
    And the Password field should be visible
    And the Login button should be visible

  @FieldValidation @VAL-3
  Scenario: Verify Login button is disabled when fields are empty
    Given I navigate to the Edward Jones login page
    Then the Login button should be disabled when both fields are empty

  @FieldInteraction @VAL-1
  Scenario: Verify Username field accepts input and displays as plain text
    Given I navigate to the Edward Jones login page
    When I click on the Username field
    Then the Username field should be active and ready for input
    When I enter username "testuser001"
    Then the username should display as plain text
    And the Username field should accept alphanumeric characters

  @KeyboardNavigation @ACC-1
  Scenario: Verify Tab key navigation from Username to Password field
    Given I navigate to the Edward Jones login page
    When I click on the Username field
    And I enter username "testuser001"
    And I press the Tab key
    Then focus should move to the Password field
    And the Password field should be active

  @PasswordMasking @VAL-2 @SEC-2
  Scenario: Verify password is masked when entered
    Given I navigate to the Edward Jones login page
    When I click on the Password field
    And I enter password "ValidPass123!"
    Then the password should be masked as dots or asterisks
    And the actual password text should not be visible

  @ButtonState @VAL-3
  Scenario: Verify Login button is enabled when both fields are populated
    Given I navigate to the Edward Jones login page
    When I enter username "testuser001"
    And I enter password "ValidPass123!"
    Then the Login button should be enabled
    And the Login button should appear clickable

  @EnterKeySubmission @ACC-2
  Scenario: Verify form submission using Enter key
    Given I navigate to the Edward Jones login page
    When I enter username "testuser001"
    And I enter password "ValidPass123!"
    And I press the Enter key
    Then the login form should be submitted
    And a loading indicator may appear

  @FullAuthentication @AC-1 @PERF-1 @SEC-1 @SEC-3
  Scenario: Complete authentication flow with security and performance validation
    Given I navigate to the Edward Jones login page
    And I start monitoring network traffic
    When I enter username "testuser001"
    And I enter password "ValidPass123!"
    And I start the authentication timer
    And I press the Enter key to submit the form
    Then I should be redirected to the dashboard within 3 seconds
    And the dashboard URL should be "https://www.edwardjones.com/dashboard"
    And the dashboard should display personalized user content
    And the authentication request should be visible in network logs
    And the authentication request should use HTTPS protocol
    And the password should not be visible in plain text in the request payload
    And a session token should be created
    And the session token should have HttpOnly flag set
    And the session token should have Secure flag set

  @SessionPersistence @SEC-3
  Scenario: Verify session is maintained across navigation
    Given I am logged in with valid credentials
    When I navigate to another authenticated page
    Then I should remain authenticated
    And no login prompt should appear
    And the session should be maintained

  @KeyboardAccessibility @ACC-1 @ACC-2
  Scenario: Verify complete keyboard-only navigation and authentication
    Given I navigate to the Edward Jones login page
    When I use only keyboard navigation with Tab and Enter keys
    And I enter username "testuser001" using keyboard
    And I press Tab to move to password field
    And I enter password "ValidPass123!" using keyboard
    And I press Enter to submit
    Then I should be successfully authenticated using only keyboard
    And all fields and buttons should be accessible via keyboard
    And I should be redirected to the dashboard

  @DataDriven @MultipleUsers
  Scenario Outline: Authenticate with multiple valid user credentials
    Given I navigate to the Edward Jones login page
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the Login button
    Then I should be successfully authenticated
    And I should be redirected to the dashboard
    And the dashboard should display user-specific content for "<username>"

    Examples:
      | username    | password       |
      | testuser001 | ValidPass123!  |
      | testuser002 | SecurePass456! |
      | testuser003 | StrongPass789! |

  @Cleanup
  Scenario: Cleanup - Logout user after test execution
    Given I am logged in with valid credentials
    When I logout from the application
    Then the user session should be terminated
    And the session token should be cleared
    And I should be redirected to the login page
