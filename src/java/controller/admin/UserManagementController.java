package controller.admin;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.User;
import utils.AuthUtil;

/**
 * Controller for managing users (admin functionality)
 */
@WebServlet(name = "UserManagementController", urlPatterns = {"/admin/users"})
public class UserManagementController extends HttpServlet {

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
                    boolean success = userDAO.deleteUser(userId);

                    if (success) {
                        request.getSession().setAttribute("successMessage", "User successfully deleted.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Failed to delete user.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid user ID.");
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
                        // Update user fields
                        userToUpdate.setFullName(request.getParameter("fullName"));
                        userToUpdate.setEmail(request.getParameter("email"));
                        userToUpdate.setRole(request.getParameter("role"));
                        userToUpdate.setGender(request.getParameter("gender"));
                        userToUpdate.setPhoneNumber(request.getParameter("phoneNumber"));
                        userToUpdate.setActive("1".equals(request.getParameter("isActive")));

                        // Update user in database
                        boolean success = userDAO.updateUser(userToUpdate);

                        if (success) {
                            request.getSession().setAttribute("successMessage", "User successfully updated.");
                        } else {
                            request.getSession().setAttribute("errorMessage", "Failed to update user.");
                        }
                    } else {
                        request.getSession().setAttribute("errorMessage", "User not found.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid user ID.");
                }

                // Redirect back to user management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
                break;

            case "create":
                // Create new user
                User newUser = new User();
                newUser.setFullName(request.getParameter("fullName"));
                newUser.setUsername(request.getParameter("username"));
                newUser.setPassword(request.getParameter("password"));
                newUser.setEmail(request.getParameter("email"));
                newUser.setRole(request.getParameter("role"));
                newUser.setGender(request.getParameter("gender"));
                newUser.setPhoneNumber(request.getParameter("phoneNumber"));
                newUser.setActive(true);

                // Create user in database
                boolean success = userDAO.createUser(newUser);

                if (success) {
                    request.getSession().setAttribute("successMessage", "User successfully created.");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to create user.");
                }

                // Redirect back to user management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#users");
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
