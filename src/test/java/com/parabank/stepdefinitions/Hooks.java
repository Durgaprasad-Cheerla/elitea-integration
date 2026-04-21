package com.parabank.stepdefinitions;

import com.parabank.utils.ConfigReader;
import com.parabank.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cucumber Hooks for setup and teardown operations
 * Contains Before and After hooks for test execution lifecycle
 * 
 * JIRA: SCRUM-167
 * 
 * @author Automation Team
 * @version 1.0
 */
public class Hooks {
    
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private WebDriver driver;
    
    /**
     * Before Hook - Executes before each scenario
     * Initializes WebDriver and sets up test environment
     * 
     * @param scenario Cucumber scenario instance
     */
    @Before
    public void setUp(Scenario scenario) {
        logger.info("========================================");
        logger.info("Starting Scenario: {}", scenario.getName());
        logger.info("Scenario Tags: {}", scenario.getSourceTagNames());
        logger.info("========================================");
        
        try {
            // Get browser from configuration
            String browser = ConfigReader.getBrowser();
            logger.info("Initializing browser: {}", browser);
            
            // Initialize WebDriver
            driver = DriverManager.getDriver(browser);
            logger.info("WebDriver initialized successfully");
            
            // Log test execution start time
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            logger.info("Test execution started at: {}", timestamp);
            
        } catch (Exception e) {
            logger.error("Error during test setup: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to set up test environment", e);
        }
    }
    
    /**
     * After Step Hook - Executes after each step
     * Takes screenshot on step failure if configured
     * 
     * @param scenario Cucumber scenario instance
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        // Take screenshot if step failed and screenshot on failure is enabled
        if (scenario.isFailed() && ConfigReader.isScreenshotOnFailureEnabled()) {
            logger.warn("Step failed in scenario: {}", scenario.getName());
            captureScreenshot(scenario, "FAILED_STEP");
        }
    }
    
    /**
     * After Hook - Executes after each scenario
     * Captures screenshot on failure and quits WebDriver
     * 
     * @param scenario Cucumber scenario instance
     */
    @After
    public void tearDown(Scenario scenario) {
        logger.info("========================================");
        logger.info("Completing Scenario: {}", scenario.getName());
        logger.info("Scenario Status: {}", scenario.getStatus());
        logger.info("========================================");
        
        try {
            // Log test execution end time
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            logger.info("Test execution completed at: {}", timestamp);
            
            // Capture screenshot based on scenario status
            if (scenario.isFailed()) {
                logger.error("Scenario FAILED: {}", scenario.getName());
                if (ConfigReader.isScreenshotOnFailureEnabled()) {
                    captureScreenshot(scenario, "FAILED");
                }
            } else {
                logger.info("Scenario PASSED: {}", scenario.getName());
                // Optionally capture screenshot on pass as well
                captureScreenshot(scenario, "PASSED");
            }
            
        } catch (Exception e) {
            logger.error("Error during screenshot capture: {}", e.getMessage(), e);
        } finally {
            // Quit WebDriver
            try {
                if (driver != null) {
                    logger.info("Closing browser and quitting WebDriver");
                    DriverManager.quitDriver();
                    logger.info("WebDriver quit successfully");
                }
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver: {}", e.getMessage(), e);
            }
        }
        
        logger.info("========================================\n");
    }
    
    /**
     * Capture screenshot and attach to Cucumber report
     * Also save screenshot to file system
     * 
     * @param scenario Cucumber scenario instance
     * @param status Status string to append to filename (PASSED/FAILED)
     */
    private void captureScreenshot(Scenario scenario, String status) {
        try {
            driver = DriverManager.getDriver();
            
            if (driver != null) {
                // Take screenshot as byte array
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                
                // Attach screenshot to Cucumber report
                scenario.attach(screenshot, "image/png", scenario.getName() + "_" + status);
                logger.info("Screenshot attached to Cucumber report");
                
                // Save screenshot to file system
                saveScreenshotToFile(screenshot, scenario.getName(), status);
                
            } else {
                logger.warn("WebDriver is null, cannot capture screenshot");
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Save screenshot to file system
     * 
     * @param screenshot Screenshot byte array
     * @param scenarioName Name of the scenario
     * @param status Status string (PASSED/FAILED)
     */
    private void saveScreenshotToFile(byte[] screenshot, String scenarioName, String status) {
        try {
            // Get screenshot folder from configuration
            String screenshotFolder = ConfigReader.getScreenshotFolder();
            
            // Create folder if it doesn't exist
            File folder = new File(screenshotFolder);
            if (!folder.exists()) {
                folder.mkdirs();
                logger.info("Created screenshot folder: {}", screenshotFolder);
            }
            
            // Generate filename with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String sanitizedScenarioName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");
            String filename = sanitizedScenarioName + "_" + status + "_" + timestamp + ".png";
            String filepath = screenshotFolder + File.separator + filename;
            
            // Write screenshot to file
            Files.write(Paths.get(filepath), screenshot);
            logger.info("Screenshot saved to: {}", filepath);
            
        } catch (IOException e) {
            logger.error("Failed to save screenshot to file: {}", e.getMessage(), e);
        }
    }
}
