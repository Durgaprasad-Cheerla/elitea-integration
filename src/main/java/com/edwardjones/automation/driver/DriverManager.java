package com.edwardjones.automation.driver;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.edwardjones.automation.config.ConfigurationManager;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * WebDriver Manager - Handles browser driver initialization and management
 * Uses WebDriverManager for automatic driver management
 * Implements ThreadLocal for parallel execution support
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 */
public class DriverManager {
    
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ConfigurationManager config = ConfigurationManager.getInstance();
    
    /**
     * Initializes WebDriver based on browser configuration
     * 
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }
    
    /**
     * Initializes driver based on browser type from configuration
     */
    private static void initializeDriver() {
        String browserType = config.getBrowserType().toLowerCase();
        boolean isHeadless = config.isHeadless();
        
        logger.info("Initializing " + browserType + " browser (Headless: " + isHeadless + ")");
        
        WebDriver webDriver = null;
        
        switch (browserType) {
            case "chrome":
                webDriver = initializeChromeDriver(isHeadless);
                break;
            case "firefox":
                webDriver = initializeFirefoxDriver(isHeadless);
                break;
            case "edge":
                webDriver = initializeEdgeDriver(isHeadless);
                break;
            case "safari":
                webDriver = initializeSafariDriver();
                break;
            default:
                logger.error("Unsupported browser type: " + browserType);
                throw new IllegalArgumentException("Browser type not supported: " + browserType);
        }
        
        configureDriver(webDriver);
        driver.set(webDriver);
        logger.info("WebDriver initialized successfully");
    }
    
    /**
     * Initializes Chrome WebDriver
     * 
     * @param isHeadless Run in headless mode
     * @return ChromeDriver instance
     */
    private static WebDriver initializeChromeDriver(boolean isHeadless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        if (isHeadless) {
            options.addArguments("--headless=new");
        }
        
        // Additional Chrome arguments for stability and security testing
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        
        // Enable DevTools Protocol for network monitoring
        options.setCapability("goog:loggingPrefs", 
            java.util.Map.of("performance", "ALL", "browser", "ALL"));
        
        logger.info("Chrome options configured");
        return new ChromeDriver(options);
    }
    
    /**
     * Initializes Firefox WebDriver
     * 
     * @param isHeadless Run in headless mode
     * @return FirefoxDriver instance
     */
    private static WebDriver initializeFirefoxDriver(boolean isHeadless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (isHeadless) {
            options.addArguments("--headless");
        }
        
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        logger.info("Firefox options configured");
        return new FirefoxDriver(options);
    }
    
    /**
     * Initializes Edge WebDriver
     * 
     * @param isHeadless Run in headless mode
     * @return EdgeDriver instance
     */
    private static WebDriver initializeEdgeDriver(boolean isHeadless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        if (isHeadless) {
            options.addArguments("--headless");
        }
        
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        
        logger.info("Edge options configured");
        return new EdgeDriver(options);
    }
    
    /**
     * Initializes Safari WebDriver
     * Note: Safari does not support headless mode
     * 
     * @return SafariDriver instance
     */
    private static WebDriver initializeSafariDriver() {
        logger.info("Safari driver does not require WebDriverManager setup");
        return new SafariDriver();
    }
    
    /**
     * Configures common WebDriver settings (timeouts, window size)
     * 
     * @param webDriver WebDriver instance to configure
     */
    private static void configureDriver(WebDriver webDriver) {
        // Set timeouts from configuration
        webDriver.manage().timeouts()
            .implicitlyWait(Duration.ofSeconds(config.getImplicitWaitSeconds()));
        webDriver.manage().timeouts()
            .pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeoutSeconds()));
        
        // Maximize window if configured
        if (config.shouldMaximizeWindow()) {
            webDriver.manage().window().maximize();
            logger.info("Browser window maximized");
        }
        
        logger.info("WebDriver timeouts configured - Implicit: " + 
            config.getImplicitWaitSeconds() + "s, Page Load: " + 
            config.getPageLoadTimeoutSeconds() + "s");
    }
    
    /**
     * Quits and removes the current WebDriver instance
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error quitting WebDriver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }
    
    /**
     * Closes current browser window (does not quit driver)
     */
    public static void closeCurrentWindow() {
        if (driver.get() != null) {
            driver.get().close();
            logger.info("Current browser window closed");
        }
    }
}
