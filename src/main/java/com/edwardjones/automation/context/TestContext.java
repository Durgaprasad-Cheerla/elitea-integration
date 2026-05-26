package com.edwardjones.automation.context;

import com.edwardjones.automation.pages.LoginPage;
import com.edwardjones.automation.pages.DashboardPage;
import com.edwardjones.automation.utils.SecurityPerformanceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Context - Shared state management for test scenarios
 * Stores page objects, test data, and runtime variables
 * Provides thread-safe context for parallel execution
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 */
public class TestContext {
    
    private static final Logger logger = LogManager.getLogger(TestContext.class);
    private static ThreadLocal<TestContext> instance = new ThreadLocal<>();
    
    // Page Objects
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    
    // Utilities
    private SecurityPerformanceHelper securityPerformanceHelper;
    
    // Test Data Storage
    private Map<String, Object> testData;
    
    // Runtime Variables
    private long authenticationStartTime;
    private long authenticationEndTime;
    private String currentUsername;
    private String currentPassword;
    
    /**
     * Private constructor for thread-safe singleton
     */
    private TestContext() {
        testData = new HashMap<>();
        logger.info("TestContext initialized for thread: " + Thread.currentThread().getId());
    }
    
    /**
     * Gets TestContext instance for current thread
     * 
     * @return TestContext instance
     */
    public static TestContext getInstance() {
        if (instance.get() == null) {
            instance.set(new TestContext());
        }
        return instance.get();
    }
    
    /**
     * Clears TestContext for current thread
     */
    public static void clearContext() {
        if (instance.get() != null) {
            instance.get().cleanup();
            instance.remove();
            logger.info("TestContext cleared for thread: " + Thread.currentThread().getId());
        }
    }
    
    /**
     * Gets LoginPage instance (creates if doesn't exist)
     * 
     * @return LoginPage instance
     */
    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
            logger.debug("LoginPage instantiated");
        }
        return loginPage;
    }
    
    /**
     * Gets DashboardPage instance (creates if doesn't exist)
     * 
     * @return DashboardPage instance
     */
    public DashboardPage getDashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage();
            logger.debug("DashboardPage instantiated");
        }
        return dashboardPage;
    }
    
    /**
     * Gets SecurityPerformanceHelper instance (creates if doesn't exist)
     * 
     * @return SecurityPerformanceHelper instance
     */
    public SecurityPerformanceHelper getSecurityPerformanceHelper() {
        if (securityPerformanceHelper == null) {
            securityPerformanceHelper = new SecurityPerformanceHelper();
            logger.debug("SecurityPerformanceHelper instantiated");
        }
        return securityPerformanceHelper;
    }
    
    /**
     * Stores test data with key-value pair
     * 
     * @param key Data key
     * @param value Data value
     */
    public void setTestData(String key, Object value) {
        testData.put(key, value);
        logger.debug("Test data stored - Key: " + key);
    }
    
    /**
     * Retrieves test data by key
     * 
     * @param key Data key
     * @return Data value
     */
    public Object getTestData(String key) {
        Object value = testData.get(key);
        logger.debug("Test data retrieved - Key: " + key);
        return value;
    }
    
    /**
     * Retrieves test data as String
     * 
     * @param key Data key
     * @return Data value as String
     */
    public String getTestDataAsString(String key) {
        Object value = getTestData(key);
        return value != null ? value.toString() : null;
    }
    
    /**
     * Checks if test data exists for key
     * 
     * @param key Data key
     * @return true if exists, false otherwise
     */
    public boolean hasTestData(String key) {
        return testData.containsKey(key);
    }
    
    /**
     * Clears all test data
     */
    public void clearTestData() {
        testData.clear();
        logger.debug("All test data cleared");
    }
    
    /**
     * Sets authentication start time
     * 
     * @param startTime Start timestamp
     */
    public void setAuthenticationStartTime(long startTime) {
        this.authenticationStartTime = startTime;
        logger.debug("Authentication start time set: " + startTime);
    }
    
    /**
     * Gets authentication start time
     * 
     * @return Start timestamp
     */
    public long getAuthenticationStartTime() {
        return authenticationStartTime;
    }
    
    /**
     * Sets authentication end time
     * 
     * @param endTime End timestamp
     */
    public void setAuthenticationEndTime(long endTime) {
        this.authenticationEndTime = endTime;
        logger.debug("Authentication end time set: " + endTime);
    }
    
    /**
     * Gets authentication end time
     * 
     * @return End timestamp
     */
    public long getAuthenticationEndTime() {
        return authenticationEndTime;
    }
    
    /**
     * Calculates authentication duration in milliseconds
     * 
     * @return Duration in milliseconds
     */
    public long getAuthenticationDurationMillis() {
        long duration = authenticationEndTime - authenticationStartTime;
        logger.info("Authentication duration: " + duration + " ms");
        return duration;
    }
    
    /**
     * Calculates authentication duration in seconds
     * 
     * @return Duration in seconds
     */
    public double getAuthenticationDurationSeconds() {
        double duration = (authenticationEndTime - authenticationStartTime) / 1000.0;
        logger.info("Authentication duration: " + duration + " seconds");
        return duration;
    }
    
    /**
     * Sets current username being tested
     * 
     * @param username Username
     */
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
        logger.debug("Current username set: " + username);
    }
    
    /**
     * Gets current username
     * 
     * @return Current username
     */
    public String getCurrentUsername() {
        return currentUsername;
    }
    
    /**
     * Sets current password being tested
     * 
     * @param password Password
     */
    public void setCurrentPassword(String password) {
        this.currentPassword = password;
        logger.debug("Current password set: [MASKED]");
    }
    
    /**
     * Gets current password
     * 
     * @return Current password
     */
    public String getCurrentPassword() {
        return currentPassword;
    }
    
    /**
     * Cleanup method - releases resources
     */
    private void cleanup() {
        loginPage = null;
        dashboardPage = null;
        
        if (securityPerformanceHelper != null) {
            securityPerformanceHelper.closeDevTools();
            securityPerformanceHelper = null;
        }
        
        clearTestData();
        logger.debug("TestContext resources cleaned up");
    }
}
