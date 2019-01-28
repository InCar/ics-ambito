package com.incarcloud.ics.core.utils;

import java.util.*;

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

    @SuppressWarnings("unchecked")
    public static <E> Set<E> asSet(E... elements) {
        if (elements != null && elements.length != 0) {
            if (elements.length == 1) {
                return Collections.singleton(elements[0]);
            } else {
                LinkedHashSet<E> set = new LinkedHashSet(elements.length * 4 / 3 + 1);
                Collections.addAll(set, elements);
                return set;
            }
        } else {
            return Collections.emptySet();
        }
    }


    public static <E> List<E> asList(E... elements) {
        return elements != null && elements.length != 0 ? Arrays.asList(elements) : Collections.emptyList();
    }

    public static boolean isNotEmpty(Map map) {
        return map != null && !map.isEmpty();
    }
}
