package com.incarcloud.ics.ambito.converter;

import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.jdbc.SqlEntity;
import com.incarcloud.ics.ambito.jdbc.WhereSqlEntity;

import static com.incarcloud.ics.ambito.converter.ConverterUtil.collectionAppend;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/26
 */
public class NumberConditionConverter implements Converter<NumberCondition,SqlEntity> {

    @Override
    public SqlEntity convert(NumberCondition condition, Object... exts) {
        WhereSqlEntity sqlEntity = new WhereSqlEntity();
        sqlEntity.addParam(condition.val);
        sqlEntity.append("o.");
        sqlEntity.append(condition.fieldName);
        NumberCondition.Handler handler = condition.handler;
        switch (handler){
            case EQUAL: {
                sqlEntity.append(" = ? ");
                break;
            }
            case NOT_EQUAL: {
                sqlEntity.append(" != ? ");
                break;
            }
            case GE: {
                sqlEntity.append(" >= ? ");
                break;
            }
            case GT: {
                sqlEntity.append(" > ? ");
                break;
            }
            case LE: {
                sqlEntity.append(" <= ? ");
                break;
            }
            case LT: {
                sqlEntity.append(" < ? ");
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
