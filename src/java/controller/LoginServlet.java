package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.User;
import util.AuthUtil;
import util.CookieUtil;

/**
 * Servlet controller for handling login operations
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    /**
     * Handles the HTTP GET request - displays the login form
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
            // Redirect to dashboard
            response.sendRedirect("dashboard");
            return;
        }

        // Forward to login page
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request - processes the login form
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
        String remember = request.getParameter("remember");

        // Validate input
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {

            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        // Check if there's a remember-me cookie
        String cookieUsername = CookieUtil.getCookieValue(request, "remember_username");
        if (cookieUsername != null && username.isEmpty()) {
            username = cookieUsername;
            // You might want to pre-fill the username field in the form
            request.setAttribute("username", username);
        }

        // Authenticate user
        User user = userDAO.authenticate(username, password);

        if (user != null) {
            // Login successful
            AuthUtil.storeUserInSession(request, user);



            // Handle remember-me functionality
            if (remember != null) {
                // Store username in a cookie that lasts for 30 days
                CookieUtil.addCookie(response, "remember_username", username, 30 * 24 * 60 * 60);
            } else {
                // Delete the cookie if "remember me" is not checked
                CookieUtil.deleteCookie(request, response, "remember_username");
            }

            // Update last login time
            userDAO.updateLastLogin(user.getUserID());

            // Redirect based on user role
            if ("admin".equals(user.getRole())) {
                response.sendRedirect("admin/dashboard");
            } else {
                response.sendRedirect("dashboard");
            }
        } else {
            // Login failed
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
        return "Login Servlet handles user authentication";
    }
}
