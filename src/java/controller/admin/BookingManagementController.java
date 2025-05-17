package controller.admin;

import dal.BookingDAO;
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
 * Controller for managing bookings (admin functionality)
 */
@WebServlet(name = "BookingManagementController", urlPatterns = {"/admin/bookings"})
public class BookingManagementController extends HttpServlet {

    /**
     * Handles the HTTP GET request for the booking management page
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
        
        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings;
        
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
        
        // Get paginated bookings
        bookings = bookingDAO.getBookings(pageNumber, pageSize);
        
        // Calculate pagination info
        int totalBookings = bookingDAO.getTotalBookings();
        int totalPages = (int) Math.ceil((double) totalBookings / pageSize);
        
        // Set data in request for the view
        request.setAttribute("user", user);
        request.setAttribute("bookings", bookings);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalBookings", totalBookings);
        
        // Forward to admin dashboard with bookings tab active
        response.sendRedirect(request.getContextPath() + "/admin/dashboard#bookings");
    }

    /**
     * Handles the HTTP POST request for booking actions (update status)
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
            response.sendRedirect(request.getContextPath() + "/admin/dashboard#bookings");
            return;
        }
        
        BookingDAO bookingDAO = new BookingDAO();
        
        switch (action) {
            case "updateStatus":
                try {
                    // Update booking status
                    int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                    String status = request.getParameter("status");
                    
                    boolean success = bookingDAO.updateBookingStatus(bookingId, status);
                    
                    if (success) {
                        request.getSession().setAttribute("successMessage", "Booking status successfully updated.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Failed to update booking status.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid booking ID.");
                }
                
                // Redirect back to booking management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#bookings");
                break;
                
            case "view":
                try {
                    // Get booking details
                    int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                    
                    Booking booking = bookingDAO.getBookingById(bookingId);
                    
                    if (booking != null) {
                        request.setAttribute("bookingDetails", booking);
                        request.getRequestDispatcher("/WEB-INF/views/admin/booking-details.jsp").forward(request, response);
                        return;
                    } else {
                        request.getSession().setAttribute("errorMessage", "Booking not found.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid booking ID.");
                }
                
                // Redirect back to booking management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#bookings");
                break;
                
            default:
                // Unknown action, redirect back to booking management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#bookings");
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Booking Management Controller";
    }
}
