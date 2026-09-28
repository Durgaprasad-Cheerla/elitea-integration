@SCRUM-191 @Login @ErrorHandling @Security
Feature: Verify Error Handling for Invalid Login Credentials
  As a security-conscious system
  I want to display generic error messages for invalid login attempts
  So that potential attackers cannot determine which credential is incorrect

  Background:
    Given the user is on the Edward Jones Online Access login page

  @TC-SCRUM-189-003 @NegativeTesting
  Scenario: Verify generic error message is displayed for invalid credentials
    When the user enters invalid User ID "InvalidUser999"
    And the user enters invalid password "WrongPass123"
    And the user clicks the Sign in button
    Then a clear error message should be displayed indicating authentication failure
    And the error message should be generic and not reveal specific field information
    And the error message should NOT contain "Invalid User ID" separately
    And the error message should NOT contain "Invalid Password" separately
    And the user should remain on the login page
    And the User ID field should be accessible for retry
    And the Password field should be accessible for retry

  @TC-SCRUM-189-003 @DataDriven
  Scenario Outline: Verify error handling for various invalid credential combinations
    When the user enters invalid User ID "<userId>"
    And the user enters invalid password "<password>"
    And the user clicks the Sign in button
    Then a clear error message should be displayed indicating authentication failure
    And the error message should be generic and not reveal specific field information
    And the user should remain on the login page

    Examples:
      | userId          | password      |
      | InvalidUser999  | WrongPass123  |
      | TestUser123     | InvalidPass   |
      | ''              | WrongPass123  |
      | InvalidUser999  | ''            |
