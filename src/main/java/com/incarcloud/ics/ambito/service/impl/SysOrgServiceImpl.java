package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.SysOrgBean;
import com.incarcloud.ics.ambito.entity.SysOrgUserBean;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.SysOrgService;
import com.incarcloud.ics.ambito.service.SysOrgUserService;
import com.incarcloud.ics.ambito.service.SysOrgVehicleService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.config.Config;
import com.incarcloud.ics.exception.AmbitoException;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;
import com.incarcloud.ics.pojo.ErrorDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26 14:28
 */
@Service
public class SysOrgServiceImpl  extends BaseServiceImpl<SysOrgBean> implements SysOrgService {

    private Logger logger = LoggerFactory.getLogger(SysOrgServiceImpl.class);

    @Autowired
    private SysOrgUserService sysOrgUserService;

    @Autowired
    private SysOrgVehicleService sysOrgVehicleService;

    @Autowired
    private UserService sysUserService;

    @Override
    public int save(SysOrgBean sysOrgBean){
        //顶级组织的parentCode设为"0"
        if(sysOrgBean.getParentCode() == null){
            sysOrgBean.setParentCode(SysOrgBean.ROOT_CODE);
            sysOrgBean.setParentCodes(SysOrgBean.ROOT_CODE+SysOrgBean.CODE_SPERATOR);
            sysOrgBean.setLevel((byte)0);
            sysOrgBean.setIsLeaf((byte)1);
            return super.save(sysOrgBean);
        }

        //设置parentCodes/level/ifLeaf
        SysOrgBean parent = getOrgByCode(sysOrgBean.getParentCode());
        if(parent == null){
            throw ErrorDefine.PARENT_ORG_NOT_EXISTS.toAmbitoException();
        }

        sysOrgBean.setParentCodes(buildParentsCode(parent));
        sysOrgBean.setLevel((byte)(parent.getLevel()+1));
        sysOrgBean.setIsLeaf((byte)1);
        //父节点设置为非叶子节点
        parent.setIsLeaf((byte)0);
        super.update(parent);
        return super.save(sysOrgBean);
    }

    private String buildParentsCode(SysOrgBean parent){
        return parent.getParentCodes()
                + parent.getOrgCode()
                + SysOrgBean.CODE_SPERATOR;
    }


    private List<SysOrgBean> getOrgsByCode(String code) {
        return super.query(new StringCondition("orgCode", code, StringCondition.Handler.EQUAL));
    }

    private SysOrgBean getOrgByCode(String code) {
        List<SysOrgBean> orgsByCode = getOrgsByCode(code);
        if(orgsByCode.size() > 0){
            return orgsByCode.get(0);
        }else {
            return null;
        }
    }

//    private void checkBeforeSave(SysOrgBean sysOrgBean){
//        //判断名称是否重复
//        List<SysOrgBean> nameExists = getOrgsByCode(sysOrgBean.getOrgCode());
//        if(CollectionUtils.isNotEmpty(nameExists)){
//            throw ErrorDefine.REPEATED_NAME.toAmbitoException();
//        }
//
//        //判断code是否重复
//        List<SysOrgBean> codeExists = getOrgsByCode(sysOrgBean.getOrgCode());
//        if(CollectionUtils.isNotEmpty(codeExists)){
//            throw ErrorDefine.REPEATED_CODE.toAmbitoException();
//        }
//    }



    @Override
    @Transactional
    public int delete(Serializable id) throws AmbitoException {
        SysOrgBean sysOrgBean = get(id);

        //如果为顶级组织且下面有子组织，则不允许删除
        if(SysOrgBean.ROOT_CODE.equals(sysOrgBean.getOrgCode())){
            List<SysOrgBean> childrenOrgs = getChildrenOrgs(sysOrgBean);
            if(childrenOrgs.size() > 0){
                throw ErrorDefine.UNDELETABLE.toAmbitoException();
            }
        }
        //允许递归删除
        if(Config.getConfig().isDeleteOrgRecursion()){
            //叶子节点，直接删除,并解除绑定关系
            if(isLeaf(sysOrgBean)){
                this.delete(sysOrgBean);
            }else {
                //非叶子节点，删除自身，并递归删除子节点
                this.deleteRecursion(sysOrgBean);
            }
        //不允许递归删除
        }else {
            //非叶子节点，直接删除
            if(isLeaf(sysOrgBean)){
               this.delete(sysOrgBean);
            }else {
                //删除
                this.delete(sysOrgBean);
                //将子节点提升
                moveUpChildren(sysOrgBean);
            }
        }
        return 0;
    }


