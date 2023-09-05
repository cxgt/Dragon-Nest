package com.learningRoad.annotation;

import java.lang.annotation.*;

/**
 * 敏感信息类注解
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveData {
}

