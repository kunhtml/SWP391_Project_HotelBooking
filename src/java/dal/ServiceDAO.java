package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Service;

/**
 * Data Access Object for Service entity
 * Handles database operations related to services
 */
public class ServiceDAO extends DBContext {

    /**
     * Get all services
     * @return List of all services
     */
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM Services WHERE isActive = 1 ORDER BY name";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Service service = mapService(rs);
                services.add(service);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all services: " + e.getMessage());
        }
        return services;
    }
    
    /**
     * Get services with pagination
     * @param pageNumber Page number (1-based)
     * @param pageSize Number of items per page
     * @return List of services for the specified page
     */
    public List<Service> getServices(int pageNumber, int pageSize) {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM Services WHERE isActive = 1 " +
                     "ORDER BY name " +
                     "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, (pageNumber - 1) * pageSize);
            stmt.setInt(2, pageSize);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Service service = mapService(rs);
                    services.add(service);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting services with pagination: " + e.getMessage());
        }
        return services;
    }
    
    /**
     * Get total number of services
     * @return Total number of services
     */
    public int getTotalServices() {
        String sql = "SELECT COUNT(*) FROM Services WHERE isActive = 1";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting total services: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get a service by ID
     * @param serviceID Service ID
     * @return Service if found, null otherwise
     */
    public Service getServiceById(int serviceID) {
        String sql = "SELECT * FROM Services WHERE serviceID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, serviceID);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapService(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting service by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Create a new service
     * @param service Service to create
     * @return ID of the created service, or -1 if failed
     */
    public int createService(Service service) {
        String sql = "INSERT INTO Services (hotelID, name, description, price, category, isActive) " +
                     "VALUES (?, ?, ?, ?, ?, 1); SELECT SCOPE_IDENTITY() AS ID";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, service.getHotelID());
            stmt.setString(2, service.getName());
            stmt.setString(3, service.getDescription());
            stmt.setDouble(4, service.getPrice());
            stmt.setString(5, service.getCategory());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating service: " + e.getMessage());
        }
        return -1;
    }
    
    /**
     * Update a service
     * @param service Service to update
     * @return true if successful, false otherwise
     */
    public boolean updateService(Service service) {
        String sql = "UPDATE Services SET name = ?, description = ?, price = ?, " +
                     "category = ?, updatedDate = GETDATE() " +
                     "WHERE serviceID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, service.getName());
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getPrice());
            stmt.setString(4, service.getCategory());
            stmt.setInt(5, service.getServiceID());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating service: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete a service (soft delete by setting isActive to 0)
     * @param serviceID Service ID
     * @return true if successful, false otherwise
     */
    public boolean deleteService(int serviceID) {
        String sql = "UPDATE Services SET isActive = 0, updatedDate = GETDATE() WHERE serviceID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, serviceID);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting service: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Map a ResultSet row to a Service object
     * @param rs ResultSet to map
     * @return Mapped Service object
     * @throws SQLException if a database access error occurs
     */
    private Service mapService(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setServiceID(rs.getInt("serviceID"));
        service.setHotelID(rs.getInt("hotelID"));
        service.setName(rs.getString("name"));
        service.setDescription(rs.getString("description"));
        service.setPrice(rs.getDouble("price"));
        service.setCategory(rs.getString("category"));
        service.setActive(rs.getBoolean("isActive"));
        return service;
    }
}
