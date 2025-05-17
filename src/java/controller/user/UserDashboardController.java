
package controller.user;

import dal.BookingDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Booking;
import model.User;
import utils.AuthUtil;

/**
 * Controller for the user dashboard
 */
@WebServlet(name = "UserDashboardController", urlPatterns = {"/dashboard"})
public class UserDashboardController extends HttpServlet {

    /**
     * Handles the HTTP GET request for the user dashboard
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        User user = AuthUtil.getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // If user is admin, redirect to admin dashboard
        if ("admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Get user's bookings
        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings = bookingDAO.getBookingsByUserId(user.getUserID());

        // Set data in request for the view
        request.setAttribute("user", user);
        request.setAttribute("bookings", bookings);

        // Forward to user dashboard page
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        User user = AuthUtil.getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get action parameter
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        UserDAO userDAO = new UserDAO();

        switch (action) {
            case "updateProfile":
                // Update user profile
                user.setFullName(request.getParameter("fullName"));
                user.setEmail(request.getParameter("email"));
                user.setPhoneNumber(request.getParameter("phoneNumber"));
                user.setGender(request.getParameter("gender"));

                boolean success = userDAO.updateUser(user);

                if (success) {
                    request.getSession().setAttribute("successMessage", "Profile successfully updated.");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to update profile.");
                }

                // Redirect back to dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard#profile");
                break;

            case "changePassword":
                // Change password
                String currentPassword = request.getParameter("currentPassword");
                String newPassword = request.getParameter("newPassword");
                String confirmPassword = request.getParameter("confirmPassword");

                // Validate passwords
                if (!newPassword.equals(confirmPassword)) {
                    request.getSession().setAttribute("errorMessage", "New passwords do not match.");
                    response.sendRedirect(request.getContextPath() + "/dashboard#profile");
                    return;
                }

                // Verify current password
                if (!currentPassword.equals(user.getPassword())) {
                    request.getSession().setAttribute("errorMessage", "Current password is incorrect.");
                    response.sendRedirect(request.getContextPath() + "/dashboard#profile");
                    return;
                }

                // Change password
                success = userDAO.changePassword(user.getUserID(), newPassword);

                if (success) {
                    request.getSession().setAttribute("successMessage", "Password successfully changed.");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to change password.");
                }

                // Redirect back to dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard#profile");
                break;

            default:
                // Unknown action, redirect back to dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard");
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "User Dashboard Controller";
    }
}
