package com.incarcloud.ics.core.cache;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public class LRUCache<K,V> implements Cache<K,V> ,Serializable {

    private static final long serialVersionUID = 1900228394235744386L;
    protected static final float DEFAULT_LOAD_FACTOR = 0.75f;
    protected static final int   DEFAULT_MAX_SIZE    = 100;

    private final LinkedHashMap<K,V> backingMap;
    private final Lock lock = new ReentrantLock();

    public LRUCache() {
        this(DEFAULT_MAX_SIZE);
    }

    public LRUCache(int maxSize) {
        this.backingMap = new LinkedHashMap<K,V>(maxSize, DEFAULT_LOAD_FACTOR, true){
            private static final long serialVersionUID = -6685883371757818096L;
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxSize;
            }
        };
    }

    @Override
    public V get(K key) {
        try {
            lock.lock();
            return doGet(key);
        } finally {
            lock.unlock();
        }
    }

    protected V doGet(K key){
        return backingMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        try {
            lock.lock();
            return this.doPut(key, value);
        } finally {
            lock.unlock();
        }
    }

    protected V doPut(K key, V value){
        return backingMap.put(key, value);
    }

    @Override
    public V remove(K k) {
        try {
            lock.lock();
            return backingMap.remove(k);
        } finally {
            lock.unlock();
        }
    }


    @Override
    public int size() {
        try {
            lock.lock();
            return backingMap.size();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Set<K> keys() {
        return backingMap.keySet();
    }

    @Override
    public Collection<V> values() {
        try {
            lock.lock();
            return backingMap.values();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear(){
        try {
            lock.lock();
            this.backingMap.clear();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LRUCache{");
        sb.append("backingMap=").append(backingMap);
        sb.append('}');
        return sb.toString();
    }
}
