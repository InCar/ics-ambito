package com.incarcloud.ics.ambito.config;

import com.incarcloud.ics.core.authc.MD5PasswordMatcher;

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
    private boolean deleteOrgRecursion;

    /**
     * 密码处理方式
     */
    private String credentialMatherClass;

    public static Config getDefaultConfig(){
        return new Config();
    }

    public Config(boolean deleteOrgRecursion, String credentialMatherClass) {
        this.deleteOrgRecursion = deleteOrgRecursion;
        this.credentialMatherClass = credentialMatherClass;
    }

    public Config() {
        this(false, MD5PasswordMatcher.class.getName());
    }

    public boolean isDeleteOrgRecursion() {
        return deleteOrgRecursion;
    }

    public void setDeleteOrgRecursion(boolean deleteOrgRecursion) {
        this.deleteOrgRecursion = deleteOrgRecursion;
    }

    public String getCredentialMatherClass() {
        return credentialMatherClass;
    }

    public void setCredentialMatherClass(String credentialMatherClass) {
        this.credentialMatherClass = credentialMatherClass;
    }
}
