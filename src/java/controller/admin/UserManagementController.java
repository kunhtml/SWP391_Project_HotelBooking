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
        
        // Get all users (in a real application, you would implement pagination)
        UserDAO userDAO = new UserDAO();
        // List<User> users = userDAO.getAllUsers(); // This method needs to be implemented
        
        // Set data in request for the view
        request.setAttribute("user", user);
        // request.setAttribute("users", users);
        
        // Forward to user management page
        request.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(request, response);
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
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        
        switch (action) {
            case "delete":
                // Delete user
                int userId = Integer.parseInt(request.getParameter("userId"));
                // boolean success = userDAO.deleteUser(userId); // This method needs to be implemented
                
                // Redirect back to user management page
                response.sendRedirect(request.getContextPath() + "/admin/users");
                break;
                
            case "update":
                // Update user (this would typically be handled by a separate form)
                // Implement user update logic here
                
                // Redirect back to user management page
                response.sendRedirect(request.getContextPath() + "/admin/users");
                break;
                
            default:
                // Unknown action, redirect back to user management page
                response.sendRedirect(request.getContextPath() + "/admin/users");
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
