package com.incarcloud.ics.ambito.converter;

import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.jdbc.SqlEntity;
import com.incarcloud.ics.ambito.jdbc.WhereSqlEntity;

import static com.incarcloud.ics.ambito.converter.ConverterUtil.collectionAppend;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/26
 */
public class StringConditionConverter implements Converter<StringCondition, SqlEntity> {

    @Override
    public SqlEntity convert(StringCondition condition, Object... exts) {
        WhereSqlEntity sqlEntity = new WhereSqlEntity();
        if(condition.fieldName == null || condition.val == null || condition.handler == null){
            return sqlEntity;
        }
        sqlEntity.addParam(condition.val);
        sqlEntity.append("o.");
        sqlEntity.append(condition.fieldName);
        StringCondition.Handler handler = condition.handler;
        switch (handler){
            case EQUAL: {
                sqlEntity.append(" = ? ");
                break;
            }
            case NOT_EQUAL: {
                sqlEntity.append(" <> ? ");
                break;
            }
            case ALL_LIKE: {
                sqlEntity.append(" like concat('%',?,'%') ");
                break;
            }
            case LEFT_LIKE: {
                sqlEntity.append(" like concat('%',?) ");
                break;
            }
            case RIGHT_LIKE: {
                sqlEntity.append(" like concat(?,'%') ");
                break;
            }
            case IN: {
                collectionAppend(condition, sqlEntity, " in (");
                break;
            }
            case NOT_IN: {
                collectionAppend(condition, sqlEntity, " not in (");
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unimpl handler type [" + handler+"]");
            }
        }
        return sqlEntity;
    }



}
