package com.incarcloud.ics.ambito.converter;

import com.incarcloud.ics.ambito.condition.impl.DateCondition;
import com.incarcloud.ics.ambito.jdbc.SqlEntity;
import com.incarcloud.ics.ambito.jdbc.WhereSqlEntity;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/26
 */
public class DateConditionConverter implements Converter<DateCondition, SqlEntity> {

    @Override
    public SqlEntity convert(DateCondition condition, Object... exts) {
        WhereSqlEntity sqlEntity = new WhereSqlEntity();
        if(condition.fieldName == null || condition.val == null || condition.handler == null){
            return sqlEntity;
        }
        sqlEntity.addParam(condition.val);
        sqlEntity.append("o.");
        sqlEntity.append(condition.fieldName);
        DateCondition.Handler handler = condition.handler;
        switch (handler){
            case EQUAL: {
                sqlEntity.append(" = ? ");
                break;
            }
            case NOT_EQUAL:{
                sqlEntity.append(" <> ? ");
                break;
            }
            case GE:{
                sqlEntity.append(" >= ? ");
                break;
            }
            case GT:{
                sqlEntity.append(" > ? ");
                break;
            }
            case LE:{
                sqlEntity.append(" <= ? ");
                break;
            }
            case LT:{
                sqlEntity.append(" < ? ");
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unimpl handler type [" + handler+"]");
            }
        }
        return sqlEntity;
    }
}
