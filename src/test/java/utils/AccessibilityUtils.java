package utils;

import org.openqa.selenium.WebDriver;

/**
 * Accessibility Utilities class for WCAG compliance testing
 * Provides methods to check color contrast ratios and other accessibility features
 * 
 * @author QA Automation Team
 * @version 1.0
 */
public class AccessibilityUtils {
    
    private WebDriver driver;
    
    public AccessibilityUtils(WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * Calculate contrast ratio between two colors
     * Implements WCAG 2.1 contrast ratio calculation formula
     * 
     * @param backgroundColor background color in RGB format
     * @param foregroundColor foreground color in RGB format
     * @return contrast ratio
     */
    public double calculateContrastRatio(String backgroundColor, String foregroundColor) {
        double[] bgRgb = parseRgbColor(backgroundColor);
        double[] fgRgb = parseRgbColor(foregroundColor);
        
        double bgLuminance = calculateRelativeLuminance(bgRgb);
        double fgLuminance = calculateRelativeLuminance(fgRgb);
        
        double lighter = Math.max(bgLuminance, fgLuminance);
        double darker = Math.min(bgLuminance, fgLuminance);
        
        return (lighter + 0.05) / (darker + 0.05);
    }
    
    /**
     * Parse RGB color string to array of RGB values
     * Supports formats: rgb(r, g, b) or rgba(r, g, b, a)
     * 
     * @param colorString color string in RGB or RGBA format
     * @return array of [R, G, B] values (0-255)
     */
    private double[] parseRgbColor(String colorString) {
        // Remove "rgb(" or "rgba(" and ")"
        String cleaned = colorString.replaceAll("rgba?\\(|\\)", "").trim();
        String[] parts = cleaned.split(",");
        
        return new double[] {
            Double.parseDouble(parts[0].trim()),
            Double.parseDouble(parts[1].trim()),
            Double.parseDouble(parts[2].trim())
        };
    }
    
    /**
     * Calculate relative luminance according to WCAG formula
     * 
     * @param rgb array of RGB values (0-255)
     * @return relative luminance value
     */
    private double calculateRelativeLuminance(double[] rgb) {
        double[] sRgb = new double[3];
        
        for (int i = 0; i < 3; i++) {
            double channel = rgb[i] / 255.0;
            sRgb[i] = channel <= 0.03928 
                ? channel / 12.92 
                : Math.pow((channel + 0.055) / 1.055, 2.4);
        }
        
        // Calculate luminance using WCAG formula: 0.2126*R + 0.7152*G + 0.0722*B
        return 0.2126 * sRgb[0] + 0.7152 * sRgb[1] + 0.0722 * sRgb[2];
    }
    
    /**
     * Check if contrast ratio meets WCAG 2.1 AA standard for normal text
     * Normal text requires minimum 4.5:1 contrast ratio
     * 
     * @param contrastRatio calculated contrast ratio
     * @return true if meets AA standard for normal text
     */
    public boolean meetsWCAG_AA_NormalText(double contrastRatio) {
        return contrastRatio >= 4.5;
    }
    
    /**
     * Check if contrast ratio meets WCAG 2.1 AA standard for large text
     * Large text requires minimum 3:1 contrast ratio
     * 
     * @param contrastRatio calculated contrast ratio
     * @return true if meets AA standard for large text
     */
    public boolean meetsWCAG_AA_LargeText(double contrastRatio) {
        return contrastRatio >= 3.0;
    }
    
    /**
     * Check if contrast ratio meets WCAG 2.1 AAA standard for normal text
     * Normal text requires minimum 7:1 contrast ratio for AAA
     * 
     * @param contrastRatio calculated contrast ratio
     * @return true if meets AAA standard for normal text
     */
    public boolean meetsWCAG_AAA_NormalText(double contrastRatio) {
        return contrastRatio >= 7.0;
    }
    
    /**
     * Check if contrast ratio meets WCAG 2.1 AAA standard for large text
     * Large text requires minimum 4.5:1 contrast ratio for AAA
     * 
     * @param contrastRatio calculated contrast ratio
     * @return true if meets AAA standard for large text
     */
    public boolean meetsWCAG_AAA_LargeText(double contrastRatio) {
        return contrastRatio >= 4.5;
    }
}
