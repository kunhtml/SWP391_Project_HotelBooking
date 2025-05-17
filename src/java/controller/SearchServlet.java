package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import model.Room;
import model.RoomType;

/**
 * Servlet for handling room search functionality
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

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
        
        // Get search parameters
        String checkInStr = request.getParameter("check-in");
        String checkOutStr = request.getParameter("check-out");
        String adultsStr = request.getParameter("adults");
        String childrenStr = request.getParameter("children");
        String roomsStr = request.getParameter("rooms");
        
        // Validate input
        if (checkInStr == null || checkOutStr == null || 
            adultsStr == null || childrenStr == null || roomsStr == null) {
            
            request.setAttribute("error", "All search parameters are required");
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
            return;
        }
        
        try {
            // Parse dates
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate = sdf.parse(checkInStr);
            Date checkOutDate = sdf.parse(checkOutStr);
            
            // Calculate number of nights
            long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
            long nights = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            
            // Parse other parameters
            int adults = Integer.parseInt(adultsStr);
            int children = Integer.parseInt(childrenStr);
            int roomCount = Integer.parseInt(roomsStr);
            
            // Store search parameters in request attributes
            request.setAttribute("checkIn", checkInStr);
            request.setAttribute("checkOut", checkOutStr);
            request.setAttribute("adults", adults);
            request.setAttribute("children", children);
            request.setAttribute("roomCount", roomCount);
            request.setAttribute("nights", nights);
            
            // In a real application, you would search the database for available rooms
            // For now, we'll create some dummy data
            List<Room> availableRooms = getDummyRooms(adults, children, roomCount);
            request.setAttribute("availableRooms", availableRooms);
            
            // Create suggestions
            List<Object[]> suggestions = createSuggestions(checkInDate, checkOutDate, adults, children, roomCount);
            request.setAttribute("suggestions", suggestions);
            
            // Forward to search results page
            request.getRequestDispatcher("/WEB-INF/views/search-results.jsp").forward(request, response);
            
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format");
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles the HTTP POST request - same as GET for this servlet
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Create dummy room data for demonstration
     * In a real application, this would come from the database
     */
    private List<Room> getDummyRooms(int adults, int children, int roomCount) {
        List<Room> rooms = new ArrayList<>();
        
        // Create some room types
        RoomType standard = new RoomType(1, "Standard Room", "Comfortable room with all the essentials.", 99.0, 2, 25, "room1.jpg");
        RoomType deluxe = new RoomType(2, "Deluxe Room", "Spacious room with additional amenities and city views.", 149.0, 2, 35, "room2.jpg");
        RoomType suite = new RoomType(3, "Luxury Suite", "Premium suite with separate living area and panoramic views.", 249.0, 4, 50, "room3.jpg");
        RoomType family = new RoomType(4, "Family Room", "Spacious room designed for families with children.", 179.0, 4, 40, "room4.jpg");
        
        // Add rooms based on search criteria
        if (adults <= 2 && children == 0) {
            // For couples or single travelers
            rooms.add(new Room(101, "101", 1, standard.getId(), 1, "Available"));
            rooms.add(new Room(102, "102", 1, standard.getId(), 1, "Available"));
            rooms.add(new Room(201, "201", 1, deluxe.getId(), 2, "Available"));
            standard.setAvailableRooms(2);
            deluxe.setAvailableRooms(1);
        }
        
        if (adults + children <= 4) {
            // For families or groups
            rooms.add(new Room(301, "301", 1, suite.getId(), 3, "Available"));
            rooms.add(new Room(302, "302", 1, family.getId(), 3, "Available"));
            suite.setAvailableRooms(1);
            family.setAvailableRooms(1);
        }
        
        // Set room types for each room
        for (Room room : rooms) {
            if (room.getRoomTypeId() == standard.getId()) {
                room.setRoomType(standard);
            } else if (room.getRoomTypeId() == deluxe.getId()) {
                room.setRoomType(deluxe);
            } else if (room.getRoomTypeId() == suite.getId()) {
                room.setRoomType(suite);
            } else if (room.getRoomTypeId() == family.getId()) {
                room.setRoomType(family);
            }
        }
        
        return rooms;
    }
    
    /**
     * Create suggestions based on search criteria
     */
    private List<Object[]> createSuggestions(Date checkIn, Date checkOut, int adults, int children, int roomCount) {
        List<Object[]> suggestions = new ArrayList<>();
        
        // Suggestion 1: Stay one more night
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkOut);
        calendar.add(Calendar.DATE, 1);
        Date extendedCheckOut = calendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Object[] suggestion1 = {
            "Extended Stay",
            "Stay one more night and enjoy more of our amenities.",
            sdf.format(checkIn),
            sdf.format(extendedCheckOut),
            adults,
            children,
            roomCount
        };
        suggestions.add(suggestion1);
        
        // Suggestion 2: Upgrade to a better room
        Object[] suggestion2 = {
            "Room Upgrade",
            "Upgrade to a deluxe room for a more comfortable stay.",
            sdf.format(checkIn),
            sdf.format(checkOut),
            adults,
            children,
            roomCount
        };
        suggestions.add(suggestion2);
        
        // Suggestion 3: Family package
        if (adults == 2 && children == 0) {
            Object[] suggestion3 = {
                "Family Package",
                "Planning to bring kids? Check our family rooms.",
                sdf.format(checkIn),
                sdf.format(checkOut),
                2,
                2,
                roomCount
            };
            suggestions.add(suggestion3);
        }
        
        return suggestions;
    }
}
