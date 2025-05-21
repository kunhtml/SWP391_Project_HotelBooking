package controller.admin;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import utils.AuthUtil;

/**
 * Controller for managing users (admin functionality)
 */
@WebServlet(name = "UserManagementController", urlPatterns = {"/admin/users"})
public class UserManagementController extends HttpServlet {

    // Use java.util.logging instead of SLF4J
    private static final Logger LOGGER = Logger.getLogger(UserManagementController.class.getName());

    /**
     * Handles the HTTP GET request for the user management page
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in and has admin role
        User user = AuthUtil.getLoggedInUser(request);
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/access-denied");
            return;
        }

        UserDAO userDAO = new UserDAO();
        List<User> users;

        // Get pagination parameters
        int pageNumber = 1;
        int pageSize = 10;

        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                pageNumber = Integer.parseInt(pageParam);
                if (pageNumber < 1) {
                    pageNumber = 1;
                }
            }
        } catch (NumberFormatException e) {
            // If page parameter is invalid, default to page 1
            pageNumber = 1;
        }

        // Get search and filter parameters
        String searchTerm = request.getParameter("search");
        String roleFilter = request.getParameter("role");
        String statusFilter = request.getParameter("status");

        // Apply search and filters if provided
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            users = userDAO.searchUsers(searchTerm);
            request.setAttribute("searchTerm", searchTerm);
        } else if ((roleFilter != null && !roleFilter.isEmpty()) ||
                  (statusFilter != null && !statusFilter.isEmpty())) {
            // Convert status string to Boolean
            Boolean isActive = null;
            if (statusFilter != null && !statusFilter.isEmpty()) {
                if ("1".equals(statusFilter)) {
                    isActive = true;
                } else if ("0".equals(statusFilter)) {
                    isActive = false;
                }
            }

            users = userDAO.filterUsers(roleFilter, isActive);
            request.setAttribute("roleFilter", roleFilter);
            request.setAttribute("statusFilter", statusFilter);
        } else {
            // Get paginated users
            users = userDAO.getUsers(pageNumber, pageSize);

            // Calculate pagination info
            int totalUsers = userDAO.getTotalUsers();
            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalUsers", totalUsers);
        }

        // Set data in request for the view
        request.setAttribute("user", user);
        request.setAttribute("users", users);

        // Forward to admin dashboard with users tab active
        response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
    }

    /**
     * Handles the HTTP POST request for user actions (create, update, delete)
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in and has admin role
        User user = AuthUtil.getLoggedInUser(request);
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/access-denied");
            return;
        }

        // Get action parameter
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
            return;
        }

        UserDAO userDAO = new UserDAO();

        switch (action) {
            case "delete":
                try {
                    // Delete user
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    boolean deleteSuccess = userDAO.deleteUser(userId);

                    if (deleteSuccess) {
                        request.getSession().setAttribute("successMessage", "User successfully deleted.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Failed to delete user.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid user ID.");
                    LOGGER.log(Level.WARNING, "Invalid user ID during deletion", e);
                }

                // Redirect back to user management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
                break;

            case "update":
                try {
                    // Get user ID
                    int userId = Integer.parseInt(request.getParameter("userId"));

                    // Get existing user
                    User userToUpdate = userDAO.getUserByID(userId);

                    if (userToUpdate != null) {
                        // Check if the user is trying to modify their own role
                        if (user.getUserID() == userId && !userToUpdate.getRole().equals(request.getParameter("role"))) {
                            // Prevent changing own role
                            request.getSession().setAttribute("errorMessage", "You cannot change your own role.");
                            response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
                            return;
                        }

                        // Update user fields
                        userToUpdate.setFullName(request.getParameter("fullName"));
                        userToUpdate.setEmail(request.getParameter("email"));
                        userToUpdate.setRole(request.getParameter("role"));
                        userToUpdate.setGender(request.getParameter("gender"));
                        userToUpdate.setPhoneNumber(request.getParameter("phoneNumber"));
                        userToUpdate.setActive("1".equals(request.getParameter("isActive")));

                        // Update user in database
                        boolean updateSuccess = userDAO.updateUser(userToUpdate);

                        if (updateSuccess) {
                            request.getSession().setAttribute("successMessage", "User successfully updated.");
                        } else {
                            request.getSession().setAttribute("errorMessage", "Failed to update user.");
                        }
                    } else {
                        request.getSession().setAttribute("errorMessage", "User not found.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid user ID.");
                    LOGGER.log(Level.WARNING, "Invalid user ID during update", e);
                }

                // Redirect back to user management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
                break;

            case "create":
                try {
                    // Validate input fields
                    String fullName = request.getParameter("fullName");
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String confirmPassword = request.getParameter("confirmPassword");
                    String email = request.getParameter("email");
                    String role = request.getParameter("role");
                    String gender = request.getParameter("gender");
                    String phoneNumber = request.getParameter("phoneNumber");

                    // Validate required fields
                    if (fullName == null || fullName.trim().isEmpty() ||
                        username == null || username.trim().isEmpty() ||
                        password == null || password.trim().isEmpty() ||
                        email == null || email.trim().isEmpty() ||
                        role == null || role.trim().isEmpty()) {
                        request.getSession().setAttribute("errorMessage", "All required fields must be filled.");
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
                        return;
                    }

                    // Validate password match
                    if (!password.equals(confirmPassword)) {
                        request.getSession().setAttribute("errorMessage", "Passwords do not match.");
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
                        return;
                    }

                    // Check if username already exists
                    if (userDAO.getUserByUsername(username) != null) {
                        request.getSession().setAttribute("errorMessage", "Username already exists.");
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
                        return;
                    }

                    // Create new user object
                    User newUser = new User();
                    newUser.setFullName(fullName);
                    newUser.setUsername(username);
                    newUser.setPassword(password); // Note: In a real app, hash the password
                    newUser.setEmail(email);
                    newUser.setRole(role);
                    newUser.setGender(gender);
                    newUser.setPhoneNumber(phoneNumber);
                    newUser.setActive(true); // Default to active

                    // Attempt to create user
                    boolean createSuccess = userDAO.createUser(newUser);

                    if (createSuccess) {
                        request.getSession().setAttribute("successMessage", "User successfully created.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Failed to create user. Please try again.");
                    }
                } catch (Exception e) {
                    // Log the exception
                    LOGGER.log(Level.SEVERE, "Error creating user", e);
                    request.getSession().setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
                }

                // Redirect back to user management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
                break;

            case "changePassword":
                try {
                    // Get user ID
                    int userId = Integer.parseInt(request.getParameter("userId"));

                    // Get current password from form
                    String currentPassword = request.getParameter("currentPassword");
                    String newPassword = request.getParameter("newPassword");
                    String confirmPassword = request.getParameter("confirmPassword");

                    // Get user from database to verify current password
                    User userToUpdate = userDAO.getUserByID(userId);

                    // Validate inputs
                    if (userToUpdate == null) {
                        request.getSession().setAttribute("errorMessage", "User not found.");
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard#settings");
                        return;
                    }

                    // Validate passwords
                    if (!newPassword.equals(confirmPassword)) {
                        request.getSession().setAttribute("errorMessage", "New passwords do not match.");
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard#settings");
                        return;
                    }

                    // Verify current password
                    if (!userDAO.verifyPassword(userId, currentPassword)) {
                        request.getSession().setAttribute("errorMessage", "Current password is incorrect.");
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard#settings");
                        return;
                    }

                    // Change password in database
                    boolean changePasswordSuccess = userDAO.changePassword(userId, newPassword);

                    if (changePasswordSuccess) {
                        // Update the user in session if it's the same user
                        if (user.getUserID() == userId) {
                            user.setPassword(newPassword);
                            request.getSession().setAttribute("user", user);
                        }
                        request.getSession().setAttribute("successMessage", "Password successfully changed.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Failed to change password.");
                    }

                    // Redirect back to settings page
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard#settings");
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid user ID.");
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard#settings");
                }
                break;

            default:
                // Unknown action, redirect back to user management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "User Management Controller";
    }
}
