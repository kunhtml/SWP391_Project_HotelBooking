package controller;

import dal.DBContext;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for testing database connection
 */
@WebServlet(name = "DatabaseTestController", urlPatterns = {"/test-database"})
public class DatabaseTestController extends HttpServlet {

    /**
     * Handles the HTTP GET request for testing database connection
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get connection from DBContext
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            if (conn != null && !conn.isClosed()) {
                // Connection successful
                request.setAttribute("connectionSuccess", true);

                // Get database metadata
                DatabaseMetaData metaData = conn.getMetaData();
                request.setAttribute("dbProduct", metaData.getDatabaseProductName());
                request.setAttribute("dbVersion", metaData.getDatabaseProductVersion());
                request.setAttribute("driverName", metaData.getDriverName());
                request.setAttribute("driverVersion", metaData.getDriverVersion());
                request.setAttribute("dbUrl", metaData.getURL());
                request.setAttribute("dbUser", metaData.getUserName());

                // Get tables
                List<TableInfo> tables = new ArrayList<>();
                ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    String tableType = rs.getString("TABLE_TYPE");
                    tables.add(new TableInfo(tableName, tableType));
                }
                request.setAttribute("tables", tables);

                // Check if Users table exists and get its columns
                if (tables.stream().anyMatch(t -> t.getName().equalsIgnoreCase("Users"))) {
                    List<ColumnInfo> columns = new ArrayList<>();
                    ResultSet columnsRs = metaData.getColumns(null, null, "Users", "%");

                    while (columnsRs.next()) {
                        String columnName = columnsRs.getString("COLUMN_NAME");
                        String columnType = columnsRs.getString("TYPE_NAME");
                        int columnSize = columnsRs.getInt("COLUMN_SIZE");
                        boolean nullable = columnsRs.getInt("NULLABLE") == 1;

                        columns.add(new ColumnInfo(columnName, columnType, columnSize, nullable));
                    }

                    request.setAttribute("columns", columns);
                }

                // Close the connection
                conn.close();

            } else {
                // Connection failed
                request.setAttribute("connectionSuccess", false);
                request.setAttribute("errorMessage", "Could not establish a connection to the database.");
            }

        } catch (SQLException e) {
            // Exception occurred
            request.setAttribute("connectionSuccess", false);
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("sqlState", e.getSQLState());
            request.setAttribute("errorCode", e.getErrorCode());

            // Log the stack trace
            e.printStackTrace();
        }

        // Forward to the JSP page
        request.getRequestDispatcher("/WEB-INF/views/database-test.jsp").forward(request, response);
    }

    /**
     * Inner class to hold table information
     */
    public static class TableInfo {
        private final String name;
        private final String type;

        public TableInfo(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * Inner class to hold column information
     */
    public static class ColumnInfo {
        private final String name;
        private final String type;
        private final int size;
        private final boolean nullable;

        public ColumnInfo(String name, String type, int size, boolean nullable) {
            this.name = name;
            this.type = type;
            this.size = size;
            this.nullable = nullable;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getSize() {
            return size;
        }

        public boolean isNullable() {
            return nullable;
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Database Connection Test Controller";
    }
}
