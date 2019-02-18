package com.incarcloud.ics.ambito.jdbc;

import com.incarcloud.ics.ambito.utils.StringUtils;
import com.incarcloud.ics.exception.AmbitoException;
import com.incarcloud.ics.pojo.ErrorDefine;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/2/18
 */
public class UniqueChecker<T> {
    private Class entityClass;
    private BaseDaoImpl<T> baseDao;

    public UniqueChecker(Class entityClass, BaseDaoImpl<T> baseDao) {
        this.entityClass = entityClass;
        this.baseDao = baseDao;
    }

    public void uniqueCheckBeforeSave(T t){
        //1、获取所有的unique字段
        List<Field> uniqueFileds = getUniqueFields();
        //2、循环遍历unique字段，判断是否唯一
        uniqueFileds.forEach(e->uniqueCheck(e, t));
    }

    private void uniqueCheck(Field field, T t){
        //根据unique字段查询数据库，获取已经存在的记录
        boolean isUnique = isUnique(field, t);
        if(!isUnique){
            throw getUniqueMessage(field).toAmbitoException();
        }
    }

    private ErrorDefine getUniqueMessage(Field field) {
        Unique annotation = field.getAnnotation(Unique.class);
        return annotation.message();
    }

    private boolean isUnique(Field field, T t){
        String fieldName = StringUtils.camelToUnderline(field.getName());
        Object filedVal = getFieldVal(field, t);
        String whereSql = fieldName + " = ?";
        Object[] params = new Object[]{filedVal};
        List<T> results =  baseDao.query(whereSql, Arrays.asList(params));
        if(results.isEmpty()){
            return true;
        }else {
            Object pkVal = getPKVal(t);
            if(pkVal != null){
                Set<T> res = results.stream().filter(e -> !getPKVal(e).equals(pkVal)).collect(Collectors.toSet());
                return res.isEmpty();
            }else {
                return false;
            }
        }
    }

    public Field getPKField(){
        List<Field> pkFields = getPKFields();
        if(pkFields.isEmpty()){
            throw new AmbitoException("There is no pk filed");
        }else if(pkFields.size() > 1){
            throw new AmbitoException("There is more than one pk field !");
        }else {
            return pkFields.get(0);
        }
    }


    private List<Field> getPKFields(){
        List<Field> pkFields = getPKFields(entityClass);
        Class superClass = entityClass.getSuperclass();
        while (!superClass.equals(Object.class)){
            pkFields.addAll(getPKFields(superClass));
            superClass = superClass.getSuperclass();
        }
        return pkFields;
    }


    private List<Field> getPKFields(Class entityClass){
        Field[] declaredFields = entityClass.getDeclaredFields();
        return Arrays.stream(declaredFields).filter(e -> e.isAnnotationPresent(Id.class)).collect(Collectors.toList());
    }

    private Object getPKVal(T t){
        Field pkField = getPKField();
        return getFieldVal(pkField, t);
    }


    private Object getFieldVal(Field field , T t){
        Object fieldVal = null;
        try {
            field.setAccessible(true);
            fieldVal = field.get(t);
            return fieldVal;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private List<Field> getUniqueFields() {
        Field[] declaredFields = entityClass.getDeclaredFields();
        return Arrays.stream(declaredFields).filter(e->e.isAnnotationPresent(Unique.class)).collect(Collectors.toList());
    }
}
