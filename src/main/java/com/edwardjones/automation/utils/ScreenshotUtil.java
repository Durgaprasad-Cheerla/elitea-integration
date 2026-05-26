package com.edwardjones.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.edwardjones.automation.driver.DriverManager;
import com.edwardjones.automation.config.ConfigurationManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Screenshot Utility
 * Provides methods for capturing and saving screenshots
 * Used for test reporting and failure documentation
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 */
public class ScreenshotUtil {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static ConfigurationManager config = ConfigurationManager.getInstance();
    
    /**
     * Captures screenshot and saves to file
     * 
     * @param testName Test case name for screenshot filename
     * @return File path of saved screenshot
     */
    public static String captureScreenshot(String testName) {
        WebDriver driver = DriverManager.getDriver();
        
        if (driver == null) {
            logger.error("WebDriver is null, cannot capture screenshot");
            return null;
        }
        
        try {
            // Generate timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            
            // Get screenshot directory from config
            String screenshotDir = config.getScreenshotDirectory();
            
            // Create directory if it doesn't exist
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
                logger.info("Created screenshot directory: " + screenshotDir);
            }
            
            // Capture screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            // Define destination file path
            String destinationPath = screenshotDir + File.separator + fileName;
            File destinationFile = new File(destinationPath);
            
            // Copy screenshot to destination
            FileUtils.copyFile(sourceFile, destinationFile);
            
            logger.info("Screenshot captured successfully: " + destinationPath);
            return destinationPath;
            
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Captures screenshot with custom path
     * 
     * @param filePath Complete file path where screenshot should be saved
     * @return File path of saved screenshot
     */
    public static String captureScreenshotWithPath(String filePath) {
        WebDriver driver = DriverManager.getDriver();
        
        if (driver == null) {
            logger.error("WebDriver is null, cannot capture screenshot");
            return null;
        }
        
        try {
            // Create parent directory if it doesn't exist
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
                logger.info("Created directory: " + parentDir.getAbsolutePath());
            }
            
            // Capture screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            // Copy screenshot to destination
            FileUtils.copyFile(sourceFile, file);
            
            logger.info("Screenshot captured successfully: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Captures screenshot as byte array (for embedding in reports)
     * 
     * @return Screenshot as byte array
     */
    public static byte[] captureScreenshotAsBytes() {
        WebDriver driver = DriverManager.getDriver();
        
        if (driver == null) {
            logger.error("WebDriver is null, cannot capture screenshot");
            return null;
        }
        
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
            logger.info("Screenshot captured as byte array");
            return screenshotBytes;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot as bytes: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Captures screenshot for failed scenario
     * 
     * @param scenarioName Scenario name
     * @return File path of saved screenshot
     */
    public static String captureFailureScreenshot(String scenarioName) {
        String fileName = "FAILED_" + scenarioName;
        logger.warn("Capturing failure screenshot for: " + scenarioName);
        return captureScreenshot(fileName);
    }
}
