package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Room;
import model.RoomType;

/**
 * Data Access Object for Room entity
 * Handles database operations related to rooms
 */
public class RoomDAO extends DBContext {

    /**
     * Get all rooms
     * @return List of all rooms
     */
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.*, rt.name as roomTypeName, rt.basePrice, rt.capacity, rt.bedType " +
                     "FROM Rooms r " +
                     "JOIN RoomTypes rt ON r.roomTypeID = rt.roomTypeID " +
                     "ORDER BY r.roomNumber";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Room room = mapRoom(rs);
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all rooms: " + e.getMessage());
        }
        return rooms;
    }
    
    /**
     * Get rooms with pagination
     * @param pageNumber Page number (1-based)
     * @param pageSize Number of items per page
     * @return List of rooms for the specified page
     */
    public List<Room> getRooms(int pageNumber, int pageSize) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.*, rt.name as roomTypeName, rt.basePrice, rt.capacity, rt.bedType " +
                     "FROM Rooms r " +
                     "JOIN RoomTypes rt ON r.roomTypeID = rt.roomTypeID " +
                     "ORDER BY r.roomNumber " +
                     "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, (pageNumber - 1) * pageSize);
            stmt.setInt(2, pageSize);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Room room = mapRoom(rs);
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting rooms with pagination: " + e.getMessage());
        }
        return rooms;
    }
    
    /**
     * Get total number of rooms
     * @return Total number of rooms
     */
    public int getTotalRooms() {
        String sql = "SELECT COUNT(*) FROM Rooms";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting total rooms: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get a room by ID
     * @param roomID Room ID
     * @return Room if found, null otherwise
     */
    public Room getRoomById(int roomID) {
        String sql = "SELECT r.*, rt.name as roomTypeName, rt.basePrice, rt.capacity, rt.bedType " +
                     "FROM Rooms r " +
                     "JOIN RoomTypes rt ON r.roomTypeID = rt.roomTypeID " +
                     "WHERE r.roomID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, roomID);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRoom(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting room by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Update room status
     * @param roomID Room ID
     * @param status New status
     * @return true if successful, false otherwise
     */
    public boolean updateRoomStatus(int roomID, String status) {
        String sql = "UPDATE Rooms SET status = ?, updatedDate = GETDATE() WHERE roomID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, roomID);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating room status: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update room
     * @param room Room to update
     * @return true if successful, false otherwise
     */
    public boolean updateRoom(Room room) {
        String sql = "UPDATE Rooms SET roomTypeID = ?, roomNumber = ?, floor = ?, " +
                     "status = ?, isActive = ?, updatedDate = GETDATE() " +
                     "WHERE roomID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, room.getRoomTypeId());
            stmt.setString(2, room.getRoomNumber());
            stmt.setString(3, String.valueOf(room.getFloor()));
            stmt.setString(4, room.getStatus());
            stmt.setBoolean(5, true); // isActive
            stmt.setInt(6, room.getRoomID());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating room: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Map a ResultSet row to a Room object
     * @param rs ResultSet to map
     * @return Mapped Room object
     * @throws SQLException if a database access error occurs
     */
    private Room mapRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomID(rs.getInt("roomID"));
        room.setRoomNumber(rs.getString("roomNumber"));
        room.setRoomTypeId(rs.getInt("roomTypeID"));
        room.setFloor(rs.getString("floor") != null ? Integer.parseInt(rs.getString("floor")) : 0);
        room.setStatus(rs.getString("status"));
        
        // Create and set room type
        RoomType roomType = new RoomType();
        roomType.setId(rs.getInt("roomTypeID"));
        roomType.setName(rs.getString("roomTypeName"));
        roomType.setPrice(rs.getDouble("basePrice"));
        roomType.setCapacity(rs.getInt("capacity"));
        room.setRoomType(roomType);
        
        return room;
    }

    /**
     * Search for available rooms based on check-in, check-out dates, guests, and room count
     * @param checkInDate Check-in date
     * @param checkOutDate Check-out date
     * @param totalGuests Total number of guests
     * @param roomCount Number of rooms requested
     * @return List of available rooms
     */
    public List<Room> searchAvailableRooms(Date checkInDate, Date checkOutDate, int totalGuests, int roomCount) {
        List<Room> availableRooms = new ArrayList<>();
        String sql = "SELECT DISTINCT r.*, rt.name as roomTypeName, rt.basePrice, rt.capacity, rt.bedType " +
                     "FROM Rooms r " +
                     "JOIN RoomTypes rt ON r.roomTypeID = rt.roomTypeID " +
                     "WHERE rt.capacity >= ? " +
                     "AND r.status = 'Available' " +
                     "AND r.roomID NOT IN ( " +
                     "    SELECT DISTINCT br.roomID " +
                     "    FROM BookingRooms br " +
                     "    JOIN Bookings b ON br.bookingID = b.bookingID " +
                     "    WHERE b.status != 'Cancelled' " +
                     "    AND ( " +
                     "        (? < b.checkOutDate AND ? > b.checkInDate) " +
                     "        OR (? <= b.checkOutDate AND ? >= b.checkInDate) " +
                     "    ) " +
                     ") " +
                     "ORDER BY rt.basePrice " +
                     "OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, totalGuests);
            stmt.setDate(2, new java.sql.Date(checkOutDate.getTime()));
            stmt.setDate(3, new java.sql.Date(checkInDate.getTime()));
            stmt.setDate(4, new java.sql.Date(checkInDate.getTime()));
            stmt.setDate(5, new java.sql.Date(checkOutDate.getTime()));
            stmt.setInt(6, roomCount);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Room room = mapRoom(rs);
                    availableRooms.add(room);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching available rooms: " + e.getMessage());
        }
        
        return availableRooms;
    }
    
    /**
     * Add a new room to the database
     * @param room Room to add
     * @return true if successful, false otherwise
     */
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO Rooms (roomNumber, roomTypeID, hotelID, floor, status, isActive, createdDate) " +
                     "VALUES (?, ?, ?, ?, ?, 1, GETDATE())";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, room.getRoomNumber());
            stmt.setInt(2, room.getRoomTypeId());
            stmt.setInt(3, room.getHotelID());
            stmt.setString(4, String.valueOf(room.getFloor()));
            stmt.setString(5, room.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get all room types
     * @return List of all room types
     */
    public List<RoomType> getAllRoomTypes() {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM RoomTypes WHERE isActive = 1 ORDER BY name";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                RoomType roomType = new RoomType();
                roomType.setId(rs.getInt("roomTypeID"));
                roomType.setName(rs.getString("name"));
                roomType.setDescription(rs.getString("description"));
                roomType.setPrice(rs.getDouble("basePrice"));
                roomType.setCapacity(rs.getInt("capacity"));
                roomType.setArea(rs.getInt("capacity")); // Using capacity as area for now
                roomType.setImageUrl(rs.getString("imageURL"));
                roomTypes.add(roomType);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all room types: " + e.getMessage());
        }
        return roomTypes;
    }
}
