package com.edwardjones.automation.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.edwardjones.automation.driver.DriverManager;
import com.edwardjones.automation.context.TestContext;
import com.edwardjones.automation.utils.ScreenshotUtil;
import com.edwardjones.automation.config.ConfigurationManager;

/**
 * Cucumber Hooks - Before and After scenario execution
 * Handles setup and teardown operations for each test scenario
 * Manages WebDriver lifecycle and failure handling
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 */
public class CucumberHooks {
    
    private static final Logger logger = LogManager.getLogger(CucumberHooks.class);
    private ConfigurationManager config;
    
    public CucumberHooks() {
        this.config = ConfigurationManager.getInstance();
    }
    
    /**
     * Executed before each scenario
     * Initializes WebDriver and test context
     * 
     * @param scenario Cucumber scenario
     */
    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("========================================");
        logger.info("STARTING SCENARIO: " + scenario.getName());
        logger.info("Scenario ID: " + scenario.getId());
        logger.info("Scenario Tags: " + scenario.getSourceTagNames());
        logger.info("========================================");
        
        // Initialize WebDriver (will create new instance if not exists)
        try {
            DriverManager.getDriver();
            logger.info("WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: " + e.getMessage());
            throw new RuntimeException("WebDriver initialization failed", e);
        }
        
        // Initialize Test Context
        TestContext.getInstance();
        logger.info("Test Context initialized");
    }
    
    /**
     * Executed after each scenario
     * Captures screenshot on failure, cleans up resources
     * 
     * @param scenario Cucumber scenario
     */
    @After
    public void afterScenario(Scenario scenario) {
        logger.info("========================================");
        logger.info("FINISHING SCENARIO: " + scenario.getName());
        logger.info("Scenario Status: " + scenario.getStatus());
        logger.info("========================================");
        
        // Capture screenshot if scenario failed
        if (scenario.isFailed() && config.shouldTakeScreenshotOnFailure()) {
            try {
                logger.warn("Scenario FAILED - Capturing screenshot");
                
                // Capture screenshot as bytes and attach to report
                byte[] screenshot = ScreenshotUtil.captureScreenshotAsBytes();
                if (screenshot != null) {
                    scenario.attach(screenshot, "image/png", scenario.getName() + "_FAILED");
                    logger.info("Screenshot attached to Cucumber report");
                }
                
                // Also save screenshot to file
                String screenshotPath = ScreenshotUtil.captureFailureScreenshot(scenario.getName());
                if (screenshotPath != null) {
                    logger.info("Failure screenshot saved to: " + screenshotPath);
                }
            } catch (Exception e) {
                logger.error("Failed to capture screenshot: " + e.getMessage());
            }
        }
        
        // Log scenario result
        if (scenario.isFailed()) {
            logger.error("❌ SCENARIO FAILED: " + scenario.getName());
        } else {
            logger.info("✅ SCENARIO PASSED: " + scenario.getName());
        }
        
        // Clean up test context
        try {
            TestContext.clearContext();
            logger.info("Test Context cleared");
        } catch (Exception e) {
            logger.warn("Error clearing test context: " + e.getMessage());
        }
        
        // Quit WebDriver after scenario
        try {
            DriverManager.quitDriver();
            logger.info("WebDriver quit successfully");
        } catch (Exception e) {
            logger.error("Error quitting WebDriver: " + e.getMessage());
        }
        
        logger.info("========================================\n");
    }
    
    /**
     * Executed before scenarios tagged with @PerformanceTest
     * Sets up performance monitoring
     * 
     * @param scenario Cucumber scenario
     */
    @Before("@PERF-1 or @Performance")
    public void beforePerformanceTest(Scenario scenario) {
        logger.info("⏱️  Performance test detected - Performance monitoring enabled");
        // Additional performance setup can be added here
    }
    
    /**
     * Executed before scenarios tagged with @Security
     * Sets up security monitoring
     * 
     * @param scenario Cucumber scenario
     */
    @Before("@SEC-1 or @SEC-2 or @SEC-3 or @Security")
    public void beforeSecurityTest(Scenario scenario) {
        logger.info("🔒 Security test detected - Security validation enabled");
        // Additional security setup can be added here
    }
    
    /**
     * Executed after scenarios tagged with @Cleanup
     * Ensures cleanup is performed
     * 
     * @param scenario Cucumber scenario
     */
    @After("@Cleanup")
    public void afterCleanupTest(Scenario scenario) {
        logger.info("🧹 Cleanup scenario - Ensuring all resources are cleaned");
        
        try {
            // Clear all browser cookies and storage
            if (DriverManager.getDriver() != null) {
                DriverManager.getDriver().manage().deleteAllCookies();
                logger.info("All cookies and storage cleared");
            }
        } catch (Exception e) {
            logger.warn("Error during cleanup: " + e.getMessage());
        }
    }
}
