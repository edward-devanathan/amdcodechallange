package org.amd.rsa;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hasher {

    public static void main(String[] args) {
        String filePath = "C:\\amdcodechallenge\\AMDimagefile.JPG";

        try {
            byte[] fileBytes = readFileBytes(filePath);
            String sha256Hash = computeSHA256(fileBytes);
            System.out.println("SHA-256 hash (hex): " + sha256Hash);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readFileBytes(String filePath) throws IOException, NoSuchAlgorithmException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            return digest.digest();
        }
    }

    private static String computeSHA256(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input);
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
