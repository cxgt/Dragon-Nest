package com.example.dataencryptionanddecryption.Util;

import cn.hutool.core.codec.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * @author chenxin
 * @date 2023/08/21 11:27
 */
public class DESUtil {
    public static void main(String[] args) throws Exception
    {
        System.out.println("请输入明文：");
        Scanner in=new Scanner(System.in);
        String clearText=in.nextLine();
        System.out.println("请输入密钥：");
        Scanner in1=new Scanner(System.in);
        String originKey=in1.nextLine();
        String cipherText = desEncript(clearText, originKey);
        System.out.println("通过DES加密后的结果是：");
        System.out.println(cipherText);
        String clearText1=desDecript(cipherText,originKey);
        System.out.println("解密结果是：\n"+clearText1);
    }

    /**
     * 加密算法
     * @param clearText
     * @param originKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static String desEncript(String clearText, String originKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        Cipher cipher=Cipher.getInstance("DES"); /*提供加密的方式：DES*/
        SecretKeySpec key=getKey(originKey);  /*对密钥进行操作，产生16个48位长的子密钥*/
        cipher.init(Cipher.ENCRYPT_MODE,key); /*初始化cipher，选定模式，这里为加密模式，并同时传入密钥*/
        byte[] doFinal=cipher.doFinal(clearText.getBytes());   /*开始加密操作*/
        String encode= Base64.encode(doFinal);    /*对加密后的数据按照Base64进行编码*/
        return encode;
    }

    /**
     * 解密算法
     * @param cipherText
     * @param originKey
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private static String desDecript(String cipherText, String originKey) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException
    {
        Cipher cipher=Cipher.getInstance("DES");   /*初始化加密方式*/
        Key key=getKey(originKey);  /*获取密钥*/
        cipher.init(Cipher.DECRYPT_MODE,key);  /*初始化操作方式*/
        byte[] decode=Base64.decode(cipherText);  /*按照Base64解码*/
        byte[] doFinal=cipher.doFinal(decode);   /*执行解码操作*/
        return new String(doFinal);   /*转换成相应字符串并返回*/
    }

    /**
     * 获取密钥算法
     * @param originKey
     * @return
     */
    private static SecretKeySpec getKey(String originKey)
    {
        byte[] buffer=new byte[8];
        byte[] originBytes=originKey.getBytes();
        /**
         * 防止输入的密钥长度超过64位
         */
        for(int i=0;i<8&&i<originBytes.length;i++)
        {
            buffer[i]=originBytes[i];  /*如果originBytes不足8,buffer剩余的补零*/
        }
        SecretKeySpec key=new SecretKeySpec(buffer,"DES"); /*第一个参数是密钥字节数组，第二个参数是加密方式*/
        return key;  /*返回操作之后得到的密钥*/
    }
}

