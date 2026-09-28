package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

/**
 * DriverManager - Singleton pattern for WebDriver management
 * 
 * Handles WebDriver initialization, configuration, and cleanup.
 * Supports Chrome and Firefox browsers with configurable options.
 */
public class DriverManager {
    
    private static WebDriver driver;
    private static final String BROWSER = System.getProperty("browser", "chrome");
    private static final String HEADLESS = System.getProperty("headless", "false");
    
    /**
     * Get WebDriver instance (Singleton pattern)
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }
    
    /**
     * Initialize WebDriver with browser-specific configurations
     */
    private static void initializeDriver() {
        switch (BROWSER.toLowerCase()) {
            case "firefox":
                initializeFirefoxDriver();
                break;
            case "chrome":
            default:
                initializeChromeDriver();
                break;
        }
        
        // Common WebDriver configurations
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().deleteAllCookies();
    }
    
    /**
     * Initialize Chrome WebDriver
     */
    private static void initializeChromeDriver() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        // Headless mode configuration
        if (HEADLESS.equalsIgnoreCase("true")) {
            options.addArguments("--headless=new");
        }
        
        // Chrome arguments for stability and security
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");
        
        // Accept insecure certificates for testing
        options.setAcceptInsecureCerts(true);
        
        // Set preferences
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        driver = new ChromeDriver(options);
    }
    
    /**
     * Initialize Firefox WebDriver
     */
    private static void initializeFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        
        // Headless mode configuration
        if (HEADLESS.equalsIgnoreCase("true")) {
            options.addArguments("--headless");
        }
        
        // Firefox arguments
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        // Accept insecure certificates
        options.setAcceptInsecureCerts(true);
        
        driver = new FirefoxDriver(options);
    }
    
    /**
     * Quit and cleanup WebDriver instance
     */
    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }
    
    /**
     * Close current browser window
     */
    public static void closeDriver() {
        if (driver != null) {
            try {
                driver.close();
            } catch (Exception e) {
                System.err.println("Error while closing driver: " + e.getMessage());
            }
        }
    }
}
