package com.incarcloud.ics.ambito.jdbc;



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
        if(isValid()){
            builder.insert(0, "(");
            builder.append(")");
        }
        return builder.toString();
    }

    public SqlEntity andMerge(WhereSqlEntity sqlEntity){
        if(sqlEntity.isValid()) {
            builder.append(" and ");
            mergeParams(sqlEntity);
            mergeSql(sqlEntity);
        }
        return this;
    }

    public SqlEntity orMerge(WhereSqlEntity sqlEntity){
        if(sqlEntity.isValid()) {
            builder.append(" or ");
            mergeParams(sqlEntity);
            mergeSql(sqlEntity);
        }
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

    private boolean isValid(){
        return builder.length() > 0 || getParams().size() > 0;
    }
}
