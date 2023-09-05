package com.learningRoad.EncryptUtil;

import com.learningRoad.annotation.SensitiveFiled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author chenxin
 * @date 2023/08/30 14:53
 */
@Component
public class AESEncrypt implements EncryptUtil{

    @Value("${aes.key}")
    private String key;

    /**
     *
     * @param aesFields paramsObject所申明的字段
     * @param paramsObject mapper中paramsType的实例
     * @param <T>
     * @return
     * @throws IllegalAccessException 字段不可访问的异常
     */
    @Override
    public <T> T aesEncrypt(Field[] aesFields, T paramsObject) throws Exception {
        for (Field aesField : aesFields) {
            //取出所有被EncryptDecryptFiled注解的字段
            SensitiveFiled filed = aesField.getAnnotation(SensitiveFiled.class);
            if (!Objects.isNull(filed)) {
                //将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
                aesField.setAccessible(true);
                Object object = aesField.get(paramsObject);
                //这里暂时只对String类型来加密
                if (object instanceof String) {
                    String value = (String) object;
                    String encrypt = value;
                    //修改: 如果有标识则不加密，没有则加密并加上标识前缀
                    if(!value.startsWith(AESUtil.KEY_SENSITIVE)) {
                        encrypt = AESUtil.encrypt(value, key);
                        encrypt = AESUtil.KEY_SENSITIVE + encrypt;
                    }
                    //开始对字段加密使用自定义的AES加密工具
                    aesField.set(paramsObject, encrypt);
                }
            }
        }
        return paramsObject;
    }
}

