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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.PasswordUtil;

/**
 * Servlet to test direct user insertion
 */
@WebServlet(name = "TestInsertServlet", urlPatterns = {"/test-insert"})
public class TestInsertServlet extends HttpServlet {

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
            out.println("<title>Test User Insertion</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("h1 { color: #1a73e8; }");
            out.println("pre { background-color: #f5f5f5; padding: 10px; border-radius: 5px; }");
            out.println(".success { color: green; }");
            out.println(".error { color: red; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Test User Insertion</h1>");
            
            DBContext dbContext = null;
            Connection conn = null;
            
            try {
                // Get connection
                dbContext = new DBContext();
                conn = dbContext.getConnection();
                
                if (conn != null) {
                    out.println("<p class='success'>✅ Database connection successful!</p>");
                    
                    // Generate test user data
                    String fullName = "Test User";
                    String username = "testuser" + System.currentTimeMillis();
                    String password = "password123";
                    String email = username + "@example.com";
                    String role = "user";
                    
                    // Generate salt and hash the password
                    byte[] salt = PasswordUtil.generateSalt();
                    byte[] passwordHash = PasswordUtil.hashPassword(password, salt);
                    
                    out.println("<p>Attempting to insert test user:</p>");
                    out.println("<pre>");
                    out.println("Full Name: " + fullName);
                    out.println("Username: " + username);
                    out.println("Email: " + email);
                    out.println("Role: " + role);
                    out.println("</pre>");
                    
                    // Try different SQL statements
                    String[] sqlStatements = {
                        "INSERT INTO [User] (FullName, Username, PasswordHash, Salt, Email, Role, IsGroup, IsActive, CreatedDate) VALUES (?, ?, ?, ?, ?, ?, 0, 1, GETDATE())",
                        "INSERT INTO [User] (FullName, Username, PasswordHash, Salt, Email, Role) VALUES (?, ?, ?, ?, ?, ?)",
                        "INSERT INTO [User] (UserName, PasswordHash, Salt, Email, FullName, Role) VALUES (?, ?, ?, ?, ?, ?)"
                    };
                    
                    boolean success = false;
                    String successfulSql = "";
                    
                    for (String sql : sqlStatements) {
                        try {
                            out.println("<p>Trying SQL: <code>" + sql + "</code></p>");
                            
                            PreparedStatement stmt = conn.prepareStatement(sql);
                            
                            // Set parameters based on the SQL statement
                            if (sql.contains("UserName")) {
                                // Third SQL statement
                                stmt.setString(1, username);
                                stmt.setBytes(2, passwordHash);
                                stmt.setBytes(3, salt);
                                stmt.setString(4, email);
                                stmt.setString(5, fullName);
                                stmt.setString(6, role);
                            } else {
                                // First and second SQL statements
                                stmt.setString(1, fullName);
                                stmt.setString(2, username);
                                stmt.setBytes(3, passwordHash);
                                stmt.setBytes(4, salt);
                                stmt.setString(5, email);
                                stmt.setString(6, role);
                            }
                            
                            int rowsAffected = stmt.executeUpdate();
                            
                            if (rowsAffected > 0) {
                                success = true;
                                successfulSql = sql;
                                out.println("<p class='success'>✅ User inserted successfully with SQL: <code>" + sql + "</code></p>");
                                out.println("<p>Rows affected: " + rowsAffected + "</p>");
                                break;
                            } else {
                                out.println("<p class='error'>❌ No rows affected with SQL: <code>" + sql + "</code></p>");
                            }
                            
                            stmt.close();
                        } catch (SQLException e) {
                            out.println("<p class='error'>❌ Error with SQL: <code>" + sql + "</code></p>");
                            out.println("<p>Error message: " + e.getMessage() + "</p>");
                        }
                    }
                    
                    if (success) {
                        out.println("<h2>Success!</h2>");
                        out.println("<p>User was successfully inserted with SQL: <code>" + successfulSql + "</code></p>");
                        out.println("<p>You should update your UserDAO to use this SQL statement.</p>");
                    } else {
                        out.println("<h2>All insertion attempts failed</h2>");
                        out.println("<p>Please check the database schema and error messages above.</p>");
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
            
            out.println("</body>");
            out.println("</html>");
        }
    }
}
