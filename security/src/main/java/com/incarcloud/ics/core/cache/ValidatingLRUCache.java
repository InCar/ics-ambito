package com.incarcloud.ics.core.cache;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ThomasChan
 * @version 1.0
 * @description 可失效的LRU缓存
 * @date 2019/1/16
 */
public class ValidatingLRUCache<K,V> extends NamedLRUCache<K,V> {

    private static final long serialVersionUID     = 1749647151672189960L;
    private static final long MINUTE_SECONDS       = 60;
    private static final long HOUR_SECONDS         = MINUTE_SECONDS * 60;
    public  static final long DEFAULT_TIME_TO_LIVE = HOUR_SECONDS;
    public  static final boolean IS_EXTERNAL       = Boolean.TRUE;

    /**
     *  默认为true表示缓存永不失效，一旦设置，timeToLiveSeconds将不生效
     */
    private boolean eternal = IS_EXTERNAL;

    /**
     *  默认为0，小于或等于0时，等同于eternal=true,表示永不过期，大于0表示缓存失效前允许存活时间,单位秒
     */
    private long timeToLiveSeconds;

    /**
     * 保存缓存的开始时间戳，用于判断缓存是否失效
     */
    private final Map<K,Long> startTimestampMap;

    public ValidatingLRUCache(String name, int maxSize, long timeToLiveSeconds) {
        this(IS_EXTERNAL, name, maxSize, timeToLiveSeconds);
    }

    public ValidatingLRUCache(boolean eternal, String name, int maxSize, long timeToLiveSeconds) {
        this(eternal, name, timeToLiveSeconds, maxSize, new ConcurrentHashMap<>());
    }

    public ValidatingLRUCache(boolean external, String name, long timeToLiveSeconds, int maxSize, Map<K,Long> startTimestampMap) {
        super(maxSize, name);
        this.eternal = external;
        this.timeToLiveSeconds = timeToLiveSeconds;
        this.startTimestampMap = startTimestampMap;
    }


    /**
     * 判断缓存是否有效
     * @param key
     * @return
     */
    protected boolean isValid(K key) {
        if(isEternal()){
            return true;
        }
        long timeToLiveSeconds = getTimeToLiveSeconds();
        //小于或等于0时，等同于eternal=true表示该缓存永不失效
        if(timeToLiveSeconds <= 0){
            return true;
        }
        //没有开始时间，表明该key没有进行缓存
        if(getStartTimestamp(key) == null){
            return false;
        }
        return getTimeToLiveSeconds() > getAlreadyLiveSeconds(key);
    }

    private long getAlreadyLiveSeconds(K key){
        return (new Date().getTime() - getStartTimestamp(key))/1000;
    }

    public long getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    public void setTimeToLiveSeconds(long timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }

    public Long getStartTimestamp(K k){
        return startTimestampMap.get(k);
    }

    public boolean isEternal() {
        return eternal;
    }

    public void setEternal(boolean eternal) {
        this.eternal = eternal;
    }


    /**
     * 每次查询缓存之前先判断缓存是否已失效，如果已经失效则移除该缓存并返回null
     * @param key
     * @return
     */
    @Override
    protected V doGet(K key) {
        if(isValid(key)){
            return super.doGet(key);
        }else {
            super.remove(key);
            return null;
        }
    }


    /**
     * 每次put后更新缓存的开始时间戳
     * @param key
     * @param value
     * @return
     */
    @Override
    protected V doPut(K key, V value) {
        V v = super.doPut(key, value);
        startTimestampMap.put(key, new Date().getTime());
        return v;
    }

}
