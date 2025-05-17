package controller.admin;

import dal.RoomDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Room;
import model.User;
import utils.AuthUtil;

/**
 * Controller for managing rooms (admin functionality)
 */
@WebServlet(name = "RoomManagementController", urlPatterns = {"/admin/rooms"})
public class RoomManagementController extends HttpServlet {

    /**
     * Handles the HTTP GET request for the room management page
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
        
        RoomDAO roomDAO = new RoomDAO();
        List<Room> rooms;
        
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
        
        // Get paginated rooms
        rooms = roomDAO.getRooms(pageNumber, pageSize);
        
        // Calculate pagination info
        int totalRooms = roomDAO.getTotalRooms();
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);
        
        // Set data in request for the view
        request.setAttribute("user", user);
        request.setAttribute("rooms", rooms);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRooms", totalRooms);
        
        // Forward to admin dashboard with rooms tab active
        response.sendRedirect(request.getContextPath() + "/admin/dashboard#rooms");
    }

    /**
     * Handles the HTTP POST request for room actions (update status)
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
            response.sendRedirect(request.getContextPath() + "/admin/dashboard#rooms");
            return;
        }
        
        RoomDAO roomDAO = new RoomDAO();
        
        switch (action) {
            case "updateStatus":
                try {
                    // Update room status
                    int roomId = Integer.parseInt(request.getParameter("roomId"));
                    String status = request.getParameter("status");
                    
                    boolean success = roomDAO.updateRoomStatus(roomId, status);
                    
                    if (success) {
                        request.getSession().setAttribute("successMessage", "Room status successfully updated.");
                    } else {
                        request.getSession().setAttribute("errorMessage", "Failed to update room status.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid room ID.");
                }
                
                // Redirect back to room management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#rooms");
                break;
                
            case "update":
                try {
                    // Get room ID
                    int roomId = Integer.parseInt(request.getParameter("roomId"));
                    
                    // Get existing room
                    Room roomToUpdate = roomDAO.getRoomById(roomId);
                    
                    if (roomToUpdate != null) {
                        // Update room fields
                        roomToUpdate.setRoomNumber(request.getParameter("roomNumber"));
                        roomToUpdate.setRoomTypeId(Integer.parseInt(request.getParameter("roomTypeId")));
                        roomToUpdate.setFloor(Integer.parseInt(request.getParameter("floor")));
                        roomToUpdate.setStatus(request.getParameter("status"));
                        
                        // Update room in database
                        boolean success = roomDAO.updateRoom(roomToUpdate);
                        
                        if (success) {
                            request.getSession().setAttribute("successMessage", "Room successfully updated.");
                        } else {
                            request.getSession().setAttribute("errorMessage", "Failed to update room.");
                        }
                    } else {
                        request.getSession().setAttribute("errorMessage", "Room not found.");
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("errorMessage", "Invalid room ID or numeric value.");
                }
                
                // Redirect back to room management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#rooms");
                break;
                
            default:
                // Unknown action, redirect back to room management page
                response.sendRedirect(request.getContextPath() + "/admin/dashboard#rooms");
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Room Management Controller";
    }
}
