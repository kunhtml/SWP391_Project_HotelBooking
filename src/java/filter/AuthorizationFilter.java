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
import model.User;
import utils.AuthUtil;

/**
 * Filter for authorization
 * Restricts access to admin resources for non-admin users
 */
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/admin/*"})
public class AuthorizationFilter implements Filter {
    
    /**
     * Default constructor
     */
    public AuthorizationFilter() {
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
        
        // Get the logged in user
        User user = AuthUtil.getLoggedInUser(httpRequest);
        
        // Check if user has admin role
        if (user != null && "admin".equals(user.getRole())) {
            // User is authorized, continue the request
            chain.doFilter(request, response);
        } else {
            // User is not authorized, redirect to access denied page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/access-denied");
        }
    }
    
    /**
     * Clean up resources
     */
    @Override
    public void destroy() {
    }
}
