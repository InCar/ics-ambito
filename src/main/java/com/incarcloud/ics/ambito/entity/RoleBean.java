package com.incarcloud.ics.ambito.entity;


import com.incarcloud.ics.ambito.jdbc.Table;
import com.incarcloud.ics.ambito.jdbc.Unique;
import com.incarcloud.ics.pojo.ErrorDefine;

import java.util.Set;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@Table(name = "t_sys_role")
public class RoleBean extends BaseBean{

    private static final long serialVersionUID = -3182001906942428991L;

    @Unique(message = ErrorDefine.REPEATED_NAME)
    private String roleName;

    private String remark;

    @Unique(message = ErrorDefine.REPEATED_CODE)
    private String roleCode;

    private Set<Long> userIds;

    private Set<Long> resourceIds;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public Set<Long> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
