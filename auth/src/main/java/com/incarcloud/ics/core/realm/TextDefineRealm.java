package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.org.Organization;
import com.incarcloud.ics.core.subject.Account;
import com.incarcloud.ics.core.utils.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public class TextDefineRealm extends SimpleAccountRealm {


    private String roleDefinitions;
    private String userDefinitions;
    private String orgDefinitions;

    public TextDefineRealm() {
    }

    public TextDefineRealm(String userDefinitions) {
        this(userDefinitions, null, null);
    }

    public TextDefineRealm(String userDefinitions, String roleDefinitions, String orgDefinitions) {
        this.roleDefinitions = roleDefinitions;
        this.userDefinitions = userDefinitions;
        this.orgDefinitions = orgDefinitions;
        processDefinitionResolve();
    }

    protected void processDefinitionResolve() {
        processUserDefinitionsResolve();
        processOrgDefinitionsResolve();
    }


    private void processOrgDefinitionsResolve() {
        if(StringUtils.isNotBlank(orgDefinitions)){
            Map<String,List<Organization>> orgMap = DefinitionParser.parseUserOrganizations(orgDefinitions);
            orgMap.keySet().forEach(userId-> {
                Account account = this.getAccountInfo(userId);
                account.setOrganizations(orgMap.get(userId));
            });
        }
    }

    private void processUserDefinitionsResolve() {
        if(StringUtils.isNotBlank(userDefinitions)){
            List<Account> accounts = DefinitionParser.parseUsers(userDefinitions);
            this.addAccounts(accounts);
        }
    }


    public String getRoleDefinitions() {
        return roleDefinitions;
    }

    public void setRoleDefinitions(String roleDefinitions) {
        this.roleDefinitions = roleDefinitions;
    }

    public String getUserDefinitions() {
        return userDefinitions;
    }

    public void setUserDefinitions(String userDefinitions) {
        this.userDefinitions = userDefinitions;
    }

    public String getOrgDefinitions() {
        return orgDefinitions;
    }

    public void setOrgDefinitions(String orgDefinitions) {
        this.orgDefinitions = orgDefinitions;
    }

}
