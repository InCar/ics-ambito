package com.incarcloud.ics.core.access;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/21 5:43 PM
 */
public enum AccessControlType {

    /**
     * 过滤器，常用于查询时的过滤，可以先传入过滤条件
     */
    FILTER,

    /**
     * 断言，主要用于判断是否拥有对某条或某几条数据的权限
     */
    ASSERT
}
