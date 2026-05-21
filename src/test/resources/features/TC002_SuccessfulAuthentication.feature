@regression @authentication @positive
Feature: TC002 - Verify Successful User Authentication Flow
  As a valid Edward Jones user
  I want to successfully log in to my account
  So that I can access the dashboard and my account information

  Background:
    Given the user has valid Edward Jones account credentials
    And the Edward Jones login page is accessible
    And the user is not currently logged in
    And the network connection is stable

  @smoke @tc002
  Scenario: Successful login with valid credentials
    When the user navigates to the Edward Jones login page
    And the user enters valid username "testuser@edwardjones.com" in the Username field
    And the user enters valid password "ValidPass123!" in the Password field
    And the user clicks the Login button
    Then a loading indicator should be displayed during authentication
    And the authentication should complete within 2 seconds
    And the user should be redirected to the dashboard page within 2 seconds
    And a secure session should be established
    And the dashboard page should load successfully with user-specific data
    And the URL should change from login page to dashboard

  @tc002 @security
  Scenario: Verify credentials are submitted securely via HTTPS
    When the user navigates to the Edward Jones login page
    And the user enters valid username "testuser@edwardjones.com" in the Username field
    And the user enters valid password "ValidPass123!" in the Password field
    And the user clicks the Login button
    Then the credentials should be submitted via HTTPS POST request
    And the password should not be visible in the network request

  @tc002 @session
  Scenario: Verify session establishment after successful login
    When the user navigates to the Edward Jones login page
    And the user performs login with username "testuser@edwardjones.com" and password "ValidPass123!"
    Then a secure session token should be present in browser storage
    And the session should have appropriate expiration time

  @tc002 @ui-feedback
  Scenario: Verify loading indicator during authentication
    When the user navigates to the Edward Jones login page
    And the user enters valid username "testuser@edwardjones.com" in the Username field
    And the user enters valid password "ValidPass123!" in the Password field
    And the user clicks the Login button
    Then a loading spinner or indicator should appear immediately
    And the Login button should be disabled during authentication
    And the loading indicator should disappear after authentication completes
