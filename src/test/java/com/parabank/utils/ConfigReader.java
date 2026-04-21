package com.parabank.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Reader utility to read properties from config.properties file
 * 
 * @author Automation Team
 * @version 1.0
 */
public class ConfigReader {
    
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    
    // Static block to load properties when class is loaded
    static {
        loadProperties();
    }
    
    /**
     * Load properties from config file
     */
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
            logger.info("Configuration properties loaded successfully from: {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("Error loading configuration properties: {}", e.getMessage());
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE_PATH, e);
        }
    }
    
    /**
     * Get property value by key
     * @param key Property key
     * @return String property value
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in configuration file", key);
        }
        return value;
    }
    
    /**
     * Get property value by key with default value
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return String property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        logger.debug("Property '{}' = '{}'", key, value);
        return value;
    }
    
    /**
     * Get base URL from configuration
     * @return String base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url");
    }
    
    /**
     * Get browser name from configuration
     * @return String browser name
     */
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    /**
     * Get implicit wait timeout
     * @return int implicit wait in seconds
     */
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    /**
     * Get explicit wait timeout
     * @return int explicit wait in seconds
     */
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }
    
    /**
     * Get page load timeout
     * @return int page load timeout in seconds
     */
    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }
    
    /**
     * Check if screenshot on failure is enabled
     * @return boolean true if enabled, false otherwise
     */
    public static boolean isScreenshotOnFailureEnabled() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }
    
    /**
     * Get screenshot folder path
     * @return String screenshot folder path
     */
    public static String getScreenshotFolder() {
        return getProperty("screenshot.folder", "test-output/screenshots");
    }
    
    /**
     * Get report folder path
     * @return String report folder path
     */
    public static String getReportFolder() {
        return getProperty("report.folder", "test-output/reports");
    }
}
