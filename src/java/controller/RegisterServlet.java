package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.User;
import util.AuthUtil;

/**
 * Servlet controller for handling user registration
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    /**
     * Handles the HTTP GET request - displays the registration form
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
            // Redirect to home page
            response.sendRedirect("index.html");
            return;
        }

        // Forward to registration page
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request - processes the registration form
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String gender = request.getParameter("gender");

        // Validate input
        boolean hasError = false;

        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("fullNameError", "Full name is required");
            hasError = true;
        }

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("emailError", "Email is required");
            hasError = true;
        } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("emailError", "Invalid email format");
            hasError = true;
        } else if (userDAO.isEmailExists(email)) {
            request.setAttribute("emailError", "Email already exists");
            hasError = true;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password is required");
            hasError = true;
        } else if (password.length() < 6) {
            request.setAttribute("passwordError", "Password must be at least 6 characters");
            hasError = true;
        }

        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("confirmPasswordError", "Please confirm your password");
            hasError = true;
        } else if (!confirmPassword.equals(password)) {
            request.setAttribute("confirmPasswordError", "Passwords do not match");
            hasError = true;
        }

        if (gender == null || gender.trim().isEmpty()) {
            request.setAttribute("genderError", "Please select your gender");
            hasError = true;
        }

        // If there are validation errors, return to the form
        if (hasError) {
            // Keep the entered values
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("gender", gender);

            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Generate a username from the email (before the @ symbol)
        String username = email.substring(0, email.indexOf('@'));

        // Check if username exists and append numbers if needed
        int counter = 1;
        String originalUsername = username;
        while (userDAO.isUsernameExists(username)) {
            username = originalUsername + counter;
            counter++;
        }

        System.out.println("About to create user: " + fullName + ", " + username + ", " + email + ", " + gender);

        // Create new user and save to database
        boolean success = userDAO.createUser(fullName, username, password, email, gender, "user");

        System.out.println("User creation result: " + success);

        if (success) {
            // Set success message and redirect to login page
            System.out.println("Registration successful! Redirecting to login page.");
            request.getSession().setAttribute("registrationSuccess", "Registration successful! Your username is: " + username);
            response.sendRedirect("login");
        } else {
            // Set error message and return to form
            System.out.println("Registration failed. Returning to form.");
            request.setAttribute("error", "Registration failed. Please try again.");
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("gender", gender);

            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Registration Servlet handles user registration";
    }
}
