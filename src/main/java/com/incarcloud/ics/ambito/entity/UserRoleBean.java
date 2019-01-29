package com.incarcloud.ics.ambito.entity;


import com.incarcloud.ics.ambito.jdbc.Table;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@Table(name = "t_sys_user_role")
public class UserRoleBean extends BaseBean{
    private static final long serialVersionUID = -6743282472286010767L;
    
    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
