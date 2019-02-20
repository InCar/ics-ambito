package com.incarcloud.ics.config;

/**
 * 缓存配置
 */
public class CacheConfig{
    private final String cacheName;
    private boolean isEternal;
    private int maxSize;
    private int timeToLiveSeconds;

    public static CacheConfig getDefaultConfigOfCache(String cacheName){
        return new CacheConfig(cacheName);
    }

    public CacheConfig(String cacheName) {
        this(getBuilder(cacheName));
    }

    private CacheConfig(CacheConfigBuilder builder){
        this.cacheName = builder.cacheName;
        this.isEternal = builder.isEternal;
        this.maxSize = builder.maxSize;
        this.timeToLiveSeconds = builder.timeToLiveSeconds;
    }

    public static CacheConfigBuilder getBuilder(String cacheName){
        return new CacheConfigBuilder(cacheName);
    }

    public String getCacheName() {
        return cacheName;
    }

    public boolean isEternal() {
        return isEternal;
    }

    public void setEternal(boolean eternal) {
        isEternal = eternal;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CacheConfig{");
        sb.append("cacheName='").append(cacheName).append('\'');
        sb.append(", isEternal=").append(isEternal);
        sb.append(", maxSize=").append(maxSize);
        sb.append(", timeToLiveSeconds=").append(timeToLiveSeconds);
        sb.append('}');
        return sb.toString();
    }

    public static class CacheConfigBuilder {
        private String cacheName;
        private boolean isEternal;
        private int maxSize;
        private int timeToLiveSeconds;

        public CacheConfigBuilder(String cacheName) {
            this.cacheName = cacheName;
            this.isEternal = false;
            this.maxSize = 1000;
            this.timeToLiveSeconds = 60 * 60;
        }

        public CacheConfigBuilder setIsEternal(boolean isEternal) {
            this.isEternal = isEternal;
            return this;
        }

        public CacheConfigBuilder setMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public CacheConfigBuilder setTimeToLiveSeconds(int timeToLiveSeconds) {
            this.timeToLiveSeconds = timeToLiveSeconds;
            return this;
        }

        public CacheConfig build(){
            return new CacheConfig(this);
        }
    }
}