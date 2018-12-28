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
        if(condition.concatWay.equals(ConditionImpl.ConcatWay.OR)){
            sqlEntity = childrenList.stream()
                    .map(e -> (WhereSqlEntity) e.toSqlEntity())
                    .reduce((e1, e2) -> (WhereSqlEntity) e1.orMerge(e2))
                    .orElse(sqlEntity);
        }else if(condition.concatWay.equals(ConditionImpl.ConcatWay.AND)){
            sqlEntity = childrenList.stream()
                    .map(e -> (WhereSqlEntity) e.toSqlEntity())
                    .reduce((e1, e2) -> (WhereSqlEntity) e1.andMerge(e2))
                    .orElse(sqlEntity);
        }else {
            throw new UnsupportedOperationException("Not support concat way: "+ condition.concatWay);
        }
        return sqlEntity;
    }

}
