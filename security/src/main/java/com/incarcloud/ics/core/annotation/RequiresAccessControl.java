package com.incarcloud.ics.core.annotation;

import com.incarcloud.ics.core.access.AccessControlType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/18
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAccessControl {
    Class entityClassName();
    AccessControlType controlType() default AccessControlType.FILTER;
}
