package com.incarcloud.ambito.sql;

import java.beans.beancontext.BeanContextMembershipEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/25
 */
public class SqlEntity {

    private List<Object> params = new ArrayList<>();

    private String sql;

    public String getSql() {
        return sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(ArrayList<Object> objects) {
        this.params = objects;
    }

    private void addParam(Object o){
        params.add(o);
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
