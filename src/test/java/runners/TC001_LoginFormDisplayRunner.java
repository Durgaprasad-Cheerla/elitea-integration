package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Test Runner for TC001 - Login Form Display and Layout Elements
 * Configures Cucumber options and executes feature files
 * 
 * @author QA Automation Team
 * @version 1.0
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/TC001_LoginFormDisplay.feature",
    glue = {"stepdefinitions", "utils"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/TC001_LoginFormDisplay.html",
        "json:target/cucumber-reports/TC001_LoginFormDisplay.json",
        "junit:target/cucumber-reports/TC001_LoginFormDisplay.xml"
    },
    monochrome = true,
    tags = "@tc001"
)
public class TC001_LoginFormDisplayRunner {
    // This class will be empty - Cucumber uses annotations to configure test execution
}
