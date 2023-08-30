package com.example.dataencryptionanddecryption.Util;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
/**
 * @author chenxin
 * @date 2023/08/24 15:47
 */
public class MDUtil {


        /**
         * MD5加密，方式1
         * @param pwd
         * @return
         */
        public static String commonsCodecMd5(String pwd) {
            return DigestUtils.md5Hex(pwd.getBytes());
        }
        public static String commonsCodecMd2(String pwd) {
            return DigestUtils.md2Hex(pwd.getBytes());
        }

        /**
         * MD5加密，方式2
         *
         * @param pwd
         * @return
         */
        public static String jdkMd5(String pwd) {
            byte[] message = null;
            message = pwd.getBytes();
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] encrypwd = md.digest(message);
            String md5code = new BigInteger(1, encrypwd).toString(16);
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code = "0" + md5code;
            }
            return md5code;
        }

        public static String jdkMd2(String pwd) {
            byte[] message = null;
            message = pwd.getBytes();
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("md2");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] encrypwd = md.digest(message);
            String md5code = Hex.encodeHexString(encrypwd);
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code = "0" + md5code;
            }
            return md5code;
        }

        /**
         * Bouncy Castle实现MD4加密
         */
        public static String bouncyCastleMD4(String src) {
            /*通过这种方式给JDK动态添加一个provider,就可以通过这种方式获得JDK本身不支持的MD4了*/
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("md4");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] md4Bytes = md.digest(src.getBytes());
            return new String(org.bouncycastle.util.encoders.Hex.encode(md4Bytes));
        }

        /**
         * Bouncy Castle实现MD5加密
         */
        public static String bouncyCastleMD5(String src){
            Digest digest = new MD5Digest();
            digest.update(src.getBytes(), 0, src.getBytes().length);
            byte[]md5Bytes = new byte[digest.getDigestSize()];
            digest.doFinal(md5Bytes, 0);
            return new String(org.bouncycastle.util.encoders.Hex.encode(md5Bytes));
        }

        /**
         * Bouncy Castle实现MD2加密
         */
        public static String bouncyCastleMD2(String src){
            Digest digest = new MD2Digest();
            digest.update(src.getBytes(), 0, src.getBytes().length);
            byte[]md2Bytes = new byte[digest.getDigestSize()];
            digest.doFinal(md2Bytes, 0);
            return new String(org.bouncycastle.util.encoders.Hex.encode(md2Bytes));
        }

        /**
         *  可逆的加密算法
         * @param inStr
         * @return
         */
        public static String KL(String inStr) {
            char[] a = inStr.toCharArray();
            for (int i = 0; i < a.length; i++) {
                a[i] = (char) (a[i] ^ 't');
            }
            String s = new String(a);
            return s;
        }

        /**
         *  加密后解密
         * @param inStr
         * @return
         */
        public static String JM(String inStr) {
            char[] a = inStr.toCharArray();
            for (int i = 0; i < a.length; i++) {
                a[i] = (char) (a[i] ^ 't');
            }
            String k = new String(a);
            return k;
        }

    public static void main(String[] args) {
        String inStr = "000000";
        System.out.println("原始字符串："+inStr);
        System.out.println("MD2加密：" + jdkMd2(inStr));
        System.out.println("MD2加密：" + commonsCodecMd2(inStr));
        System.out.println("MD2加密：" + bouncyCastleMD2(inStr));
        //670b14728ad9902aecba32e22fa4f6bd  32位 
        System.out.println("MD4加密：" + bouncyCastleMD4(inStr));
        //bf85aaf547303397450fbc8ede0ec3a5  32位 
        System.out.println("MD5加密：" + jdkMd5(inStr));
        System.out.println("MD5加密：" + commonsCodecMd5(inStr));
        System.out.println("MD5加密：" + bouncyCastleMD5(inStr));
        //670b14728ad9902aecba32e22fa4f6bd  32位
        String encrypMode = jdkMd5(inStr);
        System.out.println("MD5后再加密：" + KL(encrypMode));
        System.out.println("解密为MD5后的：" + JM(KL(encrypMode)));
    }
}
