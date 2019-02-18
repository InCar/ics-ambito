package com.incarcloud.ics.ambito.entity;


import com.incarcloud.ics.ambito.jdbc.Table;
import com.incarcloud.ics.ambito.jdbc.Unique;
import com.incarcloud.ics.pojo.ErrorDefine;

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

    //父级id
    private Long parentId;

    //所有父级id，以/分隔
    private String parentIds;

    //资源名称
    @Unique(message = ErrorDefine.REPEATED_NAME)
    private String resourceName;

    //类型 menu=菜单，button=按钮
    private ResourceType type = ResourceType.menu;

    //排序s
    private Integer sort;

    //url
    private String url;

    //图标
    private String icon;

    //备注
    private String remark;

    //是否展示
    private Boolean isDisplay = Boolean.TRUE;

    //权限标识
    private String permission;

    //等级
    private byte level;

    private List<ResourceBean> resourceBeans;

    public enum ResourceType { menu, button }

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

    public List<ResourceBean> getResourceBeans() {
        return resourceBeans;
    }

    public void setResourceBeans(List<ResourceBean> resourceBeans) {
        this.resourceBeans = resourceBeans;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Boolean display) {
        isDisplay = display;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }
}
