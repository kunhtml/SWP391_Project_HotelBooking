package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for Settings entity
 */
public class SettingsDAO extends DBContext {

    /**
     * Get all settings as a map
     * @return Map of settings with key as setting name and value as setting value
     */
    public Map<String, String> getSettingsMap() {
        Map<String, String> settings = new HashMap<>();
        String sql = "SELECT setting_name, setting_value FROM Settings";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("setting_name");
                String value = rs.getString("setting_value");
                settings.put(name, value);
            }
        } catch (SQLException e) {
            System.err.println("Error getting settings: " + e.getMessage());
        }

        // Add default values if not found in database
        if (!settings.containsKey("hotel_name")) {
            settings.put("hotel_name", "Luxury Hotel");
        }
        if (!settings.containsKey("contact_address")) {
            settings.put("contact_address", "Khu Cong Nghe Cao Hoa Lac, km 29, Dai lo Thang Long, Ha Noi");
        }
        if (!settings.containsKey("contact_phone")) {
            settings.put("contact_phone", "+1 234 567 890");
        }
        if (!settings.containsKey("contact_email")) {
            settings.put("contact_email", "info@luxuryhotel.com");
        }
        if (!settings.containsKey("footer_copyright")) {
            settings.put("footer_copyright", "&copy; 2025 Luxury Hotel. All rights reserved.");
        }

        return settings;
    }

    /**
     * Get a setting by name
     * @param settingName Setting name
     * @return Setting value or null if not found
     */
    public String getSetting(String settingName) {
        String sql = "SELECT setting_value FROM Settings WHERE setting_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, settingName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("setting_value");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting setting: " + e.getMessage());
        }

        // Return default values if not found in database
        switch (settingName) {
            case "hotel_name":
                return "Luxury Hotel";
            case "contact_address":
                return "Khu Cong Nghe Cao Hoa Lac, km 29, Dai lo Thang Long, Ha Noi";
            case "contact_phone":
                return "+1 234 567 890";
            case "contact_email":
                return "info@luxuryhotel.com";
            case "footer_copyright":
                return "&copy; 2025 Luxury Hotel. All rights reserved.";
            default:
                return null;
        }
    }

    /**
     * Update a setting
     * @param settingName Setting name
     * @param settingValue Setting value
     * @return true if successful, false otherwise
     */
    public boolean updateSetting(String settingName, String settingValue) {
        String sql = "IF EXISTS (SELECT 1 FROM Settings WHERE setting_name = ?) " +
                     "UPDATE Settings SET setting_value = ? WHERE setting_name = ? " +
                     "ELSE " +
                     "INSERT INTO Settings (setting_name, setting_value) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, settingName);
            stmt.setString(2, settingValue);
            stmt.setString(3, settingName);
            stmt.setString(4, settingName);
            stmt.setString(5, settingValue);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating setting: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update multiple settings
     * @param settings Map of settings with key as setting name and value as setting value
     * @return true if all updates were successful, false otherwise
     */
    public boolean updateSettings(Map<String, String> settings) {
        boolean allSuccessful = true;

        for (Map.Entry<String, String> entry : settings.entrySet()) {
            boolean success = updateSetting(entry.getKey(), entry.getValue());
            if (!success) {
                allSuccessful = false;
            }
        }

        return allSuccessful;
    }

    /**
     * Convert Vietnamese characters to ASCII
     * @param str String to convert
     * @return Converted string
     */
    public static String convertVietnameseToASCII(String str) {
        if (str == null) {
            return null;
        }

        str = str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        str = str.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        str = str.replaceAll("[ìíịỉĩ]", "i");
        str = str.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        str = str.replaceAll("[ùúụủũưừứựửữ]", "u");
        str = str.replaceAll("[ỳýỵỷỹ]", "y");
        str = str.replaceAll("[đ]", "d");

        str = str.replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A");
        str = str.replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E");
        str = str.replaceAll("[ÌÍỊỈĨ]", "I");
        str = str.replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O");
        str = str.replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U");
        str = str.replaceAll("[ỲÝỴỶỸ]", "Y");
        str = str.replaceAll("[Đ]", "D");

        return str;
    }
}
