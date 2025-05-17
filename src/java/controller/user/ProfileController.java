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
 * Controller for the user profile page
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {

    /**
     * Handles the HTTP GET request for the profile page
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

        // Get user's bookings
        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings = bookingDAO.getBookingsByUserId(user.getUserID());

        // Set data in request for the view
        request.setAttribute("user", user);
        request.setAttribute("bookings", bookings);

        // Forward to dashboard page with profile tab active
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request for updating profile
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

        // Get form data
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String gender = request.getParameter("gender");

        // Update user profile
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);

        // Save changes to database
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.updateUser(user);

        // Set success or error message
        if (success) {
            request.getSession().setAttribute("successMessage", "Profile successfully updated.");
            // Update session user
            request.getSession().setAttribute("user", user);
        } else {
            request.getSession().setAttribute("errorMessage", "Failed to update profile.");
        }

        // Redirect back to profile
        response.sendRedirect(request.getContextPath() + "/profile");
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Profile Controller";
    }
}
