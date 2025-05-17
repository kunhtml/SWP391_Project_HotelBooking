package controller;

import database.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet to create the User table
 */
@WebServlet(name = "CreateTableServlet", urlPatterns = {"/create-table"})
public class CreateTableServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Create User Table</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("h1 { color: #1a73e8; }");
            out.println("pre { background-color: #f5f5f5; padding: 10px; border-radius: 5px; }");
            out.println(".success { color: green; }");
            out.println(".error { color: red; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Create User Table</h1>");
            
            DBContext dbContext = null;
            Connection conn = null;
            
            try {
                // Get connection
                dbContext = new DBContext();
                conn = dbContext.getConnection();
                
                if (conn != null) {
                    out.println("<p class='success'>✅ Database connection successful!</p>");
                    
                    // Check if User table exists
                    DatabaseMetaData metaData = conn.getMetaData();
                    ResultSet tables = metaData.getTables(null, null, "User", null);
                    
                    if (tables.next()) {
                        out.println("<p>User table already exists. Do you want to drop it and recreate?</p>");
                        out.println("<a href='create-table?action=drop' style='color: red; text-decoration: none; padding: 5px 10px; border: 1px solid red; border-radius: 3px;'>Drop and Recreate</a>");
                    } else {
                        createUserTable(conn, out);
                    }
                    
                    // Check if action is to drop and recreate
                    String action = request.getParameter("action");
                    if ("drop".equals(action)) {
                        try {
                            Statement stmt = conn.createStatement();
                            stmt.executeUpdate("DROP TABLE [User]");
                            out.println("<p class='success'>✅ User table dropped successfully!</p>");
                            createUserTable(conn, out);
                        } catch (SQLException e) {
                            out.println("<p class='error'>❌ Error dropping User table: " + e.getMessage() + "</p>");
                        }
                    }
                    
                } else {
                    out.println("<p class='error'>❌ Failed to establish database connection!</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>❌ Error: " + e.getMessage() + "</p>");
                out.println("<pre>");
                e.printStackTrace(out);
                out.println("</pre>");
            } finally {
                if (dbContext != null) {
                    dbContext.closeConnection();
                }
            }
            
            out.println("<p><a href='schema'>View Database Schema</a></p>");
            out.println("<p><a href='test-insert'>Test User Insertion</a></p>");
            
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    /**
     * Create the User table
     * @param conn Database connection
     * @param out PrintWriter for output
     */
    private void createUserTable(Connection conn, PrintWriter out) {
        try {
            Statement stmt = conn.createStatement();
            
            String createTableSQL = "CREATE TABLE [User] (\n" +
                "    UserID INT IDENTITY(1,1) PRIMARY KEY,\n" +
                "    FullName NVARCHAR(100) NOT NULL,\n" +
                "    Username NVARCHAR(50) NOT NULL UNIQUE,\n" +
                "    PasswordHash VARBINARY(MAX) NOT NULL,\n" +
                "    Salt VARBINARY(MAX) NOT NULL,\n" +
                "    Email NVARCHAR(100) NOT NULL UNIQUE,\n" +
                "    Role NVARCHAR(20) NOT NULL,\n" +
                "    Gender NVARCHAR(10),\n" +
                "    PhoneNumber NVARCHAR(20),\n" +
                "    HotelID INT,\n" +
                "    IsGroup BIT DEFAULT 0,\n" +
                "    IsActive BIT DEFAULT 1,\n" +
                "    CreatedDate DATETIME DEFAULT GETDATE(),\n" +
                "    LastLogin DATETIME\n" +
                ")";
            
            out.println("<p>Executing SQL:</p>");
            out.println("<pre>" + createTableSQL + "</pre>");
            
            stmt.executeUpdate(createTableSQL);
            out.println("<p class='success'>✅ User table created successfully!</p>");
            
        } catch (SQLException e) {
            out.println("<p class='error'>❌ Error creating User table: " + e.getMessage() + "</p>");
        }
    }
}
