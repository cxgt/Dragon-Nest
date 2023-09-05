package com.learningRoad.annotation;

import java.lang.annotation.*;

/**
 * 敏感字段注解
 */
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveFiled {
}


