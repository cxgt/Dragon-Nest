package com.example.dataencryptionanddecryption.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author chenxin
 * @date 2023/08/24 15:41
 */
public class SHA512Util {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String message = "Hello World!";
        byte[] messageBytes = message.getBytes();

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digest = md.digest(messageBytes);

        System.out.println("Original message: " + message);
        System.out.println("SHA-512 hash: " + bytesToHex(digest));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02x", b);
            sb.append(hex);
        }
        return sb.toString();
    }
}
