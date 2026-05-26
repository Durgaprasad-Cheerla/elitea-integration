package com.edwardjones.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Set;

/**
 * Dashboard Page Object Model
 * Contains all locators and methods for Edward Jones Dashboard Page (post-login)
 * Handles session management and user-specific content verification
 * 
 * @author Automation Team
 * @version 1.0
 * @since 2024-01-01
 * 
 * Test Case Coverage: SCRUM-184 - Dashboard verification after successful authentication
 */
public class DashboardPage extends BasePage {
    
    private static final Logger logger = LogManager.getLogger(DashboardPage.class);
    
    // Dashboard Page Locators
    private final By dashboardContainer = By.cssSelector(".dashboard-container, #dashboard");
    private final By welcomeMessage = By.cssSelector(".welcome-message, .user-greeting");
    private final By userName = By.cssSelector(".user-name, .username-display");
    private final By accountInfo = By.cssSelector(".account-info, .user-account");
    private final By logoutButton = By.cssSelector("button.logout, a[href*='logout']");
    private final By userProfile = By.cssSelector(".user-profile, .profile-section");
    private final By dashboardMenu = By.cssSelector(".dashboard-menu, nav.main-nav");
    
    // Session/Security related
    private static final String SESSION_TOKEN_COOKIE_NAME = "session_token";
    private static final String AUTH_TOKEN_COOKIE_NAME = "auth_token";
    private static final String JSESSIONID_COOKIE_NAME = "JSESSIONID";
    
    /**
     * Constructor
     */
    public DashboardPage() {
        super();
        logger.info("DashboardPage object initialized");
    }
    
