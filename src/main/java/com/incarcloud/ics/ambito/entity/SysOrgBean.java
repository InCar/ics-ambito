package com.incarcloud.ics.ambito.entity;


import com.incarcloud.ics.ambito.jdbc.Table;
import com.incarcloud.ics.ambito.jdbc.Unique;
import com.incarcloud.ics.core.access.DataFilter;
import com.incarcloud.ics.pojo.ErrorDefine;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author GuoKun
 * @Despriction 机构表
 * @Date 2018-12-26 11:46:02
 */
@Table(name = "t_sys_org")
@DataFilter(tableName = "t_sys_org")
public class SysOrgBean extends ExtendableBean{

    private static final long serialVersionUID = -5029982119320932787L;

    /**
     * 默认顺序
     */
    public static final int DEFAULT_SORT = 50;

    /**
     * 根组织编号
     */
    public static final String ROOT_CODE = "ics";

    /**
     * 机构编码分隔符
     */
    public static final String CODE_SPERATOR = ",";

    // 机构名称
    @Unique(message = ErrorDefine.REPEATED_NAME)
    private String orgName;

    // 机构编码
    @Unique(message = ErrorDefine.REPEATED_CODE)
    private String orgCode;

    //上级code
    private String parentCode;

    //所有直系上级code，以逗号拼接
    private String parentCodes;

    // 0 系统默认不可操作，1 可操作
    private byte operateType;

    //等级
    private byte level;

    // 机构全称
    private String fullName;

    // 负责人
    private String leader;

    // 电话
    private String phone;

    // 联系地址
    private String address;

    // 邮政编码
    private String zipCode;

    // 邮箱
    private String email;

    //状态 0=关闭，1=正常
    private byte status;

    //备注
    private String remark;

    // 顺序
    private int sort = DEFAULT_SORT;

    //是否为叶子节点
    private byte isLeaf;

    private List<SysOrgBean> children = new ArrayList<>();

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<SysOrgBean> getChildren() {
        return children;
    }

    public void setChildren(List<SysOrgBean> children) {
        this.children = children;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public byte getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(byte isLeaf) {
        this.isLeaf = isLeaf;
    }

    public byte getOperateType() {
        return operateType;
    }

    public void setOperateType(byte operateType) {
        this.operateType = operateType;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }
}
