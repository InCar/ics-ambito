package com.incarcloud.ics.ambito.jdbc;


import com.incarcloud.ics.ambito.condition.impl.ConditionImpl;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/25
 */
public class WhereSqlEntity extends SqlEntity {

    private StringBuilder builder = new StringBuilder();

    public void append(String s){
        builder.append(s);
    }

    @Override
    public String getSql() {
        return builder.toString();
    }

    public SqlEntity merge(SqlEntity sqlEntity, ConditionImpl.ConcatWay concatWay){
        if(concatWay.equals(ConditionImpl.ConcatWay.AND)){
            andMerge(sqlEntity);
        }else if(concatWay.equals(ConditionImpl.ConcatWay.OR)){
            orMerge(sqlEntity);
        }else {
            throw new RuntimeException("Not support this concatWay ["+concatWay+"]");
        }
        return this;
    }

    protected SqlEntity andMerge(SqlEntity sqlEntity){
        builder.append(" and ");
        mergeParams(sqlEntity);
        mergeSql(sqlEntity);
        return this;
    }

    protected SqlEntity orMerge(SqlEntity sqlEntity){
        builder.append(" or ");
        mergeParams(sqlEntity);
        mergeSql(sqlEntity);
        return this;
    }

    private void mergeParams(SqlEntity sqlEntity){
        this.addAllParam(sqlEntity.getParams());
    }

    private void mergeSql(SqlEntity sqlEntity){
        builder.append(sqlEntity.getSql());
    }

    public void deleteLastChar(){
        if(builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
    }
}
