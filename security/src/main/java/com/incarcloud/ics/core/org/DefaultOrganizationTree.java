package com.incarcloud.ics.core.org;

import com.incarcloud.ics.core.exception.SecurityException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public class DefaultOrganizationTree implements OrganizationTree, Serializable {

    private static final long serialVersionUID = 3610198628251100423L;
    private static Logger logger = Logger.getLogger(DefaultOrganizationTree.class.getName());

    private Map<String,Organization> orgMap;

    protected DefaultOrganizationTree() {
        orgMap = new TreeMap<>();
    }

    protected DefaultOrganizationTree(Map<String,Organization> organizationMap) {
        this();
        this.putAll(organizationMap);
    }

    protected void putAll(Map<String,Organization> organizationMap){
        orgMap.putAll(organizationMap);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return (List<Organization>)orgMap.values();
    }

    @Override
    public List<Organization> getSiblingOf(Organization org) {
        Organization parent = org.getParent();
        return orgMap.values().stream().filter(e->e.isChildOf(parent) && !e.getCode().equals(org.getCode())).collect(Collectors.toList());
    }

    @Override
    public List<Organization> getChildrenOrganizationsOf(Organization org) {
        return orgMap.values().stream().filter(e->e.isChildOf(org)).collect(Collectors.toList());
    }

    @Override
    public List<Organization> getManageOrganizationsOf(Organization org) {
        List<Organization> childrenOrganizations = getChildrenOrganizationsOf(org);
        childrenOrganizations.add(org);
        return childrenOrganizations;
    }

    @Override
    public Organization getChief() {
        List<Organization> collect = orgMap.values().stream().filter(e -> e.getParent() != null).collect(Collectors.toList());
        Organization chief = null;
        if(collect.isEmpty()){
            logger.fine("There is no chief of this organization tree");
        }else if(collect.size() > 1){
            throw SecurityException.getException("More than one chief have been found!");
        }else {
            chief = collect.get(0);
        }
        return chief;
    }
}
