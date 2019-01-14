package com.incarcloud.ics.core.cache;


import com.incarcloud.ics.core.exception.CacheException;

public interface CacheManager {
    <K, V> Cache<K, V> getCache(String name) throws CacheException;
}
