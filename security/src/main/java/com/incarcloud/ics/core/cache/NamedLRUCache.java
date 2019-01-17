package com.incarcloud.ics.core.cache;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public class NamedLRUCache<K,V> extends LRUCache<K,V> {

    private static final long serialVersionUID = -3951722153025530119L;

    private String name;

    public NamedLRUCache(String name) {
        super();
        this.name = name;
    }

    public NamedLRUCache(int maxSize, String name) {
        super(maxSize);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
