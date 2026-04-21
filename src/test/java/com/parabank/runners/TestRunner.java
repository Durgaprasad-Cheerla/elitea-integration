package com.parabank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG Test Runner for Cucumber BDD Tests
 * Executes Cucumber feature files using TestNG framework
 * 
 * JIRA: SCRUM-166, SCRUM-167
 * Test Case: TC001 - Verify Successful User Registration with Valid Data
 * 
 * @author Automation Team
 * @version 1.0
 */
@CucumberOptions(
        // Path to feature files
        features = "src/test/resources/features",
        
        // Path to step definitions package
        glue = {"com.parabank.stepdefinitions"},
        
        // Tags to execute specific scenarios
        // Use @SCRUM-167 to run only this test case
        // Use @Regression to run all regression tests
        tags = "@SCRUM-167 or @UserRegistration",
        
        // Pretty console output
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/cucumber-report.html",
                "json:test-output/cucumber-reports/cucumber-report.json",
                "junit:test-output/cucumber-reports/cucumber-report.xml"
        },
        
        // Generate readable method names in reports
        monochrome = true,
        
        // Stop execution on first failure (set to false for complete test run)
        dryRun = false,
        
        // Publish report to Cucumber Reports
        publish = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    
    /**
     * Enable parallel execution at scenario level
     * Override this method to enable parallel test execution
     * 
     * @return Object[][] containing scenario data
     */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
