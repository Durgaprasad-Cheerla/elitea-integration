package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader - Utility class for reading configuration properties
 * 
 * Provides centralized access to test configuration values from
 * config.properties file.
 */
public class ConfigReader {
    
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    
    static {
        loadProperties();
    }
    
    /**
     * Load properties from config.properties file
     */
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
            System.out.println("Configuration loaded successfully from: " + CONFIG_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to load configuration file: " + e.getMessage());
            // Initialize with default properties if file not found
            setDefaultProperties();
        }
    }
    
    /**
     * Set default properties if config file is not found
     */
    private static void setDefaultProperties() {
        properties.setProperty("app.url", "https://onlineaccess.edwardjones.com/app/oa-login");
        properties.setProperty("browser", "chrome");
        properties.setProperty("headless", "false");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "10");
        properties.setProperty("page.load.timeout", "30");
    }
    
    /**
     * Get property value by key
     * 
     * @param key - Property key
     * @return String - Property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get property value with default fallback
     * 
     * @param key - Property key
     * @param defaultValue - Default value if key not found
     * @return String - Property value or default
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get application URL
     * 
     * @return String - Application URL
     */
    public static String getAppUrl() {
        return getProperty("app.url");
    }
    
    /**
     * Get browser name
     * 
     * @return String - Browser name (chrome/firefox)
     */
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    /**
     * Check if headless mode is enabled
     * 
     * @return boolean - true if headless mode
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    /**
     * Get implicit wait timeout
     * 
     * @return int - Timeout in seconds
     */
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    /**
     * Get explicit wait timeout
     * 
     * @return int - Timeout in seconds
     */
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "10"));
    }
    
    /**
     * Get page load timeout
     * 
     * @return int - Timeout in seconds
     */
    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }
    
    /**
     * Get invalid user ID for testing
     * 
     * @return String - Invalid user ID
     */
    public static String getInvalidUserId() {
        return getProperty("invalid.userId", "InvalidUser999");
    }
    
    /**
     * Get invalid password for testing
     * 
     * @return String - Invalid password
     */
    public static String getInvalidPassword() {
        return getProperty("invalid.password", "WrongPass123");
    }
}
