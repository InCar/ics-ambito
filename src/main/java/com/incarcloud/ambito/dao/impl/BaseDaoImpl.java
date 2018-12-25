package com.incarcloud.ambito.dao.impl;

import com.incarcloud.ambito.annotation.Id;
import com.incarcloud.ambito.pojo.PageResult;
import com.incarcloud.ambito.sql.SqlEntity;
import com.incarcloud.ambito.annotation.Table;
import com.incarcloud.ambito.dao.BaseDao;
import com.incarcloud.ambito.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.incarcloud.ambito.pojo.Page;
import com.incarcloud.ambito.utils.StringUtils;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked","rawtypes"})
public class BaseDaoImpl<T> implements BaseDao<T> {
      
      
    @Autowired
    JdbcTemplate jdbcTemplate;

    protected Class<T> entityClass = (Class<T>) getEntityClass(this.getClass());
  
    private RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(entityClass);

    private String getTableName(){
        return entityClass.getAnnotation(Table.class).name();
    }

    private String camalToUnderline(Field field){
        return StringUtils.camelToUnderline(field.getName());
    }


    private Object getEntityClass(Class<? extends BaseDaoImpl> aClass) {
        return ((ParameterizedType)aClass.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private boolean isEmpty(Object objectValue) {
        return objectValue == null;
    }

    /** 
     * 获取实体 
     * @param id 对象的id(Serializable) 
     * @return T 对象 
     *   
     *  
     */  
    @Override  
    public T get(Serializable id) {
        String sql = getSql() + "and id=? ";  
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }  

    /** 
     * 查询 
     * @return List<T> 
     *   
     *  
     */  
    @Override  
    public List<T> query() {  
        return  jdbcTemplate.query(getSql(), rowMapper);
    }  
  
    /** 
     * 查询 
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>) 
     * @return List<T>
     */  
    @Override  
    public List<T> query(String whereSql, List<Object> params) {
        return query(whereSql, params, new LinkedHashMap<>());
    }  
  
    /** 
     * 查询 
     * @param orderby 排序条件（LinkedHashMap<String, String>）
     * @return List<T>
     */  
    @Override  
    public List<T> query(LinkedHashMap<String, String> orderby) {
        return query(null, new ArrayList<>(), orderby);
    }


//    /**
//     * 查询
//     * @param page 分页参数
//     * @param whereSql 查询条件（例:o.name=?）
//     * @param params 查询条件对应的参数(List<Object>)
//     * @param orderby 排序条件（LinkedHashMap<String, String>）
//     * @return List<T>
//     */
//    @Override
//    public List<T> query(Page page, String whereSql, List<Object> params, LinkedHashMap<String, String> orderby) {
//        List<Object> paramsList = new ArrayList<Object>();
//        if(!StringUtils.isEmpty(whereSql) && !CollectionUtils.isEmpty(params)){
//            for (Object object : params) {
//                if(object instanceof Enum){
//                    paramsList.add(((Enum)object).ordinal());
//                }else{
//                    paramsList.add(object);
//                }
//            }
//        }
//
//        String sql = getSql(page, whereSql, orderby);
//        dealPage(page, sql, paramsList);
//
//        if(page.isValidPage()){
//            paramsList.add(page.getOffSize());
//            paramsList.add(page.getCurrentSize());
//        }
//
//        return jdbcTemplate.query(sql, rowMapper, paramsList.toArray());
//    }

    /**
     * 查询
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>)
     * @param orderby 排序条件（LinkedHashMap<String, String>）
     * @return List<T>
     */
    @Override
    public List<T> query(String whereSql, List<Object> params, LinkedHashMap<String, String> orderby) {
        List<Object> paramsList = new ArrayList<Object>();
        if(!StringUtils.isEmpty(whereSql) && !CollectionUtils.isEmpty(params)){
            for (Object object : params) {
                if(object instanceof Enum){
                    paramsList.add(((Enum)object).ordinal());
                }else{
                    paramsList.add(object);
                }
            }
        }
        String sql = getSql(whereSql, orderby);
        return jdbcTemplate.query(sql, rowMapper, paramsList.toArray());
    }



    /**
     * 查询
     * @param page 分页参数
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>)
     * @param orderby 排序条件（LinkedHashMap<String, String>）
     * @return List<T>
     */
    @Override
    public PageResult<T> queryPage(Page page, String whereSql, List<Object> params, LinkedHashMap<String, String> orderby) {
        List<Object> paramsList = new ArrayList<Object>();
        if(!StringUtils.isEmpty(whereSql) && !CollectionUtils.isEmpty(params)){
            for (Object object : params) {
                if(object instanceof Enum){
                    paramsList.add(((Enum)object).ordinal());
                }else{
                    paramsList.add(object);
                }
            }
        }

        String sql = getSql(page, whereSql, orderby);
        dealPage(page, sql, paramsList);

        if(page.isValidPage()){
            paramsList.add(page.getOffSize());
            paramsList.add(page.getCurrentSize());
        }
        List<T> result = jdbcTemplate.query(sql, rowMapper, paramsList.toArray());
        return new PageResult<>(result, page);
    }


    /**
     * 查询
     * @param page 分页参数
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>)
     * @return List<T>
     */
    @Override
    public PageResult<T> queryPage(Page page, String whereSql, List<Object> params) {
        return queryPage(page, whereSql, params, new LinkedHashMap<>());
    }


    /**
     * 查询
     * @param page 分页参数
     * @param orderby 排序条件（LinkedHashMap<String, String>）
     * @return List<T>
     */
    @Override
    public PageResult<T> queryPage(Page page, LinkedHashMap<String, String> orderby) {
        return queryPage(page, null, new ArrayList<>(), orderby);
    }


    /**
     * 查询
     * @param page 分页参数
     * @return List<T>
     */
    @Override
    public PageResult<T> queryPage(Page page) {
        return queryPage(page, new LinkedHashMap<>());
    }


    /** 
     * 更新 
     * @param sql 自定义更新sql 
     * @param params 查询条件对应的参数(List<Object>) 
     * @return int 更新的数量 
     *
     */  
    @Override  
    public int update(String sql, List<Object> params) {  
        return jdbcTemplate.update(sql, params.toArray());
    }  
      
    /** 
     * 更新（先从数据库取出来再更新） 
     * @param t 更新的对象 
     * @return int 更新的数量 
     *   
     *  
     */  
    @Override  
    public int update(T t) throws Exception{  
        SqlEntity sqlEntity = getUpdateSql(t);  
        //System.out.println("=====sqlEntity.getSql()="+sqlEntity.getSql());  
        return jdbcTemplate.update(sqlEntity.getSql(), sqlEntity.getParams().toArray());  
    }  
      
    /** 
     * 更新（通过模板更新，把符合template条件的数据都更新为value对象中的值） 
     * @param template 更新的对象
     * @return int 更新的数量 
     *   
     *  
     */  
    @Override  
    public int update(T value,T template) throws Exception{  
        SqlEntity sqlEntity = getUpdateSql(value,template);
        //System.out.println("=====update(T value,T template) sqlEntity.getSql()="+sqlEntity.getSql());  
        return jdbcTemplate.update(sqlEntity.getSql(), sqlEntity.getParams().toArray());  
    }  
      
    /** 
     * 保存 
     * @param t 保存的对象 
     * @return int 保存的数量 
     *   
     *  
     */  
    @Override  
    public int save(T t) throws Exception{  
        SqlEntity sqlEntity = getSaveSql(t);  
        return jdbcTemplate.update(sqlEntity.getSql(), sqlEntity.getParams().toArray());  
    }
  
    /** 
     * 保存 
     * @param sql 自定义保存sql 
     * @param params 查询条件对应的参数(List<Object>) 
     * @return int 保存的数量 
     *   
     *  
     */  
    @Override  
    public int save(String sql, List<Object> params) {  
        return jdbcTemplate.update(sql, params.toArray());
    }  
  
    /** 
     * 删除 
     * @param id 对象的id(Serializable) 
     * @return int 删除的数量 
     *   
     *  
     */  
    @Override  
    public int delete(Serializable id) {  
        String sql="delete from " + getTableName() + " where id=?";
        return jdbcTemplate.update(sql, id);  
    }  
      
    @Override
    public int getCount(String whereSql, Object[] objects){
        StringBuffer sql = new StringBuffer("select count(*) from ");  
        sql.append(getTableName());
        sql.append(" o ").append(whereSql);
        int count = jdbcTemplate.queryForObject(sql.toString(), objects, Integer.class);
        return count;
    }


    protected String getSql(){
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(getTableName());
        sql.append(" o where 1=1 ");
        return sql.toString();
    }

    protected String getSql(String whereSql){
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(getTableName());
        sql.append(" o where 1=1 ");
        if(!StringUtils.isEmpty(whereSql)){
            sql.append(" and ").append(whereSql);
        }
        return sql.toString();
    }

    /**
     * 获取sql
     * @param page 分页参数，如果为空，则不在sql增加limit ?,?
     * @param orderby 排序参数，如果为空，则不在sql增加ORDER BY
     * @param whereSql 查询条件参数，如果为空，则不在sql增加 and name=?
     * @return sql
     */
    protected String getSql(Page page, String whereSql, Map<String,String> orderby){
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(getTableName());
        sql.append(" o where 1=1 ");  
        if(!StringUtils.isEmpty(whereSql)){  
            sql.append(" and ").append(whereSql);
        }  
        if(!CollectionUtils.isEmpty(orderby)){
            sql.append(" ORDER BY ");  
            for (String string : orderby.keySet()) {  
                String value = orderby.get(string);  
                if(StringUtils.isEmpty(value)){  
                    value = "ASC";  
                }  
                sql.append("o.").append(string).append(" ").append(value.toUpperCase()).append(",");  
            }  
            if(sql.indexOf(",") > -1){  
                sql.deleteCharAt(sql.length()-1);  
            }  
        }  
        if(page != null && page.isValidPage()){
            sql.append(" limit ?,? ");  
        }  
        return sql.toString();
    }

    /**
     * 获取sql
     * @param orderby 排序参数，如果为空，则不在sql增加ORDER BY
     * @param whereSql 查询条件参数，如果为空，则不在sql增加 and name=?
     * @return sql
     */
    protected String getSql(String whereSql, Map<String,String> orderby){
        return getSql(null, whereSql, orderby);
    }


    /**
     * 获取sql
     * @param page 分页参数，如果为空，则不在sql增加limit ?,?
     * @param orderby 排序参数，如果为空，则不在sql增加ORDER BY
     * @return sql
     */
    protected String getSql(Page page, Map<String,String> orderby){
        return getSql(page, null, orderby);
    }

    /**
     * 获取sql 默认按id倒序排列
     * @param page 分页参数，如果为空，则不在sql增加limit ?,?
     * @param
     * @return sql
     */
    protected String getSql(Page page){
        Map<String,String> orderby = new LinkedHashMap<>();
        orderby.put("id", "DESC");
        return getSql(page, orderby);
    }


    private SqlEntity getUpdateSql(T t) throws Exception{  
        SqlEntity sqlEntity = new SqlEntity();
        sqlEntity.setParams(new ArrayList<>());
        Field[] fields = entityClass.getDeclaredFields();  
        StringBuffer sql = new StringBuffer();
        sql.append("update ").append(getTableName()).append(" o set ");
        for (Field field : fields) {
            StringBuffer methodName = new StringBuffer();
            upperCaseFirstLetter(field, methodName);
            if(!field.isAnnotationPresent(Id.class)){
                Method method = entityClass.getMethod(methodName.toString(), new Class[]{});
                Object objectValue = method.invoke(t, new Object[]{});
                if(objectValue instanceof Enum){
                    sqlEntity.getParams().add(((Enum)objectValue).ordinal());
                }else{
                    sqlEntity.getParams().add(objectValue);
                }
                sql.append(" o.").append(StringUtils.camelToUnderline(field.getName())).append("= ?,");
            }
        }
        if(sql.indexOf(",") > -1){
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(" where o.id=?");
        Method idMethod = entityClass.getMethod("getId", new Class[]{});
        sqlEntity.getParams().add(idMethod.invoke(t, new Object[]{}));
        sqlEntity.setSql(sql.toString());
        System.out.println(sqlEntity.getSql());
        System.out.println(sqlEntity.getParams());
        return sqlEntity;
    }

    private void upperCaseFirstLetter(Field field, StringBuffer methodName) {
        if(field.getType() == boolean.class){
            if(field.getName().contains("is")){
                methodName.append(field.getName());
            }else{
                methodName.append("is").append(StringUtils.capitalize(field.getName()));
            }
        }else{
            methodName.append("get").append(StringUtils.capitalize(field.getName()));
        }
    }

    private SqlEntity getUpdateSql(T value, T template) throws Exception{

        SqlEntity sqlEntity = new SqlEntity();
        sqlEntity.setParams(new ArrayList<>());
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer sql = new StringBuffer();
        sql.append("update ").append(getTableName()).append(" o set ");
        StringBuffer whereSql = new StringBuffer(" where ");
        for (Field field : fields) {
            StringBuffer methodName = new StringBuffer();
            upperCaseFirstLetter(field, methodName);
            if(!field.isAnnotationPresent(Id.class)){
                Method method = entityClass.getMethod(methodName.toString(), new Class[]{});
                Object objectValue = method.invoke(value, new Object[]{});
                if(!isEmpty(objectValue)){
                    if(objectValue instanceof Enum){
                        sqlEntity.getParams().add(((Enum)objectValue).ordinal());
                    }else{
                        sqlEntity.getParams().add(objectValue);
                    }
                    sql.append(" o.").append(StringUtils.camelToUnderline(field.getName())).append("= ?,");
                }
            }
        }

        for (Field field : fields) {
            StringBuffer methodName = new StringBuffer();
            upperCaseFirstLetter(field, methodName);
            Method method = entityClass.getMethod(methodName.toString(), new Class[]{});
            Object objectValue = method.invoke(template, new Object[]{});
            if(!isEmpty(objectValue)){
                sqlEntity.getParams().add(objectValue);
                whereSql.append(" o.").append(StringUtils.camelToUnderline(field.getName())).append("= ? and");
            }
        }
        if(sql.indexOf(",") > -1){
            sql.deleteCharAt(sql.length() - 1);
        }
        if(whereSql.indexOf("and") > -1){
            sql.append(whereSql.substring(0, whereSql.length()-3));
            whereSql = new StringBuffer();
        }else{
            sql.append(whereSql);
        }
        sqlEntity.setSql(sql.toString());
        return sqlEntity;
    }

    private SqlEntity getSaveSql(T t) throws Exception{
        SqlEntity sqlEntity = new SqlEntity();
        Field[] fields = entityClass.getDeclaredFields();
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ").append(getTableName()).append(" ( ");
        int paramLength = 0;  
        for (Field field : fields) {  
            StringBuffer methodName = new StringBuffer();
            upperCaseFirstLetter(field, methodName);
            Method method = entityClass.getMethod(methodName.toString(), new Class[]{});  
            Object value = method.invoke(t, new Object[]{});  
            if(!isEmpty(value)){
                if(value instanceof Enum){  
                    sqlEntity.getParams().add(((Enum) value).ordinal());  
                }else{  
                    sqlEntity.getParams().add(value);  
                }  
                sql.append("`").append(StringUtils.camelToUnderline(field.getName())).append("`").append(",");
                paramLength ++;  
            }  
        }  
        if(sql.indexOf(",") > -1){  
            sql.deleteCharAt(sql.length() - 1);  
        }  
        sql.append(") values(");  
        for (int i=0;i<paramLength;i++) {  
            sql.append("?,");  
        }  
        if(sql.indexOf(",") > -1){  
            sql.deleteCharAt(sql.length() - 1);  
        }  
        sql.append(")");  
        //System.out.println("sql.toString()="+sql.toString());  
        sqlEntity.setSql(sql.toString());  
        return sqlEntity;  
    }  
      
    private void dealPage(Page page, String sql, List<Object> params){
        String whereSql = "";
        System.out.println(sql);
        if(sql != null && !sql.trim().equals("")){  
            int whereIndex = sql.toLowerCase().indexOf("where");  
            int orderIndex = sql.toLowerCase().indexOf("order");  
            int limitIndex = sql.toLowerCase().indexOf("limit");  
            if(whereIndex > -1){  
                whereSql = sql.substring(whereIndex, sql.length());  
                orderIndex = whereSql.toLowerCase().indexOf("order");  
            }  
            if(whereIndex > -1 && orderIndex > -1){  
                whereSql = whereSql.substring(0, orderIndex - 1);  
                limitIndex = whereSql.toLowerCase().indexOf("limit");  
            }  
            if(whereIndex > -1 && limitIndex > -1){
                limitIndex = whereSql.toLowerCase().indexOf("limit");
                whereSql = whereSql.substring(0, limitIndex - 1);  
            }  
        }

        page.setTotalSize(getCount(whereSql, params.toArray()));
//        if(page.isTotalSizeNew()){
//            page.setTotalSize(getCount(whereSql, params.toArray()));
//        }
//        setPage(page);
    }

//
//    private void setPage(Page page){
//        page.setTotalPages(page.getTotalSize()%page.getCurrentSize()==0?page.getTotalSize()/page.getCurrentSize():(page.getTotalSize()/page.getCurrentSize()+1));
//        page.setCurrentPage(page.getOffSize()/page.getCurrentSize()+1);
//    }
}  