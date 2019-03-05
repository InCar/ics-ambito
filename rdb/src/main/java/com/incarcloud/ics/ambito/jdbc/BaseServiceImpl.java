package com.incarcloud.ics.ambito.jdbc;


import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {
    private static final transient Logger log = Logger.getLogger(BaseServiceImpl.class.getName());

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
        try {
            return baseDao.get(id);
        }catch (EmptyResultDataAccessException e){
            log.info(e.toString());
        }
        return null;
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
     * 条件查询
     * @param condition
     * @return List<T>
     */
    @Override
    public List<T> query(Condition condition) {
        return baseDao.query(condition);
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
     * 条件分页查询
     * @param page 分页条件
     * @param condition 查询条件
     * @return
     */
    @Override
    public PageResult<T> queryPage(Page page, Condition condition) {
        return baseDao.queryPage(page, condition);
    }

    /**
     * 条件分页查询
     * @param page 分页条件
     * @param condition 查询条件
     * @param orderby 排序属性
     * @return
     */
    @Override
    public PageResult<T> queryPage(Page page, Condition condition, LinkedHashMap<String, String> orderby) {
        return baseDao.queryPage(page, condition, orderby);
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
        baseDao.update(sql, params);
        clearCachedAuthData();
        return 1;
    }  
  
    /** 
     * 更新（先从数据库取出来再更新） 
     * @param t 更新的对象 
     * @return int 更新的数量
     */  
    @Override  
    public int update(T t) {
        baseDao.update(t);
        clearCachedAuthData();
        return 1;
    }  
      
    /** 
     * 更新（通过模板更新，把符合template条件的数据都更新为value对象中的值） 
     * @param template 更新的对象
     * @return int 更新的数量
     */  
    @Override  
    public int update(T value,T template) {
        baseDao.update(value,template);
        clearCachedAuthData();
        return 1;
    }  
  
    /** 
     * 保存 
     * @param t 保存的对象 
     * @return int 保存的数量
     */  
    @Override  
    public int save(T t) {
        baseDao.save(t);
        clearCachedAuthData();
        return 1;
    }


    /**
     * 保存
     * @param ts 保存的对象
     * @return int 保存的数量
     */
    @Override
    public int saveBatch(Collection<T> ts) {
        ts.forEach(this::save);
        return ts.size();
    }


    /** 
     * 保存 
     * @param sql 自定义保存sql 
     * @param params 查询条件对应的参数(List<Object>) 
     * @return int 保存的数量
     */  
    @Override  
    public int save(String sql, List<Object> params) {  
         int n = baseDao.save(sql, params);
         clearCachedAuthData();
         return n;
    }  
  
    /** 
     * 删除 
     * @param id 对象的id(Serializable) 
     * @return int 删除的数量
     */  
    @Override
    public int delete(Serializable id) {  
        baseDao.delete(id);
        clearCachedAuthData();
        return 1;
    }


    /**
     * 批量删除
     * @param ids 对象的id(Serializable)
     * @return int 删除的数量
     */
    @Override
    @Transactional
    public int deleteBatch(Serializable[] ids) {
        for(Serializable id : ids){
            delete(id);
        }
        return ids.length;
    }


    @Override
    public int delete(Condition condition) {
        int n = baseDao.delete(condition);
        clearCachedAuthData();
        return n;
    }


    public void clearCachedAuthData(){
        if(isClearRequired()){
            doClearCachedAuthData();
        }
    }

    protected void doClearCachedAuthData(){
    }

    @Override
    public boolean isClearRequired(){
        return true;
    }
}  