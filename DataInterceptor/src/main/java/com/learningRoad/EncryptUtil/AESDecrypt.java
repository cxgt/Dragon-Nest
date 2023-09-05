package com.learningRoad.EncryptUtil;

import com.learningRoad.annotation.SensitiveFiled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author chenxin
 * @date 2023/08/30 14:58
 */
@Component
public class AESDecrypt implements DecryptUtil {
    @Value("${aes.key}")
    private String key;


    /**
     * 解密
     *
     * @param result
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public <T> T decrypt(T result) throws Exception {
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            //去除所有被EncryptDecryptFiled注解的字段
            SensitiveFiled sensitiveFiled = declaredField.getAnnotation(SensitiveFiled.class);
            if (!Objects.isNull(sensitiveFiled)) {
                //将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
                declaredField.setAccessible(true);
                //这里的result就相当于是字段的访问器
                Object object = declaredField.get(result);
                //只支持String解密
                if (object instanceof String) {
                    String value = (String) object;
                    //修改：没有标识则不解密
                    if(value.startsWith(AESUtil.KEY_SENSITIVE)) {
                        value = value.substring(10);
                        value = AESUtil.decrypt(value, key);
                    }
                    //对注解在这段进行逐一解密
                    declaredField.set(result, value);
                }
            }
        }
        return result;
    }

}
