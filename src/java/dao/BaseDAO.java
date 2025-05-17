package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Base Data Access Object class that extends DBContext
 * All DAO classes should extend this class
 */
public abstract class BaseDAO extends DBContext {

    /**
     * Get a prepared statement with the given SQL
     * @param sql SQL query
     * @return PreparedStatement object
     * @throws SQLException if a database access error occurs
     */
    protected PreparedStatement getPreparedStatement(String sql) throws SQLException {
        try {
            // Ensure connection is valid
            if (connection == null || connection.isClosed()) {
                connection = getConnection();
            }
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.err.println("Error preparing statement: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Close database resources
     * @param rs ResultSet to close
     * @param ps PreparedStatement to close
     */
    protected void closeResources(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    /**
     * Close database resources including connection
     * @param rs ResultSet to close
     * @param ps PreparedStatement to close
     * @param conn Connection to close
     */
    protected void closeResources(ResultSet rs, PreparedStatement ps, Connection conn) {
        closeResources(rs, ps);
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
