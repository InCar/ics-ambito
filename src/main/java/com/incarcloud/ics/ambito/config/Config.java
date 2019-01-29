package com.incarcloud.ics.ambito.config;

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
    private CacheConfig authorizingCacheConfig = CacheConfig.getDefaultAuthorizeCacheConfig();
    private CacheConfig authenticateCacheConfig = CacheConfig.getDefaultAuthenticateCacheConfig();
    private CacheConfig accessCacheConfig = CacheConfig.getDefaultAccessInfoCacheConfig();

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

    public CacheConfig getAuthorizingCacheConfig() {
        return authorizingCacheConfig;
    }

    public void setAuthorizingCacheConfig(CacheConfig authorizingCacheConfig) {
        this.authorizingCacheConfig = authorizingCacheConfig;
    }

    public CacheConfig getAuthenticateCacheConfig() {
        return authenticateCacheConfig;
    }

    public void setAuthenticateCacheConfig(CacheConfig authenticateCacheConfig) {
        this.authenticateCacheConfig = authenticateCacheConfig;
    }

    public CacheConfig getAccessCacheConfig() {
        return accessCacheConfig;
    }

    public void setAccessCacheConfig(CacheConfig accessCacheConfig) {
        this.accessCacheConfig = accessCacheConfig;
    }


    /**
     * 缓存配置
     */
    public static class CacheConfig{
        private String cacheName;
        private boolean isEternal;
        private long maxSize;
        private long timeToLiveSeconds;

        public static CacheConfig getDefault(String cacheName){
            return new CacheConfig(cacheName, false, 1000, 60 * 60);
        }

        public static CacheConfig getDefaultAuthorizeCacheConfig(){
            return new CacheConfig("authorizeCache", false, 1000, 60 * 60);
        }

        public static CacheConfig getDefaultAccessInfoCacheConfig(){
            return new CacheConfig("accessInfoCache", false, 1000, 60 * 60);
        }

        public static CacheConfig getDefaultAuthenticateCacheConfig(){
            return new CacheConfig("authenticateCache", false, 1000, 60 * 60);
        }

        public CacheConfig(String cacheName, boolean isEternal, long maxSize, long timeToLiveSeconds) {
            this.cacheName = cacheName;
            this.isEternal = isEternal;
            this.maxSize = maxSize;
            this.timeToLiveSeconds = timeToLiveSeconds;
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
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Config{");
        sb.append("deleteOrgRecursion=").append(deleteOrgRecursion);
        sb.append(", authorizingCacheConfig=").append(authorizingCacheConfig);
        sb.append(", authenticateCacheConfig=").append(authenticateCacheConfig);
        sb.append(", accessCacheConfig=").append(accessCacheConfig);
        sb.append('}');
        return sb.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(new Config());
//    }


}
