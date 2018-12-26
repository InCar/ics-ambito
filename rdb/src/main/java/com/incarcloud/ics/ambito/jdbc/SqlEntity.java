package com.incarcloud.ics.ambito.jdbc;

import java.util.ArrayList;
import java.util.Collection;
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

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(ArrayList<Object> objects) {
        this.params = objects;
    }

    @SuppressWarnings("unchecked")
    public void addParam(Object o){
        if(o instanceof Collection){
            addAllParam((List<Object>)o);
        }else {
            params.add(o);
        }
    }

    public void addAllParam(List<Object> params){
        this.params.addAll(params);
    }
}
