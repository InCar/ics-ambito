package com.incarcloud.ics.ambito.entity;


import com.incarcloud.ics.ambito.jdbc.Table;

import java.util.Date;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26 14:05
 */
@Table(name = "t_sys_org_user")
public class SysOrgUserBean {

    private Long id;

    // 组织机构id
    private Long orgId;

    // 用户id
    private Long userId;

    // 更新用户id
    private Long updateUserId;

    // 创建用户id
    private Long createUserId;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;


    public SysOrgUserBean() {
    }

    public SysOrgUserBean(Long orgId, Long userId) {
        this.orgId = orgId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SysOrgUserBean{" +
                "id=" + id +
                ", orgId=" + orgId +
                ", userId=" + userId +
                ", updateUserId=" + updateUserId +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}