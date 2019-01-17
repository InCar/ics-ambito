package com.incarcloud.ics.core.role;

import java.io.Serializable;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public class SimpleRole implements Serializable {

    private static final long serialVersionUID = -7799112276642646040L;
    private String name;

    public SimpleRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
