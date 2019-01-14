package com.incarcloud.ics.core.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MapContext implements Map<String,Object>, Serializable {

    private static final long serialVersionUID = 2433421475816112986L;

    private Map<String, Object> backingMap;

    public MapContext() {
        backingMap = new HashMap<>();
    }

    public MapContext(Map<String, Object> map) {
        this();
        if(backingMap != null && !map.isEmpty()){
            this.backingMap.putAll(map);
        }
    }

    protected void nullSafePut(String key, Object value) {
        if (value != null) {
            put(key, value);
        }
    }

    @SuppressWarnings({"unchecked"})
    protected <E> E getTypedValue(String key, Class<E> type) {
        E found = null;
        Object o = backingMap.get(key);
        if (o != null) {
            if (!type.isAssignableFrom(o.getClass())) {
                String msg = "Invalid object found in SubjectContext Map under key [" + key + "].  Expected type " +
                        "was [" + type.getName() + "], but the object under that key is of type " +
                        "[" + o.getClass().getName() + "].";
                throw new IllegalArgumentException(msg);
            }
            found = (E) o;
        }
        return found;
    }

    @Override
    public int size() {
        return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return backingMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return backingMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return backingMap.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return backingMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return backingMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        backingMap.putAll(m);
    }

    @Override
    public void clear() {
        backingMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return backingMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return backingMap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return backingMap.entrySet();
    }
}
