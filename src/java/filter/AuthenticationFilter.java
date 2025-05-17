package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import utils.AuthUtil;

/**
 * Filter for authentication
 * Restricts access to protected resources for unauthenticated users
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/admin/*", "/profile", "/booking/*"})
public class AuthenticationFilter implements Filter {
    
    /**
     * Default constructor
     */
    public AuthenticationFilter() {
    }
    
    /**
     * Initialize the filter
     * @param filterConfig Filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }
    
    /**
     * Perform the filtering
     * @param request Servlet request
     * @param response Servlet response
     * @param chain Filter chain
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Check if user is logged in
        if (AuthUtil.isLoggedIn(httpRequest)) {
            // User is authenticated, continue the request
            chain.doFilter(request, response);
        } else {
            // User is not authenticated, redirect to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
    
    /**
     * Clean up resources
     */
    @Override
    public void destroy() {
    }
}
