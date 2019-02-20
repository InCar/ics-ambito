package com.incarcloud.ics.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.stream.Collectors;


public class PropertiesLoader {

    private static final String DEFAULT_PROPERTIES_FILE_NAME = "ambito-default.properties";
    private static final String CUSTOM_PROPERTIES_FILE_NAME = "ambito.properties";

    private String properiesName = "";

    public PropertiesLoader() {
    }

    public PropertiesLoader(String fileName) {
        this.properiesName = fileName;
    }

    public String readProperty(String key) {
        String value = "";
        InputStream is = null;
        try {
            is = PropertiesLoader.class.getClassLoader().getResourceAsStream(
                    properiesName);
            Properties p = new Properties();
            p.load(is);
            value = p.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public Properties getProperties() {
        Properties p = new Properties();
        InputStream is = null;
        try {
            is = PropertiesLoader.class.getClassLoader().getResourceAsStream(
                    properiesName);
            if(is != null)
                p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    static Config loadConfigFromPropertiesFiles(){
        //加载默认配置
        Properties properties = new PropertiesLoader(DEFAULT_PROPERTIES_FILE_NAME).getProperties();
        //加载自定义配置
        Properties custom = new PropertiesLoader(CUSTOM_PROPERTIES_FILE_NAME).getProperties();
        //自定义配置覆盖默认配置
        properties.putAll(custom);
        //properties转化为config
        return Config.getBuilder()
                .setCacheConfigMap(Arrays.stream(new CacheConfig[]{
                        CacheConfig.getBuilder("authenticateCache")
                                .setIsEternal(Boolean.parseBoolean(properties.getProperty("authenticateConfig.isEternal")))
                                .setMaxSize(Integer.parseInt(properties.getProperty("authenticateConfig.maxSize")))
                                .setTimeToLiveSeconds(Integer.parseInt(properties.getProperty("authenticateConfig.timeToLiveSeconds")))
                                .build(),
                        CacheConfig.getBuilder("authorizingCache")
                                .setIsEternal(Boolean.parseBoolean(properties.getProperty("authorizingConfig.isEternal")))
                                .setMaxSize(Integer.parseInt(properties.getProperty("authorizingConfig.maxSize")))
                                .setTimeToLiveSeconds(Integer.parseInt(properties.getProperty("authorizingConfig.timeToLiveSeconds")))
                                .build(),
                        CacheConfig.getBuilder("accessCache")
                                .setIsEternal(Boolean.parseBoolean(properties.getProperty("accessConfig.isEternal")))
                                .setMaxSize(Integer.parseInt(properties.getProperty("accessConfig.maxSize")))
                                .setTimeToLiveSeconds(Integer.parseInt(properties.getProperty("accessConfig.timeToLiveSeconds")))
                                .build()
                }).collect(Collectors.toMap(CacheConfig::getCacheName, e->e)))
                .setLogConfig(Config.getDefaultLogConfig()
                        .withEnableConsoleLog(Boolean.parseBoolean(properties.getProperty("logConfig.enableConsoleLog")))
                        .withEnableFileLog(Boolean.parseBoolean(properties.getProperty("logConfig.enableFileLog")))
                        .withEnableLog(Boolean.parseBoolean(properties.getProperty("logConfig.enableLog")))
                        .withFileLogDir(properties.getProperty("logConfig.fileLogDir"))
                        .withLevel(parseLevel(properties)))
                .setDeleteOrgRecursion(Boolean.parseBoolean(properties.getProperty("deleteOrgRecursion")))
                .setOrganizationType(parseOrganizationType(properties))
                .build();
    }

    private static Config.OrganizationType parseOrganizationType(Properties properties){
        if(properties == null) {
            return Config.OrganizationType.STANDARD;
        }
        String type = properties.getProperty("organizationType");
        if(type.equals("SIMPLE")){
            return Config.OrganizationType.SIMPLE;
        }else {
            return Config.OrganizationType.STANDARD;
        }
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
}