package com.incarcloud.ics.ambito.condition.impl;


import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.jdbc.SqlEntity;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */
public class BooleanCondition extends Condition {
    public BooleanCondition(String fieldName, Boolean val){
        this.fieldName=fieldName;
        this.val=val;
    }

    public BooleanCondition(String fieldName){
        this(fieldName,true);
    }

    @Override
    public SqlEntity toSqlEntity() {
        return null;
    }

}
