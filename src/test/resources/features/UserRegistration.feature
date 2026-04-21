# Feature: User Registration
# JIRA ID: SCRUM-167
# Test Case ID: TC001
# Title: Verify Successful User Registration with Valid Data
# Description: This feature tests the user registration functionality of ParaBank application
# covering all acceptance criteria from SCRUM-166

@Regression @UserRegistration @SCRUM-167
Feature: User Registration
  As a new user
  I want to register for a ParaBank account
  So that I can access online banking services

  Background:
    Given the ParaBank application is accessible and running
    And the user is on ParaBank homepage

  @TC001 @Positive @HighPriority
  Scenario: TC001 - Verify successful user registration with valid data
    # AC1: User can access the registration page from the homepage
    When the user clicks on "Register" link in the login panel
    Then the registration form should be displayed
    
    # AC2: Registration form includes all required fields
    And the registration form should contain the following fields:
      | First Name      |
      | Last Name       |
      | Address         |
      | City            |
      | State           |
      | Zip Code        |
      | Phone           |
      | SSN             |
      | Username        |
      | Password        |
      | Confirm Password|
    
    # Test execution with valid data
    When the user enters the following registration details:
      | Field           | Value           |
      | First Name      | John            |
      | Last Name       | Doe             |
      | Address         | 123 Main Street |
      | City            | New York        |
      | State           | NY              |
      | Zip Code        | 10001           |
      | Phone           | 555-1234        |
      | SSN             | 123-45-6789     |
      | Username        | testuser123     |
      | Password        | Password@123    |
      | Confirm Password| Password@123    |
    
    And the user clicks on "Register" button
    
    # AC6: Upon successful registration, user is redirected to account overview page
    Then the user should be redirected to the "Account Overview" page
    
    # AC6: Welcome message is displayed
    And a success message "Your account was created successfully. You are now logged in." should be displayed
    
    # Verify user is logged in with correct username
    And the username "testuser123" should be displayed in the logged-in user section
    
    # AC7: System creates a default checking account for new user
    And at least one default checking account should be created
    And the account number and balance should be displayed for the newly created account

  @TC001A @Positive @DataDriven
  Scenario Outline: TC001A - Verify user registration with multiple valid data sets
    When the user clicks on "Register" link in the login panel
    And the user enters "<firstName>" in the "First Name" field
    And the user enters "<lastName>" in the "Last Name" field
    And the user enters "<address>" in the "Address" field
    And the user enters "<city>" in the "City" field
    And the user enters "<state>" in the "State" field
    And the user enters "<zipCode>" in the "Zip Code" field
    And the user enters "<phone>" in the "Phone" field
    And the user enters "<ssn>" in the "SSN" field
    And the user enters "<username>" in the "Username" field
    And the user enters "<password>" in the "Password" field
    And the user enters "<password>" in the "Confirm Password" field
    And the user clicks on "Register" button
    Then the user should be redirected to the "Account Overview" page
    And a success message "Your account was created successfully. You are now logged in." should be displayed

    Examples:
      | firstName | lastName | address         | city     | state | zipCode | phone    | ssn         | username    | password     |
      | John      | Doe      | 123 Main Street | New York | NY    | 10001   | 555-1234 | 123-45-6789 | testuser123 | Password@123 |
      | Jane      | Smith    | 456 Oak Avenue  | Boston   | MA    | 02101   | 555-5678 | 987-65-4321 | testuser456 | SecureP@ss1  |
      | Bob       | Johnson  | 789 Pine Road   | Chicago  | IL    | 60601   | 555-9012 | 456-78-9012 | testuser789 | MyP@ssw0rd   |
