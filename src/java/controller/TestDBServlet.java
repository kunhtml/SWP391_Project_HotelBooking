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
 * Servlet to test database connection
 */
@WebServlet(name = "TestDBServlet", urlPatterns = {"/test-db"})
public class TestDBServlet extends HttpServlet {

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
            out.println("<title>Database Connection Test</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("h1 { color: #1a73e8; }");
            out.println("h2 { color: #333; margin-top: 20px; }");
            out.println("p { margin: 10px 0; }");
            out.println("table { border-collapse: collapse; width: 100%; margin-top: 20px; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println(".success { color: green; }");
            out.println(".error { color: red; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Database Connection Test</h1>");
            
            DBContext dbContext = null;
            Connection conn = null;
            
            try {
                // Test connection
                out.println("<h2>Connection Test</h2>");
                dbContext = new DBContext();
                conn = dbContext.getConnection();
                
                if (conn != null && !conn.isClosed()) {
                    out.println("<p class='success'>✅ Database connection successful!</p>");
                    
                    // Get database metadata
                    DatabaseMetaData metaData = conn.getMetaData();
                    out.println("<p><strong>Database Product Name:</strong> " + metaData.getDatabaseProductName() + "</p>");
                    out.println("<p><strong>Database Product Version:</strong> " + metaData.getDatabaseProductVersion() + "</p>");
                    out.println("<p><strong>Driver Name:</strong> " + metaData.getDriverName() + "</p>");
                    out.println("<p><strong>Driver Version:</strong> " + metaData.getDriverVersion() + "</p>");
                    out.println("<p><strong>URL:</strong> " + metaData.getURL() + "</p>");
                    out.println("<p><strong>Username:</strong> " + metaData.getUserName() + "</p>");
                    
                    // List tables
                    out.println("<h2>Database Tables</h2>");
                    out.println("<table>");
                    out.println("<tr><th>Table Name</th><th>Table Type</th></tr>");
                    
                    ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
                    boolean hasTables = false;
                    
                    while (tables.next()) {
                        hasTables = true;
                        String tableName = tables.getString("TABLE_NAME");
                        String tableType = tables.getString("TABLE_TYPE");
                        out.println("<tr><td>" + tableName + "</td><td>" + tableType + "</td></tr>");
                    }
                    
                    if (!hasTables) {
                        out.println("<tr><td colspan='2'>No tables found</td></tr>");
                    }
                    
                    out.println("</table>");
                    
                    // Test a simple query
                    out.println("<h2>Test Query</h2>");
                    try {
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT 'Connection is working!' AS message");
                        
                        if (rs.next()) {
                            out.println("<p class='success'>✅ Query executed successfully: " + rs.getString("message") + "</p>");
                        }
                    } catch (SQLException e) {
                        out.println("<p class='error'>❌ Error executing test query: " + e.getMessage() + "</p>");
                    }
                    
                } else {
                    out.println("<p class='error'>❌ Failed to establish database connection!</p>");
                }
            } catch (Exception e) {
                out.println("<p class='error'>❌ Error: " + e.getMessage() + "</p>");
                out.println("<p>Stack trace:</p>");
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
