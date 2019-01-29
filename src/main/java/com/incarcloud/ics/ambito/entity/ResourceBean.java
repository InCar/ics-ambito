package com.incarcloud.ics.ambito.entity;


import com.incarcloud.ics.ambito.jdbc.Table;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@Table(name = "t_sys_resource")
public class ResourceBean extends BaseBean{

    private static final long serialVersionUID = -7946151979932000051L;

    private String code;

    private String parentCode;

    private String parentCodes;

    private String resourceName;

    private Byte type;

    private Integer sort;

    private String url;

    private String icon;

    private Integer isDefault;

    private String remark;

    private Byte isDisplay;

    private List<ResourceBean> resourceBeans;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentCodes() {
        return parentCodes;
    }

    public void setParentCodes(String parentCodes) {
        this.parentCodes = parentCodes;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public void setIsDisplay(Byte isDisplay) {
        this.isDisplay = isDisplay;
    }

    public List<ResourceBean> getResourceBeans() {
        return resourceBeans;
    }

    public void setResourceBeans(List<ResourceBean> resourceBeans) {
        this.resourceBeans = resourceBeans;
    }

    public byte getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(byte isDisplay) {
        this.isDisplay = isDisplay;
    }
}
