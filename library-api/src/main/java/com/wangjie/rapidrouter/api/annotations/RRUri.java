package com.wangjie.rapidrouter.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 6/25/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface RRUri {
    String scheme();

    String host();

    RRParam[] params() default {};
}
