package com.incarcloud.ambito.service.impl;


import com.incarcloud.ambito.dao.BaseDao;
import com.incarcloud.ambito.pojo.PageResult;
import com.incarcloud.ambito.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.incarcloud.ambito.pojo.Page;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {
  
    @Autowired
    BaseDao<T> baseDao;
      
    /** 
     * 获取实体 
     * @param id 对象的id(Serializable) 
     * @return T 对象
     */  
    @Transactional(isolation=Isolation.READ_COMMITTED,
            readOnly=true)  
    @Override  
    public T get(Serializable id) {
        return baseDao.get(id);  
    }  
  
    /** 
     * 查询 
     * @return List<T>
     */  
    @Transactional(isolation=Isolation.READ_COMMITTED,readOnly=true)  
    @Override  
    public List<T> query() {
        return baseDao.query();  
    }  
  
    /** 
     * 查询 
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>) 
     * @return List<T>
     */  
    @Transactional(isolation=Isolation.READ_COMMITTED,  
            readOnly=true)  
    @Override  
    public List<T> query(String whereSql, List<Object> params) {
        return baseDao.query( whereSql, params);
    }


    /**
     * 查询
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>)
     * @return List<T>
     */
    @Transactional(isolation=Isolation.READ_COMMITTED,
            readOnly=true)
    @Override
    public List<T> query(String whereSql, Object... params) {
        return baseDao.query(whereSql, Arrays.asList(params));
    }

    /** 
     * 查询 
     * @param orderby 排序条件（LinkedHashMap<String, String>）
     * @return List<T>
     */  
    @Transactional(isolation=Isolation.READ_COMMITTED,  
            readOnly=true)  
    @Override  
    public List<T> query(LinkedHashMap<String, String> orderby) {
        return baseDao.query(orderby);
    }  
  
    /** 
     * 查询 
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>) 
     * @param orderby 排序条件（LinkedHashMap<String, String>） 
     * @return List<T>
     */  
    @Transactional(isolation=Isolation.READ_COMMITTED,  
            readOnly=true)  
    @Override  
    public List<T> query(String whereSql, List<Object> params,
            LinkedHashMap<String, String> orderby) {  
        return baseDao.query(whereSql, params, orderby);
    }


    /**
     * 查询
     * @return List<T>
     */
    @Transactional(isolation=Isolation.READ_COMMITTED,readOnly=true)
    @Override
    public PageResult<T> queryPage(Page page) {
        return baseDao.queryPage(page);
    }

    /**
     * 查询
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>)
     * @return List<T>
     */
    @Transactional(isolation=Isolation.READ_COMMITTED,
            readOnly=true)
    @Override
    public PageResult<T> queryPage(Page page, String whereSql, List<Object> params) {
        return baseDao.queryPage(page, whereSql, params);
    }


    /**
     * 查询
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>)
     * @return List<T>
     */
    @Transactional(isolation=Isolation.READ_COMMITTED,
            readOnly=true)
    @Override
    public PageResult<T> queryPage(Page page, String whereSql, Object... params) {
        return baseDao.queryPage(page, whereSql, Arrays.asList(params));
    }

    /**
     * 查询
     * @param orderby 排序条件（LinkedHashMap<String, String>）
     * @return List<T>
     */
    @Transactional(isolation=Isolation.READ_COMMITTED,
            readOnly=true)
    @Override
    public PageResult<T> queryPage(Page page, LinkedHashMap<String, String> orderby) {
        return baseDao.queryPage(page, orderby);
    }

    /**
     * 查询
     * @param whereSql 查询条件（例:o.name=?）
     * @param params 查询条件对应的参数(List<Object>)
     * @param orderby 排序条件（LinkedHashMap<String, String>）
     * @return List<T>
     */
    @Transactional(isolation=Isolation.READ_COMMITTED,
            readOnly=true)
    @Override
    public PageResult<T> queryPage(Page page, String whereSql, List<Object> params,
                         LinkedHashMap<String, String> orderby) {
        return baseDao.queryPage(page, whereSql, params, orderby);
    }


    /** 
     * 更新 
     * @param sql 自定义更新sql 
     * @param params 查询条件对应的参数(List<Object>) 
     * @return int 更新的数量
     */  
    @Override  
    public int update(String sql, List<Object> params) {  
        return baseDao.update(sql, params);  
    }  
  
    /** 
     * 更新（先从数据库取出来再更新） 
     * @param t 更新的对象 
     * @return int 更新的数量
     */  
    @Override  
    public int update(T t) throws Exception {  
        return baseDao.update(t);  
    }  
      
    /** 
     * 更新（通过模板更新，把符合template条件的数据都更新为value对象中的值） 
     * @param template 更新的对象
     * @return int 更新的数量
     */  
    @Override  
    public int update(T value,T template) throws Exception{  
        return baseDao.update(value,template);  
    }  
  
    /** 
     * 保存 
     * @param t 保存的对象 
     * @return int 保存的数量
     */  
    @Override  
    public int save(T t) throws Exception {  
        return baseDao.save(t);  
    }  
  
    /** 
     * 保存 
     * @param sql 自定义保存sql 
     * @param params 查询条件对应的参数(List<Object>) 
     * @return int 保存的数量
     */  
    @Override  
    public int save(String sql, List<Object> params) {  
        return baseDao.save(sql, params);  
    }  
  
    /** 
     * 删除 
     * @param id 对象的id(Serializable) 
     * @return int 删除的数量
     */  
    @Override  
    public int delete(Serializable id) {  
        return baseDao.delete(id);  
    }  
  
}  