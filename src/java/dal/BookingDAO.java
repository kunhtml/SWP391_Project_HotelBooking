package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import model.Room;
import model.User;

/**
 * Data Access Object for Booking entity
 * Handles database operations related to bookings
 */
public class BookingDAO extends DBContext {

    /**
     * Get all bookings
     * @return List of all bookings
     */
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, u.fullName, u.email, r.roomNumber, r.roomTypeID " +
                     "FROM Bookings b " +
                     "JOIN Users u ON b.userID = u.userID " +
                     "JOIN BookingRooms br ON b.bookingID = br.bookingID " +
                     "JOIN Rooms r ON br.roomID = r.roomID " +
                     "ORDER BY b.bookingDate DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Booking booking = mapBooking(rs);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all bookings: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Get bookings with pagination
     * @param pageNumber Page number (1-based)
     * @param pageSize Number of items per page
     * @return List of bookings for the specified page
     */
    public List<Booking> getBookings(int pageNumber, int pageSize) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, u.fullName, u.email, r.roomNumber, r.roomTypeID " +
                     "FROM Bookings b " +
                     "JOIN Users u ON b.userID = u.userID " +
                     "JOIN BookingRooms br ON b.bookingID = br.bookingID " +
                     "JOIN Rooms r ON br.roomID = r.roomID " +
                     "ORDER BY b.bookingDate DESC " +
                     "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, (pageNumber - 1) * pageSize);
            stmt.setInt(2, pageSize);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = mapBooking(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings with pagination: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Get total number of bookings
     * @return Total number of bookings
     */
    public int getTotalBookings() {
        String sql = "SELECT COUNT(*) FROM Bookings";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting total bookings: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get a booking by ID
     * @param bookingID Booking ID
     * @return Booking if found, null otherwise
     */
    public Booking getBookingById(int bookingID) {
        String sql = "SELECT b.*, u.fullName, u.email, r.roomNumber, r.roomTypeID " +
                     "FROM Bookings b " +
                     "JOIN Users u ON b.userID = u.userID " +
                     "JOIN BookingRooms br ON b.bookingID = br.bookingID " +
                     "JOIN Rooms r ON br.roomID = r.roomID " +
                     "WHERE b.bookingID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookingID);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapBooking(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting booking by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Update booking status
     * @param bookingID Booking ID
     * @param status New status
     * @return true if successful, false otherwise
     */
    public boolean updateBookingStatus(int bookingID, String status) {
        String sql = "UPDATE Bookings SET status = ?, updatedDate = GETDATE() WHERE bookingID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, bookingID);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating booking status: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get bookings by user ID
     * @param userID User ID
     * @return List of bookings for the specified user
     */
    public List<Booking> getBookingsByUserId(int userID) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, u.fullName, u.email, r.roomNumber, r.roomTypeID " +
                     "FROM Bookings b " +
                     "JOIN Users u ON b.userID = u.userID " +
                     "JOIN BookingRooms br ON b.bookingID = br.bookingID " +
                     "JOIN Rooms r ON br.roomID = r.roomID " +
                     "WHERE b.userID = ? " +
                     "ORDER BY b.bookingDate DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userID);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = mapBooking(rs);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings by user ID: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Map a ResultSet row to a Booking object
     * @param rs ResultSet to map
     * @return Mapped Booking object
     * @throws SQLException if a database access error occurs
     */
    private Booking mapBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingID(rs.getInt("bookingID"));
        booking.setCustomerID(rs.getInt("userID"));
        booking.setCheckInDate(rs.getDate("checkInDate"));
        booking.setCheckOutDate(rs.getDate("checkOutDate"));
        booking.setNumberOfGuests(rs.getInt("totalGuests"));
        booking.setBookingDate(rs.getTimestamp("bookingDate"));
        booking.setTotalPrice(rs.getDouble("totalAmount"));
        booking.setBookingStatus(rs.getString("status"));
        booking.setSpecialRequests(rs.getString("specialRequests"));
        
        // Create and set customer
        User customer = new User();
        customer.setUserID(rs.getInt("userID"));
        customer.setFullName(rs.getString("fullName"));
        customer.setEmail(rs.getString("email"));
        booking.setCustomer(customer);
        
        // Create and set room
        Room room = new Room();
        room.setRoomNumber(rs.getString("roomNumber"));
        room.setRoomTypeId(rs.getInt("roomTypeID"));
        booking.setRoom(room);
        
        return booking;
    }
}
