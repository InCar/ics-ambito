package com.incarcloud.ics.core.cache;


import com.incarcloud.ics.core.exception.CacheException;
import com.incarcloud.ics.core.utils.Asserts;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
@SuppressWarnings("unchecked")
public class LocalCacheManager implements CacheManager{

    private ConcurrentHashMap<String, Cache> cacheMap;

    public LocalCacheManager() {
        this.cacheMap = new ConcurrentHashMap<>();
    }

    @Override
    public Cache getCache(String name) throws CacheException {
        Asserts.assertNotBlank(name);
        Cache cache = cacheMap.get(name);
        if(cache == null){
            cache = createCache(name);
            Cache exists = cacheMap.putIfAbsent(name, cache) ;
            if(exists != null){
                cache = exists;
            }
        }
        return cache;
    }


    @SuppressWarnings("unchecked")
    protected Cache createCache(String name) throws CacheException {
        Cache<String,Map> cache = new MapCache<>(name, new WeakHashMap());
        return cache;
    }

}
