package com.incarcloud.ics.core.cache;

import java.util.Collection;
import java.util.Set;

public interface Cache<K,V> {

    V get(K k);

    boolean put(K k, V v);

    V remove(K k);


    int size();


    Set<K> keys();


    Collection<V> values();
}
