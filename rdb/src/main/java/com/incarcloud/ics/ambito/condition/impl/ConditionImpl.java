package com.incarcloud.ics.ambito.condition.impl;


import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.converter.ConditionImplConverter;
import com.incarcloud.ics.ambito.jdbc.SqlEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */
@SuppressWarnings("unchecked")
public class ConditionImpl extends Condition {
    public ConcatWay concatWay;
    public List<Condition> childrenList=new ArrayList<>();

    public ConditionImpl(ConcatWay concatWay, List<Condition> childrenList){
        this.concatWay=concatWay;
        this.childrenList=childrenList;
    }

    @Override
    public SqlEntity toSqlEntity() {
        return new ConditionImplConverter().convert(this);
    }

    public enum ConcatWay{
        AND,
        OR
    }

}