    /**
     * Verifies if dashboard page is loaded
     * 
     * @return true if dashboard loaded, false otherwise
     */
    public boolean isDashboardPageLoaded() {
        try {
            boolean isLoaded = isElementDisplayed(dashboardContainer) || 
                              isElementDisplayed(dashboardMenu);
            logger.info("Dashboard page loaded status: " + isLoaded);
            return isLoaded;
        } catch (Exception e) {
            logger.error("Error checking dashboard page load: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verifies if redirected to dashboard (URL check)
     * 
     * @return true if on dashboard URL, false otherwise
     */
    public boolean isOnDashboardUrl() {
        String currentUrl = getCurrentUrl();
        String expectedDashboardUrl = config.getDashboardUrl();
        boolean isOnDashboard = currentUrl.contains("/dashboard");
        
        logger.info("Dashboard URL verification - Current: " + currentUrl + 
                   ", Expected: " + expectedDashboardUrl + ", Match: " + isOnDashboard);
        return isOnDashboard;
    }
    
    /**
     * Gets the current dashboard URL
     * 
     * @return Dashboard URL
     */
    public String getDashboardUrl() {
        String url = getCurrentUrl();
        logger.info("Current dashboard URL: " + url);
        return url;
    }
    
    /**
     * Verifies dashboard URL matches expected URL exactly
     * 
     * @param expectedUrl Expected dashboard URL
     * @return true if URLs match, false otherwise
     */
    public boolean verifyDashboardUrl(String expectedUrl) {
        String actualUrl = getCurrentUrl();
        boolean matches = actualUrl.equals(expectedUrl);
        logger.info("URL verification - Expected: " + expectedUrl + 
                   ", Actual: " + actualUrl + ", Match: " + matches);
        return matches;
    }
    
    /**
     * Checks if welcome message is displayed
     * 
     * @return true if displayed, false otherwise
     */
    public boolean isWelcomeMessageDisplayed() {
        try {
            boolean isDisplayed = isElementDisplayed(welcomeMessage);
            logger.info("Welcome message displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.debug("Welcome message not found");
            return false;
        }
    }
    
    /**
     * Gets welcome message text
     * 
     * @return Welcome message text
     */
    public String getWelcomeMessageText() {
        try {
            String message = getText(welcomeMessage);
            logger.info("Welcome message: " + message);
            return message;
        } catch (Exception e) {
            logger.warn("Could not retrieve welcome message");
            return "";
        }
    }
    
    /**
     * Checks if user-specific content is displayed
     * 
     * @return true if user content displayed, false otherwise
     */
    public boolean isUserSpecificContentDisplayed() {
        try {
            boolean isUserNameDisplayed = isElementDisplayed(userName);
            boolean isAccountInfoDisplayed = isElementDisplayed(accountInfo);
            
            boolean contentDisplayed = isUserNameDisplayed || isAccountInfoDisplayed;
            logger.info("User-specific content displayed: " + contentDisplayed);
            return contentDisplayed;
        } catch (Exception e) {
            logger.debug("User-specific content not found");
            return false;
        }
    }
    
    /**
     * Gets displayed username from dashboard
     * 
     * @return Username text
     */
    public String getDisplayedUsername() {
        try {
            String username = getText(userName);
            logger.info("Displayed username: " + username);
            return username;
        } catch (Exception e) {
            logger.warn("Could not retrieve username from dashboard");
            return "";
        }
    }
    
    /**
     * Verifies personalized content for specific user
     * 
     * @param expectedUsername Expected username
     * @return true if username matches, false otherwise
     */
    public boolean verifyPersonalizedContent(String expectedUsername) {
        String actualUsername = getDisplayedUsername();
        boolean matches = actualUsername.contains(expectedUsername);
        logger.info("Personalized content verification - Expected: " + expectedUsername + 
                   ", Actual: " + actualUsername + ", Match: " + matches);
        return matches;
    }
    
    /**
     * Gets session token cookie from browser
     * 
     * @return Session token cookie, or null if not found
     */
    public Cookie getSessionTokenCookie() {
        Set<Cookie> cookies = driver.manage().getCookies();
        
        // Try multiple possible session cookie names
        Cookie sessionCookie = driver.manage().getCookieNamed(SESSION_TOKEN_COOKIE_NAME);
        if (sessionCookie != null) {
            logger.info("Session token cookie found: " + SESSION_TOKEN_COOKIE_NAME);
            return sessionCookie;
        }
        
        sessionCookie = driver.manage().getCookieNamed(AUTH_TOKEN_COOKIE_NAME);
        if (sessionCookie != null) {
            logger.info("Auth token cookie found: " + AUTH_TOKEN_COOKIE_NAME);
            return sessionCookie;
        }
        
        sessionCookie = driver.manage().getCookieNamed(JSESSIONID_COOKIE_NAME);
        if (sessionCookie != null) {
            logger.info("JSESSIONID cookie found: " + JSESSIONID_COOKIE_NAME);
            return sessionCookie;
        }
        
        logger.warn("No session token cookie found");
        return null;
    }
    
    /**
     * Verifies session token exists in browser storage
     * 
     * @return true if session token exists, false otherwise
     */
    public boolean isSessionTokenPresent() {
        Cookie sessionCookie = getSessionTokenCookie();
        boolean isPresent = sessionCookie != null;
        logger.info("Session token present: " + isPresent);
        return isPresent;
    }
    
    /**
     * Verifies session token has HttpOnly flag set
     * 
     * @return true if HttpOnly flag set, false otherwise
     */
    public boolean isSessionTokenHttpOnly() {
        Cookie sessionCookie = getSessionTokenCookie();
        if (sessionCookie != null) {
            boolean isHttpOnly = sessionCookie.isHttpOnly();
            logger.info("Session token HttpOnly flag: " + isHttpOnly);
            return isHttpOnly;
        }
        logger.warn("Session cookie not found, cannot verify HttpOnly flag");
        return false;
    }
    
    /**
     * Verifies session token has Secure flag set
     * 
     * @return true if Secure flag set, false otherwise
     */
    public boolean isSessionTokenSecure() {
        Cookie sessionCookie = getSessionTokenCookie();
        if (sessionCookie != null) {
            boolean isSecure = sessionCookie.isSecure();
            logger.info("Session token Secure flag: " + isSecure);
            return isSecure;
        }
        logger.warn("Session cookie not found, cannot verify Secure flag");
        return false;
    }
    
    /**
     * Gets all cookies from browser
     * 
     * @return Set of all cookies
     */
    public Set<Cookie> getAllCookies() {
        Set<Cookie> cookies = driver.manage().getCookies();
        logger.info("Retrieved " + cookies.size() + " cookies");
        return cookies;
    }
    
    /**
     * Navigates to another authenticated page (for session persistence testing)
     * 
     * @param pageUrl URL of authenticated page
     */
    public void navigateToAuthenticatedPage(String pageUrl) {
        navigateToUrl(pageUrl);
        logger.info("Navigated to authenticated page: " + pageUrl);
    }
    
    /**
     * Checks if user is still authenticated (no login prompt)
     * 
     * @return true if authenticated, false if redirected to login
     */
    public boolean isUserStillAuthenticated() {
        String currentUrl = getCurrentUrl();
        boolean isAuthenticated = !currentUrl.contains("/login") && 
                                 (currentUrl.contains("/dashboard") || 
                                  currentUrl.contains("/home") || 
                                  currentUrl.contains("/account"));
        logger.info("User authentication status: " + isAuthenticated);
        return isAuthenticated;
    }
    
    /**
     * Clicks logout button
     */
    public void clickLogoutButton() {
        try {
            click(logoutButton);
            logger.info("Clicked logout button");
        } catch (Exception e) {
            logger.error("Failed to click logout button: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Performs logout
     */
    public void logout() {
        clickLogoutButton();
        logger.info("User logged out");
    }
    
    /**
     * Verifies logout was successful (redirected to login page)
     * 
     * @return true if on login page, false otherwise
     */
    public boolean isLogoutSuccessful() {
        String currentUrl = getCurrentUrl();
        boolean isOnLoginPage = currentUrl.contains("/login");
        logger.info("Logout successful: " + isOnLoginPage);
        return isOnLoginPage;
    }
    
    /**
     * Clears session cookies (for cleanup)
     */
    public void clearSessionCookies() {
        driver.manage().deleteAllCookies();
        logger.info("All session cookies cleared");
    }
}
