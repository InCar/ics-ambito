package com.incarcloud.ics.core.realm;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/21
 */
public enum OrgAccessType {
    /** 对所有数据拥有数据权限 */
    ALL,

    /** 只对当前所在组织拥有数据权限 */
    BELONG,

    /** 对所在组织及其子组织拥有数据权限 */
    MANAGE

}
