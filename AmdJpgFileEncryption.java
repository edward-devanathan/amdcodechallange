package org.amd.rsa;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class AmdJpgFileEncryption {
    public static void main(String[] args) throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048); // Set key size to 2048 bits
        KeyPair keyPair = generator.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        // Load the RSA public key
        /*File publicKeyFile = new File(AmdConstants.publicFilePath);
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey loadedPublicKey = keyFactory.generatePublic(publicKeySpec);*/

        // Load the file to be encrypted (JPEG image)
        byte[] fileBytes = Files.readAllBytes(Paths.get(AmdConstants.amdJpgFile));

        // Generate a random AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        SecretKey aesKey = keyGen.generateKey();

        // Initialize the cipher for AES encryption
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);

        // Encrypt the file content using AES
        byte[] encryptedFile = aesCipher.doFinal(fileBytes);

        // Encrypt the AES key using RSA
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedAesKey = rsaCipher.doFinal(aesKey.getEncoded());

        // Save the encrypted AES key and the encrypted file
        try (FileOutputStream keyFos = new FileOutputStream("C:\\amdcodechallenge\\EncryptedAESKey");
             FileOutputStream fileFos = new FileOutputStream("C:\\amdcodechallenge\\EncryptedAMDimagefile")) {
            keyFos.write(encryptedAesKey);
            fileFos.write(encryptedFile);
        }
    }
}
