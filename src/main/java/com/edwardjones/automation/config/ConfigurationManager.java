package com.edwardjones.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Configuration Manager - Centralized configuration handler
 * Loads and manages test configuration properties from test.properties file
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 */
public class ConfigurationManager {
    
    private static final Logger logger = LogManager.getLogger(ConfigurationManager.class);
    private static ConfigurationManager instance;
    private Properties properties;
    
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/test.properties";
    
    /**
     * Private constructor for Singleton pattern
     * Loads configuration properties on instantiation
     */
    private ConfigurationManager() {
        properties = new Properties();
        loadProperties();
    }
    
    /**
     * Gets singleton instance of ConfigurationManager
     * 
     * @return ConfigurationManager instance
     */
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Loads properties from configuration file
     */
    private void loadProperties() {
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
            logger.info("Configuration properties loaded successfully from: " + CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("Failed to load configuration properties: " + e.getMessage());
            throw new RuntimeException("Configuration file not found: " + CONFIG_FILE_PATH, e);
        }
    }
    
    /**
     * Gets property value by key
     * 
     * @param key Property key
     * @return Property value as String
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property not found for key: " + key);
        }
        return value;
    }
    
    /**
     * Gets property value with default fallback
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Gets integer property value
     * 
     * @param key Property key
     * @return Property value as integer
     */
    public int getIntProperty(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.error("Invalid integer format for key: " + key);
            throw new RuntimeException("Invalid integer property: " + key, e);
        }
    }
    
    /**
     * Gets boolean property value
     * 
     * @param key Property key
     * @return Property value as boolean
     */
    public boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }
    
    // Application URLs
    public String getBaseUrl() {
        return getProperty("app.base.url");
    }
    
    public String getLoginUrl() {
        return getProperty("app.login.url");
    }
    
    public String getDashboardUrl() {
        return getProperty("app.dashboard.url");
    }
    
    // Browser Configuration
    public String getBrowserType() {
        return getProperty("browser.type", "chrome");
    }
    
    public boolean isHeadless() {
        return getBooleanProperty("browser.headless");
    }
    
    public boolean shouldMaximizeWindow() {
        return getBooleanProperty("browser.window.maximize");
    }
    
    public int getImplicitWaitSeconds() {
        return getIntProperty("implicit.wait.seconds");
    }
    
    public int getExplicitWaitSeconds() {
        return getIntProperty("explicit.wait.seconds");
    }
    
    public int getPageLoadTimeoutSeconds() {
        return getIntProperty("page.load.timeout.seconds");
    }
    
    // Test Data - User Credentials
    public String getTestUsername() {
        return getProperty("test.user.username");
    }
    
    public String getTestPassword() {
        return getProperty("test.user.password");
    }
    
    // Performance Thresholds
    public int getAuthenticationMaxSeconds() {
        return getIntProperty("performance.authentication.max.seconds");
    }
    
    public int getPageLoadMaxSeconds() {
        return getIntProperty("performance.page.load.max.seconds");
    }
    
    // Security Configuration
    public boolean shouldVerifyHttps() {
        return getBooleanProperty("security.verify.https");
    }
    
    public boolean shouldVerifyPasswordMasking() {
        return getBooleanProperty("security.verify.password.masking");
    }
    
    public boolean shouldVerifySessionToken() {
        return getBooleanProperty("security.verify.session.token");
    }
    
    // Reporting Configuration
    public String getReportOutputDirectory() {
        return getProperty("report.output.directory");
    }
    
    public boolean shouldTakeScreenshotOnFailure() {
        return getBooleanProperty("screenshot.on.failure");
    }
    
    public String getScreenshotDirectory() {
        return getProperty("screenshot.directory");
    }
}
