package com.learningRoad.EncryptUtil;

import java.lang.reflect.Field;

/**
 * @author chenxin
 * @date 2023/08/30 14:53
 */
public interface EncryptUtil {
    /**
     *
     * @param aesFields paramsObject所申明的字段
     * @param paramsObject mapper中paramsType的实例
     * @param <T>
     * @return
     * @throws IllegalAccessException 字段不可访问的异常
     * 这里为了写这个接口为了以后可以拓展掐他的加密类型
     */
    <T> T aesEncrypt(Field[] aesFields, T paramsObject) throws Exception;
}
