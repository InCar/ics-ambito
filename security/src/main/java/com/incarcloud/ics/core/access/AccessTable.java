package com.incarcloud.ics.core.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/22 2:26 PM
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessTable {
    String name() default "";
}
