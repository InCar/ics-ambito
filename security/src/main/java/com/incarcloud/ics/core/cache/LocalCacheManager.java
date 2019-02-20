package com.incarcloud.ics.core.cache;


import com.incarcloud.ics.config.CacheConfig;
import com.incarcloud.ics.config.Config;
import com.incarcloud.ics.core.exception.CacheException;
import com.incarcloud.ics.core.utils.Asserts;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
@SuppressWarnings("unchecked")
public class LocalCacheManager<K,V> implements CacheManager{

    private static Logger logger = LoggerFactory.getLogger(LocalCacheManager.class);

    private ConcurrentHashMap<String, Cache<K,V>> cacheMap;
    private long globalTimeToLive;
    private final int globalMaxSize;
    private final boolean eternal;

    public LocalCacheManager() {
        this(ValidatingLRUCache.DEFAULT_TIME_TO_LIVE, LRUCache.DEFAULT_MAX_SIZE, ValidatingLRUCache.IS_EXTERNAL);
    }

    public LocalCacheManager(long globalTimeToLive, int globalMaxSize, boolean isEternal) {
        this.globalTimeToLive = globalTimeToLive;
        this.globalMaxSize = globalMaxSize;
        this.cacheMap = new ConcurrentHashMap<>();
        this.eternal = isEternal;
    }

    @Override
    public Cache getCache(String name) throws CacheException {
        Asserts.assertNotBlank(name);
        Cache<K,V> cache = cacheMap.get(name);
        if(cache == null){
            Config config = Config.getConfig();
            if(config == null) {
                cache = createCache(name);
            }else {
                CacheConfig cacheConfig = config.getCacheConfig(name);
                logger.debug("Use cache config : " + cacheConfig);
                cache = createCache(name, cacheConfig);
            }
            Cache<K,V> exists = cacheMap.putIfAbsent(name, cache) ;
            if(exists != null){
                cache = exists;
            }
        }
        return cache;
    }

    /**
     * 根据全局配置创建缓存
     * @param name
     * @return
     * @throws CacheException
     */
    protected Cache createCache(String name) throws CacheException {
        return new ValidatingLRUCache(this.isEternal(), name, this.getGlobalMaxSize(), this.getGlobalTimeToLive());
    }

    protected Cache createCache(String name, CacheConfig cacheConfig) throws CacheException {
        return new ValidatingLRUCache(cacheConfig.isEternal(), name, cacheConfig.getMaxSize(), cacheConfig.getTimeToLiveSeconds());
    }

    public int getGlobalMaxSize() {
        return globalMaxSize;
    }

    public long getGlobalTimeToLive() {
        return globalTimeToLive;
    }

    /**
     * 用于修改缓存过期时间
     */
    public void setGlobalTimeToLive(long globalTimeToLive) {
        this.globalTimeToLive = globalTimeToLive;
        applyGlobalTimeoutToCache();
    }

    private void applyGlobalTimeoutToCache(){
        cacheMap.values().forEach(e->{
            if(e instanceof ValidatingLRUCache){
                ((ValidatingLRUCache<K, V>) e).setTimeToLiveSeconds(this.getGlobalTimeToLive());
            }
        });
    }

    public boolean isEternal() {
        return eternal;
    }
}
