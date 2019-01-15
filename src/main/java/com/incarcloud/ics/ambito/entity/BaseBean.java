package com.incarcloud.ics.ambito.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/27
 */
public class BaseBean extends SuperBaseBean implements Serializable {

    private static final long serialVersionUID = 257515526994354578L;


    public Date createTime;
    public Long createTimeLong;

    public Date updateTime;
    public Long updateTimeLong;

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


    public Long getCreateTimeLong() {
        return createTime == null? null:createTime.getTime();
    }



    public Long getUpdateTimeLong() {
        return updateTime == null ? null : updateTime.getTime();
    }

    public void setUpdateTimeLong(Long updateTimeLong) {
        this.updateTimeLong = updateTimeLong;
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


}
