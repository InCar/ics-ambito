package com.incarcloud.ics.ambito.security;

import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.entity.VehicleArchivesBean;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.SysOrgService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.ambito.utils.StringUtils;
import com.incarcloud.ics.core.access.*;
import com.incarcloud.ics.core.authc.AuthenticateInfo;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.authc.SimpleAuthenticateInfo;
import com.incarcloud.ics.core.authz.AuthorizeInfo;
import com.incarcloud.ics.core.exception.AccessorException;
import com.incarcloud.ics.core.exception.AccountNotExistsException;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.realm.AccessRealm;
import com.incarcloud.ics.core.role.SimpleAuthorizeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
@Component
public class JdbcRealm extends AccessRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected List<Class<?>> needAccessControlClass = Arrays.asList(VehicleArchivesBean.class);

    protected void addNeedAccessControlClass(Class<?> clzz){
        needAccessControlClass.add(clzz);
    }

    @Override
    protected AuthenticateInfo doGetAuthenticateInfo(AuthenticateToken authenticateToken) throws AuthenticationException {
        List<UserBean> users = userService.query(new StringCondition("username", authenticateToken.getPrincipal(), StringCondition.Handler.EQUAL));
        if(users.isEmpty()){
            throw new AccountNotExistsException("No account with username "+ authenticateToken.getPrincipal());
        }
        UserBean userBean = users.get(0);
        return new SimpleAuthenticateInfo(userBean.getUsername(), userBean.getPassword(), userBean.getSalt());
    }

    @Override
    protected AuthorizeInfo doGetAuthorizeInfo(Principal principal) {
        //获取用户的角色信息
        List<UserBean> users = userService.query(new StringCondition("username", principal.getUserIdentity(), StringCondition.Handler.EQUAL));
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        List<RoleBean> roleBeans = roleService.getRolesOfUser(users.get(0).getId());
        SimpleAuthorizeInfo authorizeInfo = new SimpleAuthorizeInfo();
        authorizeInfo.setRoles(roleBeans.stream().map(RoleBean::getRoleCode).collect(Collectors.toSet()));
        List<ResourceBean> resourceBeans = new ArrayList<>();
        for(RoleBean roleBean : roleBeans){
            List<ResourceBean> privilegeOfRole = resourceService.getPrivilegeOfRole(roleBean.getId());
            resourceBeans.addAll(privilegeOfRole);
        }
        authorizeInfo.setStringPrivileges(resourceBeans.stream().map(ResourceBean::getCode).collect(Collectors.toSet()));
        return authorizeInfo;
    }

    @Override
    protected AccessInfo doGetAccessInfo(Principal principal) {
        Collection<String> orgCodes = getOrgCodeByStrategy(principal);
        //获取用户拥有数据权限的所有实体类的实例id集合
        Map<String, Collection<Serializable>> classCollectionMap = new HashMap<>();
        this.needAccessControlClass.forEach(aClass -> {
            classCollectionMap.putAll(extractAccessibleEntityId(orgCodes, aClass));
        });
        return new SimpleAccessInfo(orgCodes, classCollectionMap);
    }


    private Collection<String> getOrgCodeByStrategy(Principal principal){
        switch (this.accessStrategy){
            case BELONG:{
                return sysOrgService.getUserBelongOrgCodes(principal.getUserIdentity());
            }
            case MANAGE: {
                return sysOrgService.getUserManageOrgCodes(principal.getUserIdentity());
            }
            case ALL:{
                return sysOrgService.getAllOrgCodes();
            }
            default:{
                return Collections.emptySet();
            }
        }
    }



    protected Map<String, Collection<Serializable>> extractAccessibleEntityId(Collection<String> orgCodes, Class<?> aEntityClass) {
        super.assertAccessControlSupported(aEntityClass);
        //获取进行过滤的字段
        FilterColumn filterColumn = aEntityClass.getAnnotation(FilterColumn.class);
        FilterType type = filterColumn.type();
        try {
            aEntityClass.getDeclaredField(type.getColumnName());
        } catch (NoSuchFieldException e) {
            throw new AccessorException("The filter column "+type.getColumnName()+" is not exists in class "+aEntityClass);
        }
        String tableName = getTableName(aEntityClass);
        //获取过滤字段
        String filterColumnName = StringUtils.camelToUnderline(type.getColumnName());

        //获取能管理的数据id集合
        List<Object> dataIds = getDataIds(orgCodes, tableName, filterColumnName);
        Map<String, Collection<Serializable>> classCollectionMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(dataIds)){
            classCollectionMap.put(aEntityClass.getName(), dataIds.stream().map(e->(Serializable)e).collect(Collectors.toSet()));
        }
        return classCollectionMap;
    }

    private List<Object> getDataIds(Collection<String> orgCodes, String tableName, String filterColumnName) {
        String str = orgCodes.stream().map(e -> "?").collect(Collectors.joining("'"));
        String sqlBuilder = "select id from " + tableName + " where " + filterColumnName + " in " + "(" + str + ")";
        return jdbcTemplate.query(sqlBuilder, (rs, rowNum) -> rs.getObject(1), orgCodes.toArray());
    }

    private String getTableName(Class<?> aEntityClass) {
        //获取表名
        String tableName = null;
        Table table = aEntityClass.getAnnotation(Table.class);
        if(table != null){
            tableName = table.name();
        }else {
            AccessTable accessTable = aEntityClass.getAnnotation(AccessTable.class);
            tableName = accessTable.name();
        }
        return tableName;
    }

}
