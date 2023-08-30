package com.example.dataencryptionanddecryption.Util;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * @author chenxin
 * @date 2023/08/24 15:14
 */
public class ECCEncryptionUtil {
    public static void main(String[] args) throws Exception {
        // 添加Bouncy Castle作为加密提供程序
        Security.addProvider(new BouncyCastleProvider());

        // 选择椭圆曲线参数
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");

        // 生成密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 明文
        String plainText = "Hello, World!";

        // 加密
        byte[] encryptedBytes = encrypt(plainText, publicKey);
        String encryptedText = Base64.toBase64String(encryptedBytes);
        System.out.println("Encrypted Text: " + encryptedText);

        // 解密
        byte[] decryptedBytes = decrypt(encryptedBytes, privateKey);
        String decryptedText = new String(decryptedBytes);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public static byte[] encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("ECIES", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes());
    }

    public static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("ECIES", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedBytes);
    }
}
