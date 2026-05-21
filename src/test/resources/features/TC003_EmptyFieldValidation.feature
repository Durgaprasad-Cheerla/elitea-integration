@regression @validation @negative
Feature: TC003 - Verify Empty Field Validation
  As a user
  I want to see validation errors when I submit empty login fields
  So that I understand what information is required to login

  Background:
    Given the Edward Jones login page is accessible
    And the user is not currently logged in

  @tc003 @smoke
  Scenario: Verify validation error when both fields are empty
    When the user navigates to the Edward Jones login page
    And the user leaves both Username and Password fields empty
    And the user clicks the Login button
    Then an error message "Username/Email is required" should be displayed below the Username field in red
    And an error message "Password is required" should be displayed below the Password field in red
    And the form should not be submitted
    And no authentication request should be sent to the server

  @tc003
  Scenario: Verify validation error when only Username field is empty
    When the user navigates to the Edward Jones login page
    And the user leaves the Username field empty
    And the user enters "TestPassword123" in the Password field
    And the user clicks the Login button
    Then an error message "Username/Email is required" should be displayed below the Username field in red
    And no error message should be shown for the Password field
    And the form should not be submitted

  @tc003
  Scenario: Verify validation error when only Password field is empty
    When the user navigates to the Edward Jones login page
    And the user enters "testuser@test.com" in the Username field
    And the user leaves the Password field empty
    And the user clicks the Login button
    Then an error message "Password is required" should be displayed below the Password field in red
    And no error message should be shown for the Username field
    And the form should not be submitted

  @tc003 @error-display
  Scenario: Verify error message styling and position
    When the user navigates to the Edward Jones login page
    And the user leaves both fields empty
    And the user clicks the Login button
    Then all error messages should be inline below respective input fields
    And all error messages should be displayed in red text
    And error messages should be clearly visible to the user
