package com.incarcloud.ics.ambito.converter;

import com.incarcloud.ics.ambito.condition.impl.NullCondition;
import com.incarcloud.ics.ambito.jdbc.SqlEntity;
import com.incarcloud.ics.ambito.jdbc.WhereSqlEntity;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/26
 */
public class NullConditionConverter implements Converter<NullCondition, SqlEntity> {

    @Override
    public SqlEntity convert(NullCondition condition, Object... exts) {
        WhereSqlEntity sqlEntity = new WhereSqlEntity();
        if(condition.fieldName == null || condition.handler == null){
            return sqlEntity;
        }
        sqlEntity.append("o.");
        sqlEntity.append(condition.fieldName);
        NullCondition.Handler handler = condition.handler;
        switch (handler){
            case NULL: {
                sqlEntity.append(" is null ");
                break;
            }
            case NOT_NULL: {
                sqlEntity.append(" is not null ");
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unimpl handler type [" + handler+"]");
            }
        }
        return sqlEntity;
    }
}
