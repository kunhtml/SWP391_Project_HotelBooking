package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import util.PasswordUtil;

/**
 * Data Access Object for User-related database operations
 */
public class UserDAO extends BaseDAO {

    /**
     * Authenticate a user by username and password
     * @param username The username
     * @param password The password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticate(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            String sql = "SELECT * FROM [User] WHERE Username = ?";
            stmt = getPreparedStatement(sql);
            stmt.setString(1, username);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Get the stored password hash and salt
                byte[] storedHash = rs.getBytes("PasswordHash");
                byte[] salt = rs.getBytes("Salt");

                // Verify the password
                if (PasswordUtil.verifyPassword(password, storedHash, salt)) {
                    user = new User();
                    user.setUserID(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setPasswordHash(storedHash);
                    user.setSalt(salt);
                    user.setEmail(rs.getString("Email"));
                    user.setFullName(rs.getString("FullName"));
                    user.setRole(rs.getString("Role"));
                    user.setHotelID(rs.getInt("HotelID"));
                    user.setIsGroup(rs.getBoolean("IsGroup"));
                    user.setPhoneNumber(rs.getString("PhoneNumber"));
                    user.setIsActive(rs.getBoolean("IsActive"));
                    user.setCreatedDate(rs.getTimestamp("CreatedDate"));
                    user.setLastLogin(rs.getTimestamp("LastLogin"));

                    // Update last login time
                    updateLastLogin(user.getUserID());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        } finally {
            closeResources(rs, stmt);
        }

        return user;
    }

    /**
     * Update the last login time for a user
     * @param userID The user ID
     */
    public void updateLastLogin(int userID) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE [User] SET LastLogin = GETDATE() WHERE UserID = ?";
            stmt = getPreparedStatement(sql);
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating last login: " + e.getMessage());
        } finally {
            closeResources(null, stmt);
        }
    }

    /**
     * Get a user by username
     * @param username The username to search for
     * @return User object if found, null otherwise
     */
    public User getUserByUsername(String username) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            String sql = "SELECT * FROM [User] WHERE Username = ?";
            stmt = getPreparedStatement(sql);
            stmt.setString(1, username);

            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPasswordHash(rs.getBytes("PasswordHash"));
                user.setSalt(rs.getBytes("Salt"));
                user.setEmail(rs.getString("Email"));
                user.setFullName(rs.getString("FullName"));
                user.setRole(rs.getString("Role"));
                user.setHotelID(rs.getInt("HotelID"));
                user.setIsGroup(rs.getBoolean("IsGroup"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setIsActive(rs.getBoolean("IsActive"));
                user.setCreatedDate(rs.getTimestamp("CreatedDate"));
                user.setLastLogin(rs.getTimestamp("LastLogin"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by username: " + e.getMessage());
        } finally {
            closeResources(rs, stmt);
        }

        return user;
    }

    /**
     * Create a new user in the database
     * @param fullName User's full name
     * @param username Username
     * @param password Plain text password
     * @param email Email address
     * @param gender Gender
     * @param role User role
     * @return true if successful, false otherwise
     */
    public boolean createUser(String fullName, String username, String password, String email, String gender, String role) {
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            System.out.println("Creating user: " + fullName + ", " + username + ", " + email + ", " + role);

            // Generate salt and hash the password
            byte[] salt = PasswordUtil.generateSalt();
            byte[] passwordHash = PasswordUtil.hashPassword(password, salt);

            System.out.println("Generated salt and password hash");

            // Let's check the actual database table structure
            try {
                PreparedStatement checkStmt = getPreparedStatement("SELECT TOP 1 * FROM [User]");
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    System.out.println("User table exists with columns:");
                    java.sql.ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.println(metaData.getColumnName(i) + " (" + metaData.getColumnTypeName(i) + ")");
                    }
                } else {
                    System.out.println("User table exists but is empty");
                }
                closeResources(rs, checkStmt);
            } catch (SQLException e) {
                System.err.println("Error checking User table: " + e.getMessage());
            }

            String sql = "INSERT INTO [User] (FullName, Username, PasswordHash, Salt, Email, Role, IsGroup, IsActive, CreatedDate) " +
                         "VALUES (?, ?, ?, ?, ?, ?, 0, 1, GETDATE())";
            System.out.println("SQL: " + sql);

            stmt = getPreparedStatement(sql);
            stmt.setString(1, fullName);
            stmt.setString(2, username);
            stmt.setBytes(3, passwordHash);
            stmt.setBytes(4, salt);
            stmt.setString(5, email);
            stmt.setString(6, role);

            System.out.println("Executing SQL statement...");
            int rowsAffected = stmt.executeUpdate();
            success = (rowsAffected > 0);
            System.out.println("User creation " + (success ? "successful" : "failed") + ". Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(null, stmt);
        }

        return success;
    }

    /**
     * Create a new user in the database
     * @param user The user to create
     * @param plainPassword The plain text password
     * @return true if successful, false otherwise
     */
    public boolean createUser(User user, String plainPassword) {
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            // Generate salt and hash the password
            byte[] salt = PasswordUtil.generateSalt();
            byte[] passwordHash = PasswordUtil.hashPassword(plainPassword, salt);

            String sql = "INSERT INTO [User] (FullName, Username, PasswordHash, Salt, Email, Role, IsGroup, IsActive, CreatedDate) " +
                         "VALUES (?, ?, ?, ?, ?, ?, 0, 1, GETDATE())";
            stmt = getPreparedStatement(sql);
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUsername());
            stmt.setBytes(3, passwordHash);
            stmt.setBytes(4, salt);
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getRole());

            int rowsAffected = stmt.executeUpdate();
            success = (rowsAffected > 0);
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        } finally {
            closeResources(null, stmt);
        }

        return success;
    }

    /**
     * Check if a username already exists
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    public boolean isUsernameExists(String username) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            String sql = "SELECT COUNT(*) FROM [User] WHERE Username = ?";
            stmt = getPreparedStatement(sql);
            stmt.setString(1, username);

            rs = stmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
        } finally {
            closeResources(rs, stmt);
        }

        return exists;
    }

    /**
     * Check if an email already exists
     * @param email The email to check
     * @return true if email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            String sql = "SELECT COUNT(*) FROM [User] WHERE Email = ?";
            stmt = getPreparedStatement(sql);
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
        } finally {
            closeResources(rs, stmt);
        }

        return exists;
    }

    /**
     * For backward compatibility with existing code
     * @param user The user to create
     * @return true if successful, false otherwise
     */
    public boolean createUser(User user) {
        return createUser(user.getFullName(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.getGender(), user.getRole());
    }
}
