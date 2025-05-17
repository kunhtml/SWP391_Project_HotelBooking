package dal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.User;
import utils.PasswordUtil;

/**
 * Data Access Object for User entity
 * Handles database operations related to users
 */
public class UserDAO extends DBContext {

    /**
     * Get a user by username
     * @param username Username to search for
     * @return User if found, null otherwise
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by username: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get a user by email
     * @param email Email to search for
     * @return User if found, null otherwise
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by email: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get a user by phone number
     * @param phoneNumber Phone number to search for
     * @return User if found, null otherwise
     */
    public User getUserByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM Users WHERE phoneNumber = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by phone number: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get a user by ID
     * @param userID User ID to search for
     * @return User if found, null otherwise
     */
    public User getUserById(int userID) {
        String sql = "SELECT * FROM Users WHERE userID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Create a new user
     * @param user User to create
     * @return true if successful, false otherwise
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (fullName, username, passwordHash, salt, email, role, gender, phoneNumber, isGroup, isActive, createdDate) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Generate salt and hash password
            byte[] salt = PasswordUtil.generateSalt();
            byte[] passwordHash = PasswordUtil.hashPassword(user.getPassword(), salt);

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUsername());
            stmt.setBytes(3, passwordHash);
            stmt.setBytes(4, salt);
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getRole());

            // Handle gender which might be null
            if (user.getGender() != null && !user.getGender().isEmpty()) {
                stmt.setString(7, user.getGender());
            } else {
                stmt.setNull(7, java.sql.Types.NVARCHAR);
            }

            stmt.setString(8, user.getPhoneNumber());
            stmt.setBoolean(9, user.getIsGroup());
            stmt.setBoolean(10, user.getIsActive());
            stmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));

            // Log the SQL and parameters for debugging
            logSqlWithParams(sql, user);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for more details
            return false;
        }
    }

    /**
     * Log SQL statement with parameters for debugging
     * @param sql SQL statement
     * @param user User object with parameters
     */
    private void logSqlWithParams(String sql, User user) {
        System.out.println("=== DATABASE OPERATION DEBUG INFO ===");
        System.out.println("Executing SQL: " + sql);
        System.out.println("Parameters:");
        System.out.println("  fullName: " + user.getFullName());
        System.out.println("  username: " + user.getUsername());
        System.out.println("  email: " + user.getEmail());
        System.out.println("  role: " + user.getRole());
        System.out.println("  gender: " + (user.getGender() != null ? user.getGender() : "NULL"));
        System.out.println("  phoneNumber: " + user.getPhoneNumber());
        System.out.println("  isGroup: " + user.getIsGroup());
        System.out.println("  isActive: " + user.getIsActive());
        System.out.println("  hotelID: " + user.getHotelID());

        // Check for potential issues
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            System.out.println("WARNING: fullName is empty");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("WARNING: username is empty");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            System.out.println("WARNING: email is empty");
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            System.out.println("WARNING: role is empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            System.out.println("WARNING: password is empty");
        }
        System.out.println("======================================");
    }

    /**
     * Update a user
     * @param user User to update
     * @return true if successful, false otherwise
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET fullName = ?, email = ?, role = ?, gender = ?, "
                   + "phoneNumber = ?, hotelID = ?, isGroup = ?, isActive = ?, profileImage = ? "
                   + "WHERE userID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getGender());
            stmt.setString(5, user.getPhoneNumber());
            stmt.setInt(6, user.getHotelID());
            stmt.setBoolean(7, user.getIsGroup());
            stmt.setBoolean(8, user.getIsActive());
            stmt.setString(9, user.getProfileImage());
            stmt.setInt(10, user.getUserID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update user's last login time
     * @param userID User ID
     * @return true if successful, false otherwise
     */
    public boolean updateLastLogin(int userID) {
        String sql = "UPDATE Users SET lastLogin = ? WHERE userID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, userID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating last login: " + e.getMessage());
            return false;
        }
    }

    /**
     * Change user's password
     * @param userID User ID
     * @param newPassword New password
     * @return true if successful, false otherwise
     */
    public boolean changePassword(int userID, String newPassword) {
        String sql = "UPDATE Users SET passwordHash = ?, salt = ? WHERE userID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Generate new salt and hash password
            byte[] salt = PasswordUtil.generateSalt();
            byte[] passwordHash = PasswordUtil.hashPassword(newPassword, salt);

            stmt.setBytes(1, passwordHash);
            stmt.setBytes(2, salt);
            stmt.setInt(3, userID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error changing password: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get all users
     * @return List of all users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY userID";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = mapUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Get users with pagination
     * @param pageNumber Page number (1-based)
     * @param pageSize Number of items per page
     * @return List of users for the specified page
     */
    public List<User> getUsers(int pageNumber, int pageSize) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY userID " +
                     "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, (pageNumber - 1) * pageSize);
            stmt.setInt(2, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = mapUser(rs);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting users with pagination: " + e.getMessage());
        }
        return users;
    }

    /**
     * Get total number of users
     * @return Total number of users
     */
    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting total users: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Delete a user (soft delete by setting isActive to 0)
     * @param userID User ID
     * @return true if successful, false otherwise
     */
    public boolean deleteUser(int userID) {
        String sql = "UPDATE Users SET isActive = 0 WHERE userID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Search users by name, email, or username
     * @param searchTerm Search term
     * @return List of matching users
     */
    public List<User> searchUsers(String searchTerm) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE " +
                     "fullName LIKE ? OR " +
                     "email LIKE ? OR " +
                     "username LIKE ? " +
                     "ORDER BY userID";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = mapUser(rs);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Filter users by role and/or active status
     * @param role Role to filter by (can be null for all roles)
     * @param isActive Active status to filter by (can be null for all statuses)
     * @return List of matching users
     */
    public List<User> filterUsers(String role, Boolean isActive) {
        List<User> users = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM Users WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (role != null && !role.isEmpty()) {
            sqlBuilder.append(" AND role = ?");
            params.add(role);
        }

        if (isActive != null) {
            sqlBuilder.append(" AND isActive = ?");
            params.add(isActive);
        }

        sqlBuilder.append(" ORDER BY userID");

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stmt.setString(i + 1, (String) param);
                } else if (param instanceof Boolean) {
                    stmt.setBoolean(i + 1, (Boolean) param);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = mapUser(rs);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error filtering users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Verify user's password
     * @param userID User ID
     * @param password Password to verify
     * @return true if password is correct, false otherwise
     */
    public boolean verifyPassword(int userID, String password) {
        String sql = "SELECT passwordHash, salt FROM Users WHERE userID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    byte[] storedHash = rs.getBytes("passwordHash");
                    byte[] salt = rs.getBytes("salt");

                    return PasswordUtil.verifyPassword(password, storedHash, salt);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error verifying password: " + e.getMessage());
        }
        return false;
    }

    /**
     * Map a ResultSet row to a User object
     * @param rs ResultSet to map
     * @return Mapped User object
     * @throws SQLException if a database access error occurs
     */
    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("userID"));
        user.setFullName(rs.getString("fullName"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getBytes("passwordHash"));
        user.setSalt(rs.getBytes("salt"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setGender(rs.getString("gender"));
        user.setPhoneNumber(rs.getString("phoneNumber"));
        user.setHotelID(rs.getInt("hotelID"));
        user.setIsGroup(rs.getBoolean("isGroup"));
        user.setIsActive(rs.getBoolean("isActive"));
        user.setCreatedDate(rs.getTimestamp("createdDate"));
        user.setLastLogin(rs.getTimestamp("lastLogin"));

        // Handle profileImage column which might not exist in older database versions
        try {
            user.setProfileImage(rs.getString("profileImage"));
        } catch (SQLException e) {
            // If the column doesn't exist, just set it to null
            user.setProfileImage(null);
        }

        return user;
    }
}
