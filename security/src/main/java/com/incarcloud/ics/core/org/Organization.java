package com.incarcloud.ics.core.org;

/**
 * @Description
 * 1 该组织接口是用户的一个集合，主要作用是用于数据权限的验证，对于在其集合内的用户，可以认为拥有对该组织下资源的访问权限
 * 每个组织有唯一的标识符，系统中最高层组织的code默认为"1"，每个下级组织的code一定是用其自身code前面加上"父级code_"组
 * 成，如：
 *    组织1 code为 org1,组织2的父级为组织1, 组织2的code为 org1_org2, 如果org3的父级是org2，那么org3的组织code为
 *    org1_org2_org3
 * 2 顶级组织的code如果不指定，默认为1
 * 3 一个用户如果属于某个组织，将对该组织下所有组织都拥有数据权限
 * @Author ThomasChan
 * @Date 2018/12/19 6:23
 * @Version 1.0
 */
public interface Organization {
    String getCode();
    boolean isChief();
    Organization getParent();
    boolean isParentOf(Organization o);
    boolean isChildOf(Organization o);
}
