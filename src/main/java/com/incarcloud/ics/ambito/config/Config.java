package com.incarcloud.ics.ambito.config;

import com.incarcloud.skeleton.config.LogConfig;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/5
 */
public class Config {

    /**
     * 是否递归删除组织，true表示递归删除�?有组织及其子组织，false表示只删除本组织
     */
    private boolean deleteOrgRecursion = false;
    private CacheConfig authorizingCache = CacheConfig.defaultConfigOf("authorizingCache");
    private CacheConfig authenticateCache =  CacheConfig.defaultConfigOf("authenticateCache");
    private CacheConfig accessCache = CacheConfig.defaultConfigOf("accessCache");
    private LogConfig logConfig = new LogConfig();


    public Config() {
    }

    public static Config getDefaultConfig(){
        return new Config();
    }

    public boolean isDeleteOrgRecursion() {
        return deleteOrgRecursion;
    }

    public void setDeleteOrgRecursion(boolean deleteOrgRecursion) {
        this.deleteOrgRecursion = deleteOrgRecursion;
    }

    public CacheConfig getAuthorizingCache() {
        return authorizingCache;
    }

    public void setAuthorizingCache(CacheConfig authorizingCache) {
        this.authorizingCache = authorizingCache;
    }

    public CacheConfig getAuthenticateCache() {
        return authenticateCache;
    }

    public void setAuthenticateCache(CacheConfig authenticateCache) {
        this.authenticateCache = authenticateCache;
    }

    public CacheConfig getAccessCache() {
        return accessCache;
    }

    public void setAccessCache(CacheConfig accessCache) {
        this.accessCache = accessCache;
    }


    /**
     * 缓存配置
     */
    public static class CacheConfig{
        private String cacheName;
        private boolean isEternal;
        private long maxSize;
        private long timeToLiveSeconds;

        public static CacheConfig defaultConfigOf(String cacheName){
            return new CacheConfigBuilder()
                    .setCacheName(cacheName)
                    .setIsEternal(false)
                    .setMaxSize(1000)
                    .setTimeToLiveSeconds(60 * 60)
                    .build();
        }

        private CacheConfig(CacheConfigBuilder builder){
            this.cacheName = builder.cacheName;
            this.isEternal = builder.isEternal;
            this.maxSize = builder.maxSize;
            this.timeToLiveSeconds = builder.timeToLiveSeconds;
        }

        public CacheConfigBuilder builder(){
            return new CacheConfigBuilder();
        }

        public String getCacheName() {
            return cacheName;
        }

        public void setCacheName(String cacheName) {
            this.cacheName = cacheName;
        }

        public boolean isEternal() {
            return isEternal;
        }

        public void setEternal(boolean eternal) {
            isEternal = eternal;
        }

        public long getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(long maxSize) {
            this.maxSize = maxSize;
        }

        public long getTimeToLiveSeconds() {
            return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
            this.timeToLiveSeconds = timeToLiveSeconds;
        }

        private static class CacheConfigBuilder {
            private String cacheName;
            private boolean isEternal;
            private long maxSize;
            private long timeToLiveSeconds;

            public CacheConfigBuilder setCacheName(String cacheName) {
                this.cacheName = cacheName;
                return this;
            }

            public CacheConfigBuilder setIsEternal(boolean isEternal) {
                this.isEternal = isEternal;
                return this;
            }

            public CacheConfigBuilder setMaxSize(long maxSize) {
                this.maxSize = maxSize;
                return this;
            }

            public CacheConfigBuilder setTimeToLiveSeconds(long timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
                return this;
            }

            public CacheConfig build(){
                return new CacheConfig(this);
            }
        }
    }

}
