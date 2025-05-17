package utils;

import jakarta.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for handling request parameters
 */
public class RequestUtil {
    
    /**
     * Get a string parameter from the request
     * @param request HTTP request
     * @param name Parameter name
     * @param defaultValue Default value if parameter is not found
     * @return Parameter value or default value
     */
    public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
    
    /**
     * Get an integer parameter from the request
     * @param request HTTP request
     * @param name Parameter name
     * @param defaultValue Default value if parameter is not found or invalid
     * @return Parameter value or default value
     */
    public static int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        String value = request.getParameter(name);
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
    
    /**
     * Get a double parameter from the request
     * @param request HTTP request
     * @param name Parameter name
     * @param defaultValue Default value if parameter is not found or invalid
     * @return Parameter value or default value
     */
    public static double getDoubleParameter(HttpServletRequest request, String name, double defaultValue) {
        String value = request.getParameter(name);
        if (value != null && !value.isEmpty()) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
    
    /**
     * Get a boolean parameter from the request
     * @param request HTTP request
     * @param name Parameter name
     * @param defaultValue Default value if parameter is not found
     * @return Parameter value or default value
     */
    public static boolean getBooleanParameter(HttpServletRequest request, String name, boolean defaultValue) {
        String value = request.getParameter(name);
        if (value != null && !value.isEmpty()) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }
    
    /**
     * Get a date parameter from the request
     * @param request HTTP request
     * @param name Parameter name
     * @param format Date format (e.g., "yyyy-MM-dd")
     * @param defaultValue Default value if parameter is not found or invalid
     * @return Parameter value or default value
     */
    public static Date getDateParameter(HttpServletRequest request, String name, String format, Date defaultValue) {
        String value = request.getParameter(name);
        if (value != null && !value.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                return dateFormat.parse(value);
            } catch (ParseException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
    
    /**
     * Check if a parameter exists in the request
     * @param request HTTP request
     * @param name Parameter name
     * @return true if parameter exists, false otherwise
     */
    public static boolean hasParameter(HttpServletRequest request, String name) {
        return request.getParameter(name) != null;
    }
    
    /**
     * Get the client's IP address
     * @param request HTTP request
     * @return Client IP address
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
