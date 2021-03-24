package com.p3solutions.license.crypto;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 
 * @author Seelan
 * 
 */
public class CryptographyUtil {

    private static final String ALGORITHM = "RSA";

    public static byte[] encrypt(byte[] publicKey, byte[] inputData)
            throws Exception {

        PublicKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(publicKey));
        
        key.getEncoded();

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(inputData);

        return encryptedBytes;
    }

    public static byte[] decrypt(byte[] privateKey, byte[] inputData)
            throws Exception {

        PrivateKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(privateKey));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedBytes = cipher.doFinal(inputData);

        return decryptedBytes;
    }

    public static KeyPair generateKeyPair()
            throws NoSuchAlgorithmException, NoSuchProviderException {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        // 4096 is keysize
        keyGen.initialize(4096, random);

        KeyPair generateKeyPair = keyGen.generateKeyPair();
        return generateKeyPair;
    }

//    public static void generateKeys() {
    	
    	
//    	try {
//    		KeyPair generateKeyPair = generateKeyPair();
//
//            byte[] publicKey = generateKeyPair.getPublic().getEncoded();
//            byte[] privateKey = generateKeyPair.getPrivate().getEncoded();
//            
//            FileGenerator.writeByteToFile(publicKey, new File("publicKey.pk"));
//            FileGenerator.writeByteToFile(privateKey, new File("privateKey.pk"));
//            
//            System.out.println("Keys generated successfully");
//    	}
//        catch (Exception e){
//        	System.out.println("Failed to generate keys");
//    		e.printStackTrace();
//    	}

//        byte[] encryptedData = encrypt(publicKey,
//                "hi this is Visruth here".getBytes());
//
//        byte[] decryptedData = decrypt(privateKey, encryptedData);
//
//        System.out.println(new String(decryptedData));

//    }

}