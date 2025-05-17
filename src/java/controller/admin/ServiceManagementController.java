package controller.admin;

import dal.ServiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Service;
import model.User;
import utils.AuthUtil;

/**
 * Controller for managing services (admin functionality)
 */
@WebServlet(name = "ServiceManagementController", urlPatterns = {"/admin/services"})
public class ServiceManagementController extends HttpServlet {

    /**
     * Handles the HTTP GET request for the service management page
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
        
        ServiceDAO serviceDAO = new ServiceDAO();
        List<Service> services;
        
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
        
        // Get paginated services
        services = serviceDAO.getServices(pageNumber, pageSize);
        
        // Calculate pagination info
        int totalServices = serviceDAO.getTotalServices();
        int totalPages = (int) Math.ceil((double) totalServices / pageSize);
        
        // Set data in request for the view
        request.setAttribute("user", user);
        request.setAttribute("services", services);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalServices", totalServices);
        
        // Forward to admin dashboard with services tab active
        response.sendRedirect(request.getContextPath() + "/admin/dashboard#services");
    }

    /**
     * Handles the HTTP POST request for service actions (create, update, delete)
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
            response.sendRedirect(request.getContextPath() + "/admin/dashboard#services");
            return;
        }
        
        ServiceDAO serviceDAO = new ServiceDAO();
        
        switch (action) {
            case "create":
                try {
                    // Create new service
                    Service newService = new Service();
                    newService.setName(request.getParameter("name"));
                    newService.setDescription(request.getParameter("description"));
                    newService.setPrice(Double.parseDouble(request.getParameter("price")));
                    newService.setCategory(request.getParameter("category"));
                    newService.setHotelID(1); // Default hotel ID
                    
                    int serviceId = serviceDAO.createService(newService);
                    
                    if (serviceId > 0) {
                        request.getSession().setAttribute("successMessage", "Service successfully created.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Failed to create service.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid price value.");
                }
                
                // Redirect back to service management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#services");
                break;
                
            case "update":
                try {
                    // Get service ID
                    int serviceId = Integer.parseInt(request.getParameter("serviceId"));
                    
                    // Get existing service
                    Service serviceToUpdate = serviceDAO.getServiceById(serviceId);
                    
                    if (serviceToUpdate != null) {
                        // Update service fields
                        serviceToUpdate.setName(request.getParameter("name"));
                        serviceToUpdate.setDescription(request.getParameter("description"));
                        serviceToUpdate.setPrice(Double.parseDouble(request.getParameter("price")));
                        serviceToUpdate.setCategory(request.getParameter("category"));
                        
                        // Update service in database
                        boolean success = serviceDAO.updateService(serviceToUpdate);
                        
                        if (success) {
                            request.getSession().setAttribute("successMessage", "Service successfully updated.");
                        } else {
                            request.getSession().setAttribute("errorMessage", "Failed to update service.");
                        }
                    } else {
                        request.getSession().setAttribute("errorMessage", "Service not found.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid service ID or price value.");
                }
                
                // Redirect back to service management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#services");
                break;
                
            case "delete":
                try {
                    // Delete service
                    int serviceId = Integer.parseInt(request.getParameter("serviceId"));
                    boolean success = serviceDAO.deleteService(serviceId);
                    
                    if (success) {
                        request.getSession().setAttribute("successMessage", "Service successfully deleted.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Failed to delete service.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid service ID.");
                }
                
                // Redirect back to service management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#services");
                break;
                
            default:
                // Unknown action, redirect back to service management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#services");
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Service Management Controller";
    }
}
