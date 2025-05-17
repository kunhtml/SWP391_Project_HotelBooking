package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Utility class for password hashing and verification
 */
public class PasswordUtil {
    private static final int SALT_LENGTH = 16;
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Generate a random salt for password hashing
     * @return Salt as byte array
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Hash a password with a given salt
     * @param password Plain text password
     * @param salt Salt for hashing
     * @return Hashed password as byte array
     */
    public static byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            return md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error hashing password: " + e.getMessage());
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verify a password against a stored hash and salt
     * @param password Plain text password to verify
     * @param storedHash Stored hash to compare against
     * @param salt Salt used for hashing
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String password, byte[] storedHash, byte[] salt) {
        byte[] computedHash = hashPassword(password, salt);
        return Arrays.equals(storedHash, computedHash);
    }
}
