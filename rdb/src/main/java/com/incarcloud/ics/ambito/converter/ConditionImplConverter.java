package com.incarcloud.ics.ambito.converter;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.ConditionImpl;
import com.incarcloud.ics.ambito.jdbc.WhereSqlEntity;

import java.util.*;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/26
 */
public class ConditionImplConverter implements Converter<ConditionImpl, WhereSqlEntity> {
    @Override
    public WhereSqlEntity convert(ConditionImpl condition, Object... exts) {
        List<Condition> childrenList = condition.childrenList;
        WhereSqlEntity sqlEntity = new WhereSqlEntity();
        if(childrenList.isEmpty()){
            return sqlEntity;
        }

        childrenList.stream().filter(e -> e.val != null && (!(e.val instanceof Collection) || !((Collection) e.val).isEmpty())).map(e->e.toSqlEntity()).forEach(e->sqlEntity.merge(e, condition.concatWay));
        return sqlEntity;
    }
}
