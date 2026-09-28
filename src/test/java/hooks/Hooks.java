package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.DriverManager;

/**
 * Cucumber Hooks - Setup and Teardown
 * 
 * @Before hooks run before each scenario
 * @After hooks run after each scenario
 * 
 * Handles WebDriver lifecycle and screenshot capture on failure
 */
public class Hooks {
    
    /**
     * Setup method - executed before each scenario
     * Initializes WebDriver
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("========================================");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("========================================");
        
        // Initialize driver
        DriverManager.getDriver();
    }
    
    /**
     * Teardown method - executed after each scenario
     * Captures screenshot on failure and quits WebDriver
     * 
     * @param scenario - Cucumber Scenario object
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            // Capture screenshot if scenario failed
            if (scenario.isFailed()) {
                System.out.println("Scenario FAILED: " + scenario.getName());
                captureScreenshot(scenario);
            } else {
                System.out.println("Scenario PASSED: " + scenario.getName());
            }
        } catch (Exception e) {
            System.err.println("Error in teardown: " + e.getMessage());
        } finally {
            // Quit driver
            DriverManager.quitDriver();
            System.out.println("========================================");
            System.out.println("Completed Scenario: " + scenario.getName());
            System.out.println("========================================\n");
        }
    }
    
    /**
     * Capture screenshot and attach to Cucumber report
     * 
     * @param scenario - Cucumber Scenario object
     */
    private void captureScreenshot(Scenario scenario) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getDriver();
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotBytes, "image/png", "Screenshot on Failure");
            System.out.println("Screenshot captured for failed scenario");
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
