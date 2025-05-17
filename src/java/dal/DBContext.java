package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection context
 * This class provides methods to connect to the database
 */
public class DBContext {
    protected Connection connection;
    private static final String SERVER_NAME = "localhost";
    private static final String DB_NAME = "Hotel_Booking";
    private static final String PORT_NUMBER = "1433";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "123";

    /**
     * Get a connection to the database
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        try {
            // Load the SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Create the connection URL
            String url = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT_NUMBER +
                         ";databaseName=" + DB_NAME +
                         ";encrypt=true;trustServerCertificate=true";

            // Create and return the connection
            connection = DriverManager.getConnection(url, USER_NAME, PASSWORD);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            throw new SQLException("Error connecting to database", e);
        }
    }

    /**
     * Close the database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
