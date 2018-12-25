package com.incarcloud.ambito.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/25
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection c){
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(Map c){
        return c == null || c.isEmpty();
    }
}
