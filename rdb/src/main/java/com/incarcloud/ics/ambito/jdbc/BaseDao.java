package com.incarcloud.ics.ambito.jdbc;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.pojo.PageResult;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public interface BaseDao<T> {
  
     T get(Serializable id);
      
     List<T> query();

     List<T> query(String whereSql, List<Object> params);
      
     List<T> query(LinkedHashMap<String, String> orderby);

     List<T> query(Condition condition, LinkedHashMap<String, String> orderby);

     List<T> query(String whereSql, List<Object> params, LinkedHashMap<String, String> orderby);

     List<T> query(Condition condition);

     PageResult<T> queryPage(Page page);

     PageResult<T> queryPage(Page page, Condition condition);

     PageResult<T> queryPage(Page page, Condition condition, LinkedHashMap<String, String> orderby);

     PageResult<T> queryPage(Page page, String whereSql, List<Object> params);

     PageResult<T> queryPage(Page page, LinkedHashMap<String, String> orderby);

     PageResult<T> queryPage(Page page, String whereSql, List<Object> params, LinkedHashMap<String, String> orderby);

     int update(String sql, List<Object> params);  
      
     int update(T t);
      
     int update(T value, T template);
      
     int save(T t);
      
     int save(String sql, List<Object> params);  
      
     int delete(Serializable id);

    int delete(Condition condition);

    int getCount(String whereSql, Object[] objects);

}  
 

