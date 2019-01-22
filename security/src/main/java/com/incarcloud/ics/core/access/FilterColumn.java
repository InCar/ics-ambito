package com.incarcloud.ics.core.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ThomasChan
 * @version 1.0
 * @description 进行数据权限过滤所依赖的字段，默认根据组织进行数据权限过滤，对应的类型为 FilterType.ORGANIZATION
 * @date 2019/1/21 2:41 PM
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterColumn {
    FilterType type() default FilterType.ORGANIZATION;
}
