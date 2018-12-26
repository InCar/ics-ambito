package com.incarcloud.ics.ambito.converter;

/**
 * Created by Administrator on 2017/9/15.
 */
public interface Converter<T,R> {
     R convert(T condition, Object... exts);
}
