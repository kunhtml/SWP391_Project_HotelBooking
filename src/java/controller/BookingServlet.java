package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for handling the booking page
 */
@WebServlet(name = "BookingServlet", urlPatterns = {"/booking"})
public class BookingServlet extends HttpServlet {

    /**
     * Handles the HTTP GET request
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get parameters from the request
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String adults = request.getParameter("adults");
        String children = request.getParameter("children");
        String rooms = request.getParameter("rooms");
        String nights = request.getParameter("nights");
        String roomId = request.getParameter("roomId");

        // If parameters are missing, redirect to search page
        if (checkIn == null || checkOut == null || adults == null || children == null || rooms == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Forward to booking.jsp
        request.getRequestDispatcher("/WEB-INF/views/booking.jsp").forward(request, response);
    }
}
