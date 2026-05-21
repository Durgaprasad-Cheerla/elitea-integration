package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

/**
 * Driver Manager class to handle WebDriver initialization and cleanup
 * Implements Singleton pattern to ensure only one WebDriver instance per thread
 * 
 * @author QA Automation Team
 * @version 1.0
 */
public class DriverManager {
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    /**
     * Get the WebDriver instance for current thread
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }
    
    /**
     * Initialize WebDriver based on browser configuration
     */
    private static void initializeDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                
                // Headless mode if specified
                if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                
                driver.set(new ChromeDriver(chromeOptions));
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                
                if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                    firefoxOptions.addArguments("--headless");
                }
                
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                
                if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                    edgeOptions.addArguments("--headless");
                }
                
                driver.set(new EdgeDriver(edgeOptions));
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        
        // Configure default timeouts
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get().manage().window().maximize();
    }
    
    /**
     * Quit the WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