    private void delete(SysOrgBean sysOrgBean){
        super.delete(sysOrgBean.getId());
        this.deleteRelationShip(sysOrgBean.getId());
    }

    private void deleteRecursion(SysOrgBean sysOrgBean){
        super.delete(sysOrgBean.getId());
        this.deleteRelationShip(sysOrgBean.getId());
        List<SysOrgBean> children = getChildrenOrgs(sysOrgBean);
        children.forEach(this::delete);
    }

    private void moveUpChildren(SysOrgBean sysOrgBean){
        SysOrgBean parent = getOrgByCode(sysOrgBean.getParentCode());
        List<SysOrgBean> children = getChildrenOrgs(sysOrgBean);
        moveUp(parent, children);
    }


    private void moveUp(SysOrgBean parent, List<SysOrgBean> children){
        children.forEach(child -> {
            //父组织不存在，说明被删除的组织是顶级组织
            if(parent == null){
                child.setParentCode(SysOrgBean.ROOT_CODE);
                child.setParentCodes(SysOrgBean.ROOT_CODE + SysOrgBean.CODE_SPERATOR);
            }else {
                child.setParentCode(parent.getOrgCode());
                child.setParentCodes(buildParentsCode(parent));
            }
            try {
                super.save(child);
            }catch (Exception e){
                throw ErrorDefine.SAVING_EXCEPTION.toAmbitoException();
            }
        });
    }


    @Override
    public List<SysOrgBean> getChildrenOrgs(SysOrgBean sysOrgBean){
        return this.query(
                new StringCondition("parentCodes",
                        sysOrgBean.getParentCodes() + sysOrgBean.getOrgCode() + SysOrgBean.CODE_SPERATOR,
                        StringCondition.Handler.RIGHT_LIKE)
        );
    }




    private boolean isLeaf(SysOrgBean sysOrgBean){
        return sysOrgBean.getIsLeaf() == 1;
    }


    /**
     * 删除绑定关系
     * @param id
     */
    private void deleteRelationShip(Serializable id){
        sysOrgVehicleService.delete(new NumberCondition("orgId", id, NumberCondition.Handler.EQUAL));
        sysOrgUserService.delete(new NumberCondition("orgId", id, NumberCondition.Handler.EQUAL));
    }


    @Override
    public Set<SysOrgBean> getUserManageOrgs(Long userId) {
        List<SysOrgUserBean> orgUserBeans = sysOrgUserService.query(new NumberCondition("userId", userId, NumberCondition.Handler.EQUAL));
        List<Long> orgIds = orgUserBeans.stream().map(SysOrgUserBean::getOrgId).collect(Collectors.toList());
        List<SysOrgBean> orgBeans = this.query(new NumberCondition("id", orgIds, NumberCondition.Handler.IN));
        Set<SysOrgBean> collect = new HashSet<>();
        for(SysOrgBean sysOrgBean : orgBeans){
            List<SysOrgBean> childrenOrgs = getChildrenOrgs(sysOrgBean);
            collect.addAll(new HashSet<>(childrenOrgs));
        }
        return collect;
    }

    @Override
    public Set<SysOrgBean> getUserManageOrgs(String username) {
        List<UserBean> users = sysUserService.query(new StringCondition("username", username, StringCondition.Handler.EQUAL));
        if(users.isEmpty()){
            return Collections.emptySet();
        }
        return getUserManageOrgs(users.get(0).getId());
    }


