package com.incarcloud.ics.ambito.jdbc;



/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/25
 */
public class WhereSqlEntity extends SqlEntity {

    public static final String OR = " or ";
    public static final String AND = " and ";

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
            builder.append(AND);
            mergeParams(sqlEntity);
            mergeSql(sqlEntity);
        }
        return this;
    }

    public SqlEntity orMerge(WhereSqlEntity sqlEntity){
        if(sqlEntity.isValid()) {
            builder.append(OR);
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

    public void deleteFirstStr(String str){
        if(builder.length() > 0 && builder.toString().startsWith(str)) {
            builder.delete(builder.indexOf(str), str.length());
        }
    }

    private boolean isValid(){
        return builder.length() > 0 || getParams().size() > 0;
    }

    public static void main(String[] args) {
        WhereSqlEntity whereSqlEntity = new WhereSqlEntity();
        whereSqlEntity.builder=new StringBuilder("andfasdfsfsdfand");
        whereSqlEntity.deleteFirstStr("and");
        System.out.println(whereSqlEntity.getSql());

    }
}
