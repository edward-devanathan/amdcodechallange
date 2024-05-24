package org.amd.rsa;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSAKeyPairGenerator {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048); // Set key size to 2048 bits
        KeyPair keyPair = generator.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // Save keys to files or use them as needed
        // (Remember to handle keys securely!)
        savePrivateKeyToFile(privateKey, "C:\\amdcodechallenge\\privateKey.key");
        savePublicKeyToFile(publicKey, "C:\\amdcodechallenge\\publicKey.key");

    }

    private static void savePublicKeyToFile(PublicKey publicKey, String filePath) {
        try(FileOutputStream fos = new FileOutputStream(filePath)){
            fos.write(Base64.getEncoder().encode(publicKey.getEncoded()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void savePrivateKeyToFile(PrivateKey privateKey, String filePath) throws FileNotFoundException {
        try(FileOutputStream fos = new FileOutputStream(filePath)){
            fos.write(Base64.getEncoder().encode(privateKey.getEncoded()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
