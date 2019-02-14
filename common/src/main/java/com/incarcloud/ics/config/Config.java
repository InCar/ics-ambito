package com.incarcloud.ics.config;


import com.incarcloud.skeleton.config.LogConfig;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/5
 */
public class Config {


    private static Config CONFIG;

    /**
     * 是否递归删除组织，true表示递归删除�?有组织及其子组织，false表示只删除本组织
     */
    private boolean deleteOrgRecursion;

    /**
     * 缓存配置
     */
    private Map<String,CacheConfig> cacheConfigMap;

    /**
     * 日志配置
     */
    private LogConfig logConfig;

    /**
     * 组织模式
     */
    private OrganizationType organizationType;

    public enum OrganizationType { SIMPLE,  STANDARD }

    private Config(ConfigBuilder builder) {
        this.deleteOrgRecursion = builder.deleteOrgRecursion;
        this.logConfig = builder.logConfig;
        this.organizationType = builder.organizationType;
        this.cacheConfigMap = builder.cacheConfigMap;
    }

    public static Config getConfig(){
        if(CONFIG == null){
            CONFIG =  PropertiesLoader.loadConfigFromPropertiesFiles();
        }
        return CONFIG;
    }


    static ConfigBuilder getBuilder(){
        return new ConfigBuilder();
    }

    static LogConfig getDefaultLogConfig() {
        return new LogConfig();
    }

    public boolean isDeleteOrgRecursion() {
        return deleteOrgRecursion;
    }

    public CacheConfig getCacheConfig(String cacheName) {
        return cacheConfigMap.getOrDefault(cacheName, new CacheConfig(cacheName));
    }

    public LogConfig getLogConfig() {
        return logConfig;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Config{");
        sb.append("deleteOrgRecursion=").append(deleteOrgRecursion);
        sb.append(", cacheConfigMap=").append(cacheConfigMap);
        sb.append(", logConfig=").append(logConfig);
        sb.append(", organizationType=").append(organizationType);
        sb.append('}');
        return sb.toString();
    }

    public static class ConfigBuilder{
        private boolean deleteOrgRecursion;
        private LogConfig logConfig;
        private OrganizationType organizationType;
        private Map<String,CacheConfig> cacheConfigMap;

        public ConfigBuilder() {
            this.deleteOrgRecursion = false;
            this.logConfig = Config.getDefaultLogConfig();
            this.organizationType = OrganizationType.STANDARD;
            this.cacheConfigMap = Arrays.stream(
                    new CacheConfig[]{
                    CacheConfig.getDefaultConfigOfCache("authorizingCache"),
                    CacheConfig.getDefaultConfigOfCache("authenticateCache"),
                    CacheConfig.getDefaultConfigOfCache("accessCache")}
                    )
                    .collect(Collectors.toMap(CacheConfig::getCacheName, e->e));
        }

        public ConfigBuilder setDeleteOrgRecursion(boolean deleteOrgRecursion) {
            this.deleteOrgRecursion = deleteOrgRecursion;
            return this;
        }

        public ConfigBuilder setCacheConfigMap(Map<String, CacheConfig> cacheConfigMap) {
            this.cacheConfigMap = cacheConfigMap;
            return this;
        }

        public ConfigBuilder setLogConfig(LogConfig logConfig) {
            this.logConfig = logConfig;
            return this;
        }

        public ConfigBuilder setOrganizationType(OrganizationType organizationType) {
            this.organizationType = organizationType;
            return this;
        }

        public Config build(){
            return new Config(this);
        }
    }


    /**
     * 缓存配置
     */
    public static class CacheConfig{
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
}
