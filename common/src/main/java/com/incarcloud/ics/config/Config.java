package com.incarcloud.ics.config;


import com.incarcloud.skeleton.config.LogConfig;

import java.util.Properties;
import java.util.logging.Level;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/5
 */
public class Config {

    private static final String DEFAULT_PROPERTIES_FILE_NAME = "ambito-default.properties";
    private static final String CUSTOM_PROPERTIES_FILE_NAME = "ambito.properties";

    private static Config CONFIG;

    /**
     * 是否递归删除组织，true表示递归删除�?有组织及其子组织，false表示只删除本组织
     */
    private boolean deleteOrgRecursion;

    /**
     * 功能权限数据缓存配置
     */
    private CacheConfig authorizingCache;

    /**
     * 认证数据缓存配置
     */
    private CacheConfig authenticateCache;

    /**
     * 数据权限缓存配置
     */
    private CacheConfig accessCache;

    /**
     * 日志配置
     */
    private LogConfig logConfig;

    private Config(ConfigBuilder builder) {
        this.deleteOrgRecursion = builder.deleteOrgRecursion;
        this.authenticateCache = builder.authenticateCache;
        this.authorizingCache = builder.authorizingCache;
        this.accessCache = builder.accessCache;
        this.logConfig = builder.logConfig;
    }

    public static Config getConfig(){
        if(CONFIG == null){
            CONFIG = loadConfigFromConfigFiles();
        }
        return CONFIG;
    }


    public static ConfigBuilder getBuilder(){
        return new ConfigBuilder();
    }

    private static LogConfig getDefaultLogConfig() {
        return new LogConfig();
    }

    public static Config loadConfigFromConfigFiles(){
        //加载默认配置
        Properties properties = new PropertiesLoader(DEFAULT_PROPERTIES_FILE_NAME).getProperties();
        //加载自定义配置
        Properties custom = new PropertiesLoader(CUSTOM_PROPERTIES_FILE_NAME).getProperties();
        //自定义配置覆盖默认配置
        properties.putAll(custom);
        //properties转化为config
        return Config.getBuilder()
                .setAuthenticateCache(
                        CacheConfig.getBuilder("authenticateCache")
                                .setIsEternal(Boolean.parseBoolean(properties.getProperty("authenticateCache.isEternal","true")))
                                .setMaxSize(Integer.parseInt(properties.getProperty("authenticateCache.maxSize","1000")))
                                .setTimeToLiveSeconds(Integer.parseInt(properties.getProperty("authenticateCache.timeToLiveSeconds","3600")))
                                .build()

                )
                .setAuthorizingCache(
                        CacheConfig.getBuilder("authorizingCache")
                                .setIsEternal(Boolean.parseBoolean(properties.getProperty("authorizingCache.isEternal","true")))
                                .setMaxSize(Integer.parseInt(properties.getProperty("authorizingCache.maxSize","1000")))
                                .setTimeToLiveSeconds(Integer.parseInt(properties.getProperty("authorizingCache.timeToLiveSeconds", "3600")))
                                .build()
                )
                .setAccessCache(CacheConfig.getBuilder("accessCache")
                        .setIsEternal(Boolean.parseBoolean(properties.getProperty("accessCache.isEternal", "true")))
                        .setMaxSize(Integer.parseInt(properties.getProperty("accessCache.maxSize", "1000")))
                        .setTimeToLiveSeconds(Integer.parseInt(properties.getProperty("accessCache.timeToLiveSeconds", "3600")))
                        .build())
                .setLogConfig(getDefaultLogConfig()
                        .withEnableConsoleLog(Boolean.parseBoolean(properties.getProperty("logConfig.enableConsoleLog", "true")))
                        .withEnableFileLog(Boolean.parseBoolean(properties.getProperty("logConfig.enableFileLog", "true")))
                        .withEnableLog(Boolean.parseBoolean(properties.getProperty("logConfig.enableLog", "true")))
                        .withFileLogDir(properties.getProperty("logConfig.fileLogDir", "log"))
                        .withLevel(parseLevel(properties)))
                .setDeleteOrgRecursion(Boolean.parseBoolean(properties.getProperty("deleteOrgRecursion", "true")))
                .build();
    }

    private static Level parseLevel(Properties properties){
        if(properties == null){
            return Level.INFO;
        }
        String level = properties.getProperty("logConfig.level", "INFO");
        switch (level){
            case "TRACE":
                return Level.FINEST;
            case "DEBUG":
                return Level.FINE;
            case "INFO":
                return Level.INFO;
            case "WARN":
                return Level.WARNING;
            case "ERROR":
                return Level.SEVERE;
            case "FETAL":
                return Level.SEVERE;
            default:
                return Level.INFO;
        }
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

    public LogConfig getLogConfig() {
        return logConfig;
    }

    public void setLogConfig(LogConfig logConfig) {
        this.logConfig = logConfig;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Config{");
        sb.append("deleteOrgRecursion=").append(deleteOrgRecursion);
        sb.append(", authorizingCache=").append(authorizingCache);
        sb.append(", authenticateCache=").append(authenticateCache);
        sb.append(", accessCache=").append(accessCache);
        sb.append(", logConfig=").append(logConfig);
        sb.append('}');
        return sb.toString();
    }

    private static class ConfigBuilder{
        private boolean deleteOrgRecursion;
        private CacheConfig authorizingCache;
        private CacheConfig authenticateCache;
        private CacheConfig accessCache;
        private LogConfig logConfig;

        public ConfigBuilder() {
            this.deleteOrgRecursion = false;
            this.authenticateCache = CacheConfig.getDefaultConfigOfCache("authorizingCache");
            this.authorizingCache = CacheConfig.getDefaultConfigOfCache("authenticateCache");
            this.accessCache = CacheConfig.getDefaultConfigOfCache("accessCache");
            this.logConfig = Config.getDefaultLogConfig();
        }

        public ConfigBuilder setDeleteOrgRecursion(boolean deleteOrgRecursion) {
            this.deleteOrgRecursion = deleteOrgRecursion;
            return this;
        }

        public ConfigBuilder setAuthorizingCache(CacheConfig authorizingCache) {
            this.authorizingCache = authorizingCache;
            return this;
        }

        public ConfigBuilder setAuthenticateCache(CacheConfig authenticateCache) {
            this.authenticateCache = authenticateCache;
            return this;
        }

        public ConfigBuilder setAccessCache(CacheConfig accessCache) {
            this.accessCache = accessCache;
            return this;
        }

        public ConfigBuilder setLogConfig(LogConfig logConfig) {
            this.logConfig = logConfig;
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
        private long maxSize;
        private long timeToLiveSeconds;

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

        private static class CacheConfigBuilder {
            private String cacheName;
            private boolean isEternal;
            private long maxSize;
            private long timeToLiveSeconds;

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
