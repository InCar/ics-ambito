package com.incarcloud.ics.ambito.jdbc;

import com.incarcloud.ics.pojo.ErrorDefine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/2/18
 */

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    ErrorDefine message() default ErrorDefine.REPEATED_FIELD;
}
