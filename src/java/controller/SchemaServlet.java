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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet to display database schema
 */
@WebServlet(name = "SchemaServlet", urlPatterns = {"/schema"})
public class SchemaServlet extends HttpServlet {

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
            out.println("<title>Database Schema</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("h1, h2 { color: #1a73e8; }");
            out.println("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println(".success { color: green; }");
            out.println(".error { color: red; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Database Schema</h1>");
            
            DBContext dbContext = null;
            Connection conn = null;
            
            try {
                // Get connection
                dbContext = new DBContext();
                conn = dbContext.getConnection();
                
                if (conn != null) {
                    out.println("<p class='success'>✅ Database connection successful!</p>");
                    
                    // Get database metadata
                    DatabaseMetaData metaData = conn.getMetaData();
                    out.println("<p><strong>Database Product Name:</strong> " + metaData.getDatabaseProductName() + "</p>");
                    out.println("<p><strong>Database Product Version:</strong> " + metaData.getDatabaseProductVersion() + "</p>");
                    
                    // List all tables
                    out.println("<h2>Tables</h2>");
                    ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
                    
                    while (tables.next()) {
                        String tableName = tables.getString("TABLE_NAME");
                        out.println("<h3>Table: " + tableName + "</h3>");
                        
                        // Get columns for this table
                        ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                        out.println("<table>");
                        out.println("<tr><th>Column Name</th><th>Type</th><th>Size</th><th>Nullable</th><th>Default</th></tr>");
                        
                        while (columns.next()) {
                            String columnName = columns.getString("COLUMN_NAME");
                            String columnType = columns.getString("TYPE_NAME");
                            int columnSize = columns.getInt("COLUMN_SIZE");
                            String nullable = columns.getInt("NULLABLE") == 1 ? "Yes" : "No";
                            String defaultValue = columns.getString("COLUMN_DEF");
                            
                            out.println("<tr>");
                            out.println("<td>" + columnName + "</td>");
                            out.println("<td>" + columnType + "</td>");
                            out.println("<td>" + columnSize + "</td>");
                            out.println("<td>" + nullable + "</td>");
                            out.println("<td>" + (defaultValue != null ? defaultValue : "") + "</td>");
                            out.println("</tr>");
                        }
                        
                        out.println("</table>");
                        
                        // Get primary keys
                        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
                        out.println("<h4>Primary Keys</h4>");
                        out.println("<ul>");
                        boolean hasPrimaryKeys = false;
                        
                        while (primaryKeys.next()) {
                            hasPrimaryKeys = true;
                            String columnName = primaryKeys.getString("COLUMN_NAME");
                            String pkName = primaryKeys.getString("PK_NAME");
                            out.println("<li>" + columnName + " (PK Name: " + pkName + ")</li>");
                        }
                        
                        if (!hasPrimaryKeys) {
                            out.println("<li>No primary keys defined</li>");
                        }
                        
                        out.println("</ul>");
                        
                        // Get foreign keys
                        ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
                        out.println("<h4>Foreign Keys</h4>");
                        out.println("<ul>");
                        boolean hasForeignKeys = false;
                        
                        while (foreignKeys.next()) {
                            hasForeignKeys = true;
                            String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                            String pkTableName = foreignKeys.getString("PKTABLE_NAME");
                            String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                            out.println("<li>" + fkColumnName + " references " + pkTableName + "(" + pkColumnName + ")</li>");
                        }
                        
                        if (!hasForeignKeys) {
                            out.println("<li>No foreign keys defined</li>");
                        }
                        
                        out.println("</ul>");
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
