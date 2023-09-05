package com.learningRoad.EncryptUtil;

/**
 * @author chenxin
 * @date 2023/08/30 14:58
 */
public interface DecryptUtil {
    /**
     * 解密
     *
     * @param result
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    <T> T decrypt(T result) throws Exception;
}