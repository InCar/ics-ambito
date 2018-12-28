package com.incarcloud.ics.ambito.condition.impl;


import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.converter.NullConditionConverter;
import com.incarcloud.ics.ambito.jdbc.SqlEntity;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/8.
 */
public class NullCondition extends Condition {
    public Handler handler;

    public NullCondition(String fieldName, Handler handler){
        this.fieldName=fieldName;
        this.handler=handler;
    }

    public NullCondition(String fieldName){
        this(fieldName,Handler.NULL);
    }

    @Override
    public SqlEntity toSqlEntity() {
        return new NullConditionConverter().convert(this);
    }

    public enum Handler{
        /**
         * 为空
         */
        NULL,
        /**
         * 不为空
         */
        NOT_NULL
    }
}
