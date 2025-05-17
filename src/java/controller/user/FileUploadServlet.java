package controller.user;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import model.User;
import utils.AuthUtil;

/**
 * Servlet for handling file uploads
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class FileUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "uploads";

    /**
     * Handles the HTTP POST request for file uploads
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in
        User user = AuthUtil.getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Get the upload type
        String uploadType = request.getParameter("uploadType");
        if (uploadType == null || uploadType.isEmpty()) {
            uploadType = "profile"; // Default to profile image upload
        }
        
        try {
            // Get the file part from the request
            Part filePart = request.getPart("file");
            if (filePart == null) {
                request.getSession().setAttribute("errorMessage", "No file selected");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }
            
            // Get the file name
            String fileName = getSubmittedFileName(filePart);
            if (fileName == null || fileName.isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Invalid file name");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }
            
            // Validate file type
            if (!isValidImageFile(fileName)) {
                request.getSession().setAttribute("errorMessage", "Invalid file type. Only JPG, JPEG, PNG, and GIF files are allowed.");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }
            
            // Create a unique file name to prevent overwriting
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            
            // Create the upload directory if it doesn't exist
            String applicationPath = request.getServletContext().getRealPath("");
            String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // Create a subdirectory for the upload type
            String typePath = uploadPath + File.separator + uploadType;
            File typeDir = new File(typePath);
            if (!typeDir.exists()) {
                typeDir.mkdirs();
            }
            
            // Save the file
            Path filePath = Paths.get(typePath + File.separator + uniqueFileName);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
            
            // Update the user's profile image path in the database
            if ("profile".equals(uploadType)) {
                String relativePath = UPLOAD_DIRECTORY + "/" + uploadType + "/" + uniqueFileName;
                user.setProfileImage(relativePath);
                
                UserDAO userDAO = new UserDAO();
                boolean success = userDAO.updateUser(user);
                
                if (success) {
                    // Update the user in the session
                    request.getSession().setAttribute("user", user);
                    request.getSession().setAttribute("successMessage", "Profile image updated successfully");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to update profile image");
                }
            }
            
            // Redirect back to the profile page
            response.sendRedirect(request.getContextPath() + "/profile");
            
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Error uploading file: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }
    
    /**
     * Extracts file name from the Content-Disposition header of the part
     * @param part The part containing the file
     * @return The file name
     */
    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return null;
    }
    
    /**
     * Validates if the file is an image file
     * @param fileName The file name to validate
     * @return true if the file is a valid image file, false otherwise
     */
    private boolean isValidImageFile(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        return lowerCaseFileName.endsWith(".jpg") || 
               lowerCaseFileName.endsWith(".jpeg") || 
               lowerCaseFileName.endsWith(".png") || 
               lowerCaseFileName.endsWith(".gif");
    }
}
