package controller.admin;

import dal.BookingDAO;
import dal.RoomDAO;
import dal.ServiceDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Booking;
import model.Room;
import model.Service;
import model.User;
import utils.AuthUtil;

/**
 * Controller for the admin dashboard
 */
@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardController extends HttpServlet {

    /**
     * Handles the HTTP GET request for the admin dashboard
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

        // Get data for dashboard
        UserDAO userDAO = new UserDAO();
        RoomDAO roomDAO = new RoomDAO();
        BookingDAO bookingDAO = new BookingDAO();
        ServiceDAO serviceDAO = new ServiceDAO();

        // Get counts for dashboard cards
        int totalUsers = userDAO.getTotalUsers();
        int totalRooms = roomDAO.getTotalRooms();
        int totalBookings = bookingDAO.getTotalBookings();

        // Get recent bookings for dashboard
        List<Booking> recentBookings = bookingDAO.getBookings(1, 5); // Get first 5 bookings

        // Get search parameters
        String searchQuery = request.getParameter("search");
        String roleFilter = request.getParameter("role");
        String statusFilter = request.getParameter("status");

        // Get data for each tab
        List<User> users;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            users = userDAO.searchUsers(searchQuery, roleFilter, statusFilter);
        } else if (roleFilter != null && !roleFilter.isEmpty()) {
            users = userDAO.getUsersByRole(roleFilter, statusFilter);
        } else {
            users = userDAO.getUsers(1, 10); // Get first 10 users
        }
        List<Room> rooms = roomDAO.getRooms(1, 10); // Get first 10 rooms
        List<Booking> bookings = bookingDAO.getBookings(1, 10); // Get first 10 bookings
        List<Service> services = serviceDAO.getServices(1, 10); // Get first 10 services

        // Set data in request for the view
        request.setAttribute("user", user);
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalRooms", totalRooms);
        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("recentBookings", recentBookings);

        // Set data for each tab
        request.setAttribute("users", users);
        request.setAttribute("rooms", rooms);
        request.setAttribute("bookings", bookings);
        request.setAttribute("services", services);

        // Set pagination info for each tab
        request.setAttribute("currentPage", 1);
        request.setAttribute("totalUsersPages", (int) Math.ceil((double) totalUsers / 10));
        request.setAttribute("totalRoomsPages", (int) Math.ceil((double) totalRooms / 10));
        request.setAttribute("totalBookingsPages", (int) Math.ceil((double) totalBookings / 10));
        request.setAttribute("totalServicesPages", (int) Math.ceil((double) serviceDAO.getTotalServices() / 10));

        // Check for success or error messages in session
        String successMessage = (String) request.getSession().getAttribute("successMessage");
        String errorMessage = (String) request.getSession().getAttribute("errorMessage");

        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
            request.getSession().removeAttribute("successMessage");
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }

        // Handle tab parameter for tab navigation
        String tab = request.getParameter("tab");
        if (tab != null && !tab.isEmpty()) {
            request.setAttribute("activeTab", tab);
        }

        // Forward to admin dashboard page
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
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
        // Redirect to GET method
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Admin Dashboard Controller";
    }
}
