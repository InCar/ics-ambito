package com.incarcloud.ics.core.utils;

import java.util.Collection;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public class Asserts {

    private Asserts(){}

    public static void assertNotBlank(CharSequence c){
        if(StringUtils.isBlank(c)){
            throw new IllegalArgumentException("The argument can't be blank");
        }
    }


    public static void assertNotEmpty(Collection c){
        if(c == null || CollectionUtils.isEmpty(c)){
            throw new IllegalArgumentException("The collection can't be empty");
        }
    }

    public static void assertNotEmpty(Collection c, String message){
        if(c == null || CollectionUtils.isEmpty(c)){
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertNotNull(Object o, String argName){
        if(o == null){
            throw new IllegalArgumentException("This argument [ "+argName +" ] can't be null!");
        }
    }

    public static void assertTrue(boolean b, String s) {
        if(!b){
            throw new IllegalArgumentException(s);
        }
    }
}
