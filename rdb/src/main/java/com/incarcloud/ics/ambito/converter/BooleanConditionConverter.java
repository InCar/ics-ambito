package com.incarcloud.ics.ambito.converter;

import com.incarcloud.ics.ambito.condition.impl.BooleanCondition;
import com.incarcloud.ics.ambito.jdbc.SqlEntity;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/26
 */
public class BooleanConditionConverter implements Converter<BooleanCondition, SqlEntity> {

    @Override
    public SqlEntity convert(BooleanCondition condition, Object... exts) {
        throw new UnsupportedOperationException("Not support this type of convert operation yet!");
    }
}
