package com.incarcloud.ics.core.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/21
 * @Version 1.0
 */
public class CollectionUtils {

    private CollectionUtils(){}

    public static boolean isNotEmpty(Collection collection){
        return collection != null && !collection.isEmpty();
    }

    public static boolean isEmpty(Collection collection){
        return collection
         == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map){
        return map == null || map.isEmpty();
    }
}
