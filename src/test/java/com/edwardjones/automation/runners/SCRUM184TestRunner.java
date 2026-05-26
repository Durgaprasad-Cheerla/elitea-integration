package com.edwardjones.automation.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Dedicated Test Runner for SCRUM-184
 * TC-SCRUM-183-001: Verify Successful User Authentication with Valid Credentials
 * 
 * This runner executes ONLY the test scenarios related to SCRUM-184
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 * 
 * Test Coverage:
 * - User Authentication with Valid Credentials
 * - Field Validation (Username, Password, Button State)
 * - Security Validation (HTTPS, Password Masking, Session Token)
 * - Performance Validation (3-second redirect requirement)
 * - Accessibility Validation (Keyboard Navigation)
 * - Session Persistence
 * 
 * Execution:
 * - Run this class as JUnit test
 * - Or via Maven: mvn clean test -Dtest=SCRUM184TestRunner
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    // Feature file for SCRUM-184
    features = "src/test/resources/features/SCRUM-184_UserAuthentication.feature",
    
    // Step definitions and hooks
    glue = {
        "com.edwardjones.automation.stepdefinitions",
        "com.edwardjones.automation.hooks"
    },
    
    // Comprehensive reporting
    plugin = {
        "pretty",
        "html:target/cucumber-reports/SCRUM-184/cucumber-report.html",
        "json:target/cucumber-reports/SCRUM-184/cucumber.json",
        "junit:target/cucumber-reports/SCRUM-184/cucumber.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    
    // Execute all SCRUM-184 scenarios
    tags = "@SCRUM-184",
    
    // Clean console output
    monochrome = true,
    
    // Fail fast on undefined steps
    strict = true,
    
    // Execute tests (not dry run)
    dryRun = false,
    
    // Publish results to Cucumber Reports
    publish = true
)
public class SCRUM184TestRunner {
    // Empty class - Cucumber uses annotations for configuration
}
