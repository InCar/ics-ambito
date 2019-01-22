package com.incarcloud.ics.core.realm;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/21
 */
public enum  AccessStrategy {
    /** 对所有的组织拥有数据权限 */
    ALL,

    /** 只对所属的组织有数据权限 */
    BELONG,

    /** 对所在组织及其子组织有数据权限 */
    MANAGE
}
