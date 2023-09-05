package com.learningRoad.Interceptor;

import com.learningRoad.EncryptUtil.AESEncrypt;
import com.learningRoad.annotation.SensitiveData;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Properties;

/**
 * @author chenxin
 * @date 2023/08/30 14:59
 */
@Slf4j
@Component
/**
 * @Intercepts注解开启拦截器
 * type 属性指定当前拦截器使用StatementHandler 、ResultSetHandler、ParameterHandler，Executor的一种
 * method 属性指定使用以上四种类型的具体方法（可进入class内部查看其方法）。
 * args 属性指定预编译语句
 */
@Intercepts({
        //@Signature注解定义拦截器的实际类型
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
})
public class EncryptInterceptor implements Interceptor {

    private final AESEncrypt encryptUtil;

    @Autowired
    public EncryptInterceptor(AESEncrypt encryptUtil) {
        this.encryptUtil = encryptUtil;
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //@Signature 指定了 type= parameterHandler 后，这里的 invocation.getTarget() 便是parameterHandler
        //若指定ResultSetHandler ，这里则能强转为ResultSetHandler
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        //获取参数对象，即mapper中paramsType的实例
        Field paramsFiled = parameterHandler.getClass().getDeclaredField("parameterObject");
        //将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
        paramsFiled.setAccessible(true);
        //取出实例
        Object parameterObject = paramsFiled.get(parameterHandler);
        if (parameterObject != null) {
            Class<?> parameterObjectClass = parameterObject.getClass();
            //校验该实例的类是否被@SensitiveData所注解
            SensitiveData sensitiveData = AnnotationUtils.findAnnotation(parameterObjectClass, SensitiveData.class);
            if (Objects.nonNull(sensitiveData)) {
                //取出当前类的所有字段，传入加密方法
                Field[] declaredFields = parameterObjectClass.getDeclaredFields();
                encryptUtil.aesEncrypt(declaredFields, parameterObject);
            }
        }
        //获取原方法的返回值
        return invocation.proceed();
    }

    /**
     * 一定要配置，加入此拦截器到拦截器链
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}

