package lv.wings.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HashUtils {

    public static String createRandomToken() {
        final int bytesLength = 32;
        byte[] randomBytes = new byte[bytesLength];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return bytesToHex(randomBytes);
    }

    public static String createTokenHash(String token) {
        try {
            return hashToken(token);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create a token hash in HashUtils.createTokenHash");
        }
    }

    private static String bytesToHex(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static String hashToken(String token) throws NoSuchAlgorithmException {
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = hasher.digest(token.getBytes());
        return bytesToHex(hashedBytes);
    }


}
