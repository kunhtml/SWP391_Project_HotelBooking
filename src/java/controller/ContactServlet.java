package controller;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.SettingsDAO;

/**
 * Servlet for handling the contact page
 */
@WebServlet(name = "ContactServlet", urlPatterns = {"/contact"})
public class ContactServlet extends HttpServlet {

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
        // Set UTF-8 encoding for request and response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // Get settings from database
        SettingsDAO settingsDAO = new SettingsDAO();
        Map<String, String> settings = settingsDAO.getSettingsMap();

        // Add Vietnamese address with proper encoding
        settings.put("contact_address_vi", "Khu Công Nghệ Cao Hòa Lạc, km 29, Đại lộ Thăng Long, Hà Nội");

        // Set settings as request attribute
        request.setAttribute("contactSettings", settings);

        // Forward to contact page
        request.getRequestDispatcher("/WEB-INF/views/contact.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request for contact form submission
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set UTF-8 encoding for request and response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        // TODO: Implement contact form submission logic
        // For now, just add a success message and redirect back to contact page

        request.getSession().setAttribute("successMessage", "Your message has been sent successfully. We will get back to you soon!");

        // Get settings from database for the redirect
        SettingsDAO settingsDAO = new SettingsDAO();
        Map<String, String> settings = settingsDAO.getSettingsMap();

        // Add Vietnamese address with proper encoding
        settings.put("contact_address_vi", "Khu Công Nghệ Cao Hòa Lạc, km 29, Đại lộ Thăng Long, Hà Nội");

        // Set settings as request attribute
        request.setAttribute("contactSettings", settings);

        response.sendRedirect("contact");
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Contact Servlet";
    }
}