    @Override
    public Set<String> getUserManageOrgCodes(String username) {
        Set<SysOrgBean> users = getUserManageOrgs(username);
        return users.stream().map(SysOrgBean::getOrgCode).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getUserBelongOrgCodes(String username) {
        List<UserBean> users = sysUserService.query(new StringCondition("username", username, StringCondition.Handler.EQUAL));
        if(users.isEmpty()){
            return Collections.emptySet();
        }
        List<SysOrgUserBean> orgUserBeans = sysOrgUserService.query(new NumberCondition("userId", users.get(0).getId(), NumberCondition.Handler.EQUAL));
        List<Long> orgIds = orgUserBeans.stream().map(SysOrgUserBean::getOrgId).collect(Collectors.toList());
        List<SysOrgBean> orgBeans = this.query(new NumberCondition("id", orgIds, NumberCondition.Handler.IN));;
        return orgBeans.stream().map(SysOrgBean::getOrgCode).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllOrgCodes() {
        List<SysOrgBean> sysOrgBeans = query(new NumberCondition("status", 0, NumberCondition.Handler.EQUAL));
        return sysOrgBeans.stream().map(SysOrgBean::getOrgCode).collect(Collectors.toSet());
    }

    @Override
    public Object getList(Long id, String orgName, String parentId, Integer pageNum, Integer pageSize) {
        Condition cond = Condition.and(
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL),
                new StringCondition("orgName", orgName, StringCondition.Handler.ALL_LIKE),
                new StringCondition("parentId", parentId, StringCondition.Handler.EQUAL)
        );
        if (pageNum == null || pageSize == null) {
            return this.query(cond);
        } else {
            return this.queryPage(new Page(pageNum, pageSize), cond);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysOrgBean saveOrUpdate(SysOrgBean sysOrgBean) {
        if(sysOrgBean.getId() == null){
            //组织code不能包含分隔符
            if(sysOrgBean.getOrgCode().trim().contains(SysOrgBean.CODE_SPERATOR)){
                throw ErrorDefine.INVALID_ORG_CODE.toAmbitoException();
            }
            this.save(sysOrgBean);
        }else {
            preModifyHandle(sysOrgBean);
            this.update(sysOrgBean);
        }
        List<SysOrgBean> sysOrgBeans = this.query(new StringCondition("orgCode",sysOrgBean.getOrgCode()));
        return sysOrgBeans.get(0);
    }

    private void preModifyHandle(SysOrgBean sysOrgBean) {
        SysOrgBean oldBean = this.get(sysOrgBean.getId());
        if(!oldBean.getOrgCode().equals(sysOrgBean.getOrgCode())){
            updateChildrenCode(oldBean, sysOrgBean);
        }
    }


    private void updateChildrenCode(SysOrgBean oldBean, SysOrgBean newBean){
        List<SysOrgBean> childrenOrgs = getChildrenOrgs(oldBean);
        childrenOrgs.forEach(e->{
            //直接子级
            if(e.getParentCode().equals(oldBean.getOrgCode())){
                e.setParentCode(newBean.getOrgCode());
                e.setParentCodes(newBean.getParentCodes() + SysOrgBean.CODE_SPERATOR + newBean.getOrgCode());
            }else {
                String codeRegex = SysOrgBean.CODE_SPERATOR + oldBean.getOrgCode() + SysOrgBean.CODE_SPERATOR;
                String codeReplace = SysOrgBean.CODE_SPERATOR + newBean.getOrgCode() + SysOrgBean.CODE_SPERATOR;
                int index = e.getParentCodes().indexOf(codeRegex);
                if(index != -1){
                    e.setParentCodes(e.getParentCodes().replaceFirst(codeRegex, codeReplace));
                }else {
                    codeRegex = oldBean.getOrgCode() + SysOrgBean.CODE_SPERATOR;
                    codeReplace = newBean.getOrgCode() + SysOrgBean.CODE_SPERATOR;
                    index = e.getParentCodes().indexOf(codeRegex);
                    if(index != -1){
                        e.setParentCodes(e.getParentCodes().replaceFirst(codeRegex, codeReplace));
                    }else {
                        logger.warn(oldBean.getParentCodes() + " does not contains code " + oldBean.getOrgCode());
                    }
                }
            }
        });
    }



    @Override
    public SysOrgBean getOrgTree(Long id) {
        SysOrgBean sysOrgBean = this.get(id);
        if(sysOrgBean == null){
            return null;
        }
        Map<String, SysOrgBean> sysOrgBeanMap = new HashMap<>();

        sysOrgBeanMap.put(sysOrgBean.getOrgCode(), sysOrgBean);
        List<SysOrgBean> sysOrgBeans = this.query();
        if(CollectionUtils.isNotEmpty(sysOrgBeans)){
            sysOrgBeanMap.putAll(sysOrgBeans.stream().collect(Collectors.toMap(SysOrgBean::getOrgCode, e->e)));
            for(SysOrgBean org : sysOrgBeans){
                SysOrgBean parent = sysOrgBeanMap.get(org.getParentCode());
                if(parent != null){
                    parent.getChildren().add(org);
                }
            }
        }
        return sysOrgBeanMap.get(sysOrgBean.getOrgCode());
    }

}
