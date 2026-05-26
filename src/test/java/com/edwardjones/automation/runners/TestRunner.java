package com.edwardjones.automation.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Cucumber Test Runner for SCRUM-184 User Authentication Tests
 * Configures Cucumber execution options including:
 * - Feature file location
 * - Step definitions package
 * - Report generation
 * - Tags for selective execution
 * - Test output formatting
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 * 
 * Execution:
 * Run this class as JUnit test to execute all authentication scenarios
 * 
 * Command Line:
 * mvn clean test -Dcucumber.filter.tags="@SCRUM-184"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    // Feature files location
    features = "src/test/resources/features",
    
    // Step definitions package
    glue = {
        "com.edwardjones.automation.stepdefinitions",
        "com.edwardjones.automation.hooks"
    },
    
    // Report plugins
    plugin = {
        "pretty",                                              // Console output
        "html:target/cucumber-reports/cucumber-html-report.html",  // HTML report
        "json:target/cucumber-reports/cucumber.json",          // JSON report
        "junit:target/cucumber-reports/cucumber.xml",          // JUnit XML report
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"  // Extent report
    },
    
    // Tags for execution control
    // Run all scenarios in SCRUM-184 feature
    tags = "@SCRUM-184",
    
    // Display scenario names and steps in console
    monochrome = true,
    
    // Fail execution if there are undefined/pending steps
    strict = true,
    
    // Dry run (validate feature files and step definitions without executing)
    dryRun = false
)
public class TestRunner {
    // This class will remain empty
    // Cucumber uses the annotations to determine test execution
}
