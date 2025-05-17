package utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 * Utility class for authentication-related operations
 */
public class AuthUtil {
    private static final String USER_SESSION_KEY = "user";
    private static final String REMEMBER_ME_COOKIE = "remember_me";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; // 30 days

    /**
     * Check if a user is logged in
     * @param request HTTP request
     * @return true if user is logged in, false otherwise
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(USER_SESSION_KEY) != null;
    }

    /**
     * Get the currently logged in user
     * @param request HTTP request
     * @return User object if logged in, null otherwise
     */
    public static User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute(USER_SESSION_KEY);
        }
        return null;
    }

    /**
     * Set a user as logged in
     * @param request HTTP request
     * @param user User to set as logged in
     */
    public static void setLoggedInUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_SESSION_KEY, user);
    }

    /**
     * Store user in session (alias for setLoggedInUser)
     * @param request HTTP request
     * @param user User to store in session
     */
    public static void storeUserInSession(HttpServletRequest request, User user) {
        setLoggedInUser(request, user);
    }

    /**
     * Log out the current user
     * @param request HTTP request
     */
    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Set a remember-me cookie
     * @param response HTTP response
     * @param username Username to remember
     * @param token Token for authentication
     */
    public static void setRememberMeCookie(HttpServletResponse response, String username, String token) {
        String cookieValue = username + ":" + token;
        Cookie cookie = new Cookie(REMEMBER_ME_COOKIE, cookieValue);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * Clear the remember-me cookie
     * @param request HTTP request
     * @param response HTTP response
     */
    public static void clearRememberMeCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REMEMBER_ME_COOKIE.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * Check if user has a specific role
     * @param request HTTP request
     * @param role Role to check
     * @return true if user has the role, false otherwise
     */
    public static boolean hasRole(HttpServletRequest request, String role) {
        User user = getLoggedInUser(request);
        return user != null && role.equals(user.getRole());
    }
}
