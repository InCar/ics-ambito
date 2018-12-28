package com.incarcloud.ics.ambito.entity;

import com.incarcloud.ics.ambito.jdbc.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/27
 */
public class BaseBean implements Serializable {

    private static final long serialVersionUID = 257515526994354578L;

    @Id
    private Long id;

    public Date createTime;

    public Date updateTime;

    public Long createUserId;

    public Long updateUserId;

    public String createUser ;

    public String updateUser ;

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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseBean)) return false;
        BaseBean baseBean = (BaseBean) o;
        return Objects.equals(id, baseBean.id);
    }
}
