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

}
