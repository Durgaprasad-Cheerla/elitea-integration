package com.parabank.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

/**
 * WebDriver Manager to handle browser initialization and configuration
 * Supports Chrome, Firefox, Edge, and Safari browsers
 * 
 * @author Automation Team
 * @version 1.0
 */
public class DriverManager {
    
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    /**
     * Initialize WebDriver based on browser type from configuration
     * @param browserName Name of the browser (chrome, firefox, edge, safari)
     * @return WebDriver instance
     */
    public static WebDriver getDriver(String browserName) {
        if (driver.get() == null) {
            driver.set(createDriver(browserName));
        }
        return driver.get();
    }
    
    /**
     * Get the current WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    /**
     * Create WebDriver instance based on browser type
     * @param browserName Name of the browser
     * @return WebDriver instance
     */
    private static WebDriver createDriver(String browserName) {
        WebDriver webDriver;
        String browser = browserName.toLowerCase().trim();
        
        logger.info("Initializing WebDriver for browser: {}", browser);
        
        switch (browser) {
            case "chrome":
                webDriver = createChromeDriver();
                break;
                
            case "firefox":
                webDriver = createFirefoxDriver();
                break;
                
            case "edge":
                webDriver = createEdgeDriver();
                break;
                
            case "safari":
                webDriver = createSafariDriver();
                break;
                
            default:
                logger.warn("Unknown browser '{}'. Defaulting to Chrome", browser);
                webDriver = createChromeDriver();
                break;
        }
        
        // Configure WebDriver with implicit wait and maximize window
        configureDriver(webDriver);
        
        logger.info("WebDriver initialized successfully for browser: {}", browser);
        return webDriver;
    }
    
    /**
     * Create Chrome WebDriver with options
     * @return WebDriver instance for Chrome
     */
    private static WebDriver createChromeDriver() {
        logger.info("Setting up ChromeDriver");
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        // Read headless mode from config (if needed)
        boolean headless = ConfigReader.getProperty("browser.headless", "false")
                                      .equalsIgnoreCase("true");
        if (headless) {
            options.addArguments("--headless");
            logger.info("Chrome running in headless mode");
        }
        
        // Additional Chrome options for stability
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox WebDriver with options
     * @return WebDriver instance for Firefox
     */
    private static WebDriver createFirefoxDriver() {
        logger.info("Setting up FirefoxDriver");
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        
        boolean headless = ConfigReader.getProperty("browser.headless", "false")
                                      .equalsIgnoreCase("true");
        if (headless) {
            options.addArguments("--headless");
            logger.info("Firefox running in headless mode");
        }
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Create Edge WebDriver with options
     * @return WebDriver instance for Edge
     */
    private static WebDriver createEdgeDriver() {
        logger.info("Setting up EdgeDriver");
        WebDriverManager.edgedriver().setup();
        
        EdgeOptions options = new EdgeOptions();
        
        boolean headless = ConfigReader.getProperty("browser.headless", "false")
                                      .equalsIgnoreCase("true");
        if (headless) {
            options.addArguments("--headless");
            logger.info("Edge running in headless mode");
        }
        
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        
        return new EdgeDriver(options);
    }
    
    /**
     * Create Safari WebDriver
     * Note: Safari does not support headless mode
     * @return WebDriver instance for Safari
     */
    private static WebDriver createSafariDriver() {
        logger.info("Setting up SafariDriver");
        return new SafariDriver();
    }
    
    /**
     * Configure WebDriver with timeouts and window settings
     * @param webDriver WebDriver instance to configure
     */
    private static void configureDriver(WebDriver webDriver) {
        logger.info("Configuring WebDriver with timeouts and window settings");
        
        // Set implicit wait
        int implicitWait = Integer.parseInt(
            ConfigReader.getProperty("implicit.wait", "10")
        );
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        
        // Set page load timeout
        int pageLoadTimeout = Integer.parseInt(
            ConfigReader.getProperty("page.load.timeout", "30")
        );
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        
        // Maximize browser window
        boolean maximize = ConfigReader.getProperty("browser.maximize", "true")
                                      .equalsIgnoreCase("true");
        if (maximize) {
            webDriver.manage().window().maximize();
            logger.info("Browser window maximized");
        }
        
        logger.info("WebDriver configured successfully");
    }
    
    /**
     * Quit the WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting WebDriver");
            driver.get().quit();
            driver.remove();
            logger.info("WebDriver quit successfully");
        }
    }
}
