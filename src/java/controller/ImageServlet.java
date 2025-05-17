package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet to serve images from the storage directory
 */
@WebServlet(name = "ImageServlet", urlPatterns = {"/storage/*"})
public class ImageServlet extends HttpServlet {

    /**
     * Handles the HTTP GET request to serve images
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the requested file path
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(404); // Not Found
            return;
        }

        // Remove the leading slash
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }

        // Get the application's real path
        String webPath = request.getServletContext().getRealPath("");

        // Go up one level from the build directory to get the project root
        File buildDir = new File(webPath);
        File projectDir = buildDir.getParentFile().getParentFile();
        String projectRoot = projectDir.getAbsolutePath();

        // Construct the full path to the file in the storage directory
        Path filePath = Paths.get(projectRoot, "storage", pathInfo);
        File file = filePath.toFile();

        // Check if the file exists
        if (!file.exists() || file.isDirectory()) {
            // Try to find the file in the web directory as a fallback
            Path webFilePath = Paths.get(webPath, pathInfo);
            File webFile = webFilePath.toFile();

            if (!webFile.exists() || webFile.isDirectory()) {
                response.sendError(404); // Not Found
                return;
            }

            // Use the file from the web directory
            file = webFile;
            filePath = webFilePath;
        }

        // Set the content type based on the file extension
        String fileName = file.getName();
        String contentType = request.getServletContext().getMimeType(fileName);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);

        // Set content length
        response.setContentLength((int) file.length());

        // Copy the file to the response output stream
        Files.copy(filePath, response.getOutputStream());
    }
}
