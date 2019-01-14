package com.incarcloud.ics.core.org;


import java.util.List;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public interface OrganizationTree{
    List<Organization> getAllOrganizations();
    List<Organization> getSiblingOf(Organization org);
    List<Organization> getChildrenOrganizationsOf(Organization org);
    List<Organization> getManageOrganizationsOf(Organization org);
    Organization getChief();
}
