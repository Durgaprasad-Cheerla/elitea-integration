@regression @ui @login-form
Feature: TC001 - Verify Login Form Display and Layout Elements
  As a user
  I want to see a properly formatted login page
  So that I can access the Edward Jones login form

  Background:
    Given the Edward Jones login page URL is available
    And the user is not currently logged in
    And the user has access to a desktop browser

  @smoke @tc001
  Scenario: Verify all login form elements are displayed correctly
    When the user navigates to the Edward Jones login page
    And the page fully loads
    Then the Username or Email input field should be visible
    And the Username or Email field should have placeholder text "Enter your email"
    And the Username or Email field type attribute should be "text" or "email"
    And the Username or Email field should be marked as required
    And the Password input field should be visible
    And the Password field should have placeholder text "Enter your password"
    And the Password field type attribute should be "password"
    And the Password field should be marked as required
    And the Login or Sign In button should be present and clearly labeled
    And the Login button should be in enabled state
    And the Edward Jones logo should be visible in the page header
    And the page should use official Edward Jones brand colors

  @tc001 @password-masking
  Scenario: Verify password field masking functionality
    When the user navigates to the Edward Jones login page
    And the user types "Test123" in the Password field
    Then the Password field characters should be masked with bullets or asterisks

  @tc001 @accessibility
  Scenario: Verify login button color contrast meets WCAG 2.1 AA requirements
    When the user navigates to the Edward Jones login page
    Then the Login button color contrast should meet WCAG 2.1 AA requirements
    And the contrast ratio should be at least 4.5:1 for normal text
    And the contrast ratio should be at least 3:1 for large text

  @tc001 @branding
  Scenario: Verify Edward Jones branding consistency
    When the user navigates to the Edward Jones login page
    Then the Edward Jones logo should be displayed in the header
    And the page should maintain consistent brand colors
    And the page typography should be consistent with Edward Jones branding
