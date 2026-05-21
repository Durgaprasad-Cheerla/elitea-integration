package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber Hooks class for setup and teardown operations
 * Handles WebDriver lifecycle and screenshot capture for failed scenarios
 * 
 * @author QA Automation Team
 * @version 1.0
 */
public class Hooks {
    
    private WebDriver driver;
    
    /**
     * Before hook - executes before each scenario
     * Initializes WebDriver and performs setup
     */
    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        System.out.println("=== Test Execution Started ===");
    }
    
    /**
     * After hook - executes after each scenario
     * Captures screenshot for failed scenarios and quits WebDriver
     * 
     * @param scenario Cucumber scenario object
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            // Capture screenshot if scenario failed
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed Scenario Screenshot");
                System.out.println("Screenshot captured for failed scenario: " + scenario.getName());
            }
            
            System.out.println("=== Test Execution Completed ===");
            System.out.println("Scenario: " + scenario.getName());
            System.out.println("Status: " + scenario.getStatus());
            
        } catch (Exception e) {
            System.err.println("Error during teardown: " + e.getMessage());
        } finally {
            // Quit WebDriver
            DriverManager.quitDriver();
        }
    }
}
