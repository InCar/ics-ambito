package com.incarcloud.ics.ambito.entity;


import com.incarcloud.ics.ambito.jdbc.Table;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@Table(name = "t_sys_role_resource")
public class RoleResourceBean extends SuperBaseBean<Long> {
    private static final long serialVersionUID = -1453921502631278952L;
    private Long resourceId;
    private Long roleId;

    public RoleResourceBean() {
    }

    public RoleResourceBean(Long resourceId, Long roleId) {
        this.resourceId = resourceId;
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
