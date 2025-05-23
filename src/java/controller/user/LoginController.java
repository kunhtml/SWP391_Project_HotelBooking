package controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.User;
import dal.UserDAO;
import utils.AuthUtil;

/**
 * Controller for handling login requests
 * Simple implementation without complex password handling
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    /**
     * Handles the HTTP GET request for the login page
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        if (AuthUtil.isLoggedIn(request)) {
            response.sendRedirect("home");
            return;
        }
        
        // Check for remember-me cookie
        String rememberedUsername = AuthUtil.getRememberedUsername(request);
        if (rememberedUsername != null) {
            request.setAttribute("rememberedUsername", rememberedUsername);
        }
        
        // Forward to login page
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request for login form submission
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean rememberMe = request.getParameter("rememberMe") != null;
        
        // Validate input
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        // Find user by username, email, or phone number
        UserDAO userDAO = new UserDAO();
        User user = null;
        
        // Try to find user by username
        user = userDAO.getUserByUsername(username);
        
        // If not found, try by email
        if (user == null && username.contains("@")) {
            user = userDAO.getUserByEmail(username);
        }
        
        // If still not found, try by phone number
        if (user == null && username.matches("\\d+")) {
            user = userDAO.getUserByPhoneNumber(username);
        }
        
        // Check if user exists and password is correct
        if (user != null && password.equals(user.getPassword())) {
            // Update last login time
            userDAO.updateLastLogin(user.getUserID());
            
            // Set user in session
            AuthUtil.setLoggedInUser(request, user);
            
            // Handle remember me
            if (rememberMe) {
                AuthUtil.setRememberMeCookie(response, user.getUsername());
            }
            
            // Redirect to home page
            response.sendRedirect("home");
        } else {
            // Authentication failed
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login Controller";
    }
}
