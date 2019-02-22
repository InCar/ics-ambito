package com.incarcloud.ics.ambito.entity;

import com.incarcloud.ics.ambito.jdbc.Table;
import com.incarcloud.ics.ambito.jdbc.Unique;
import com.incarcloud.ics.pojo.ErrorDefine;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@Table(name = "t_sys_user")
public class UserBean  extends ExtendableBean{

    private static final long serialVersionUID = 5952695047462635088L;

    @Unique(message = ErrorDefine.REPEATED_USERNAME)
    private String username;

    private String password;

    private String realName;

    private Byte gender;

    private String email;

    @Unique(message = ErrorDefine.REPEATED_PHONE)
    private String phone;

    private String remark;

    private State state = State.enabled;

    private String salt;

    private List<Long> orgIds;

    public enum State { enabled, disabled }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }
}
