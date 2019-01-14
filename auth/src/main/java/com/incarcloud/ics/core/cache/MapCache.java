package com.incarcloud.ics.core.cache;

import com.incarcloud.ics.core.utils.Asserts;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public class MapCache<K,V> implements Cache<K,V> {

    private Map<K,V> cacheMap;
    private String name;

    public MapCache(String name, Map<K,V> map) {
        Asserts.assertNotBlank(name);
        this.cacheMap = map;
        this.name = name;
    }

    @Override
    public V get(K k) {
        return this.cacheMap.get(k);
    }

    @Override
    public boolean put(K k, V v) {
        this.cacheMap.put(k, v);
        return true;
    }

    @Override
    public V remove(K k) {
        return this.cacheMap.remove(k);
    }

    @Override
    public int size() {
        return this.cacheMap.size();
    }

    @Override
    public Set<K> keys() {
        return this.cacheMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.cacheMap.values();
    }
}
