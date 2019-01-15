package com.incarcloud.ics.ambito.entity;

import com.incarcloud.ics.ambito.jdbc.Table;

import java.io.Serializable;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/15
 */
@Table(name = "t_sys_session")
public class SessionBean extends SuperBaseBean<String> implements Serializable {

    private static final long serialVersionUID = -8543325972370230532L;
    private String id;
    private byte[] session;

    public SessionBean() {
    }

    public SessionBean(byte[] session) {
        this.session = session;
    }

    public SessionBean(String id, byte[] session) {
        this.id = id;
        this.session = session;

    }

    public byte[] getSession() {
        return session;
    }

    public void setSession(byte[] session) {
        this.session = session;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
