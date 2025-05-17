//package utils;
//
//import com.google.gson.Gson;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Utility class for handling HTTP responses
// */
//public class ResponseUtil {
//    
//    private static final Gson gson = new Gson();
//    
//    /**
//     * Send a JSON response
//     * @param response HTTP response
//     * @param object Object to convert to JSON
//     * @throws IOException if an I/O error occurs
//     */
//    public static void sendJsonResponse(HttpServletResponse response, Object object) throws IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        
//        String json = gson.toJson(object);
//        
//        try (PrintWriter out = response.getWriter()) {
//            out.print(json);
//            out.flush();
//        }
//    }
//    
//    /**
//     * Send a success JSON response
//     * @param response HTTP response
//     * @param data Data to include in the response
//     * @throws IOException if an I/O error occurs
//     */
//    public static void sendSuccessResponse(HttpServletResponse response, Object data) throws IOException {
//        Map<String, Object> result = new HashMap<>();
//        result.put("status", "success");
//        result.put("data", data);
//        
//        sendJsonResponse(response, result);
//    }
//    
//    /**
//     * Send an error JSON response
//     * @param response HTTP response
//     * @param message Error message
//     * @param statusCode HTTP status code
//     * @throws IOException if an I/O error occurs
//     */
//    public static void sendErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
//        response.setStatus(statusCode);
//        
//        Map<String, Object> result = new HashMap<>();
//        result.put("status", "error");
//        result.put("message", message);
//        
//        sendJsonResponse(response, result);
//    }
//    
//    /**
//     * Send a bad request error response (400)
//     * @param response HTTP response
//     * @param message Error message
//     * @throws IOException if an I/O error occurs
//     */
//    public static void sendBadRequestError(HttpServletResponse response, String message) throws IOException {
//        sendErrorResponse(response, message, HttpServletResponse.SC_BAD_REQUEST);
//    }
//    
//    /**
//     * Send an unauthorized error response (401)
//     * @param response HTTP response
//     * @param message Error message
//     * @throws IOException if an I/O error occurs
//     */
//    public static void sendUnauthorizedError(HttpServletResponse response, String message) throws IOException {
//        sendErrorResponse(response, message, HttpServletResponse.SC_UNAUTHORIZED);
//    }
//    
//    /**
//     * Send a forbidden error response (403)
//     * @param response HTTP response
//     * @param message Error message
//     * @throws IOException if an I/O error occurs
//     */
//    public static void sendForbiddenError(HttpServletResponse response, String message) throws IOException {
//        sendErrorResponse(response, message, HttpServletResponse.SC_FORBIDDEN);
//    }
//    
//    /**
//     * Send a not found error response (404)
//     * @param response HTTP response
//     * @param message Error message
//     * @throws IOException if an I/O error occurs
//     */
//    public static void sendNotFoundError(HttpServletResponse response, String message) throws IOException {
//        sendErrorResponse(response, message, HttpServletResponse.SC_NOT_FOUND);
//    }
//    
//    /**
//     * Send an internal server error response (500)
//     * @param response HTTP response
//     * @param message Error message
//     * @throws IOException if an I/O error occurs
//     */
//    public static void sendInternalServerError(HttpServletResponse response, String message) throws IOException {
//        sendErrorResponse(response, message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//    }
//}
