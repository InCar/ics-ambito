package com.incarcloud.ics.ambito.config;

import com.incarcloud.ics.ambito.condition.Condition;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/5
 */
public class Config {


    public static Config getDefaultConfig(){
        return new Config();
    }

    public Config(boolean deleteOrgRecursion) {
        this.deleteOrgRecursion = deleteOrgRecursion;
    }

    public Config() {
        this(false);
    }

    /**
     * 是否递归删除组织，true表示递归删除所有组织及其子组织，false表示只删除本组织
     */
    private boolean deleteOrgRecursion;


    public boolean isDeleteOrgRecursion() {
        return deleteOrgRecursion;
    }

    public void setDeleteOrgRecursion(boolean deleteOrgRecursion) {
        this.deleteOrgRecursion = deleteOrgRecursion;
    }
}
