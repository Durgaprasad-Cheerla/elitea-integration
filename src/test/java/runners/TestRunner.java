package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Test Runner for SCRUM-191: Invalid Login Error Handling
 * 
 * This class configures and executes Cucumber tests for the invalid login
 * error handling test scenarios.
 * 
 * Configuration:
 * - Features: Location of .feature files
 * - Glue: Package containing step definitions and hooks
 * - Tags: Filter scenarios by tags (e.g., @SCRUM-191)
 * - Plugin: Report generation (JSON, HTML, Pretty console output)
 * - Monochrome: Clean console output without color codes
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepdefinitions", "hooks"},
    tags = "@SCRUM-191",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml"
    },
    monochrome = true,
    dryRun = false
)
public class TestRunner {
    // This class will be empty - JUnit uses annotations to run tests
}
