package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.entity.SysOrgUserBean;
import com.incarcloud.ics.ambito.service.SysOrgUserService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26
 */
@RequestMapping(value = "/ics/orgUser")
@RestController
public class SysOrgUserController {

    @Autowired
    private SysOrgUserService sysOrgUserService;

    @PostMapping(value = "/save")
    @Transactional
    public JsonMessage save(@RequestBody List<SysOrgUserBean> sysOrgUserBeans) {
        for(SysOrgUserBean sysOrgUserBean : sysOrgUserBeans) {
            List<SysOrgUserBean> existing = sysOrgUserService.query(Condition.and(
                    new NumberCondition("orgId", sysOrgUserBean.getOrgId()),
                    new NumberCondition("userId", sysOrgUserBean.getUserId())
            ));
            if(CollectionUtils.isNotEmpty(existing)){
                continue;
            }
            if (sysOrgUserBean.getId() == null) {
                sysOrgUserService.save(sysOrgUserBean);
            } else {
                sysOrgUserService.update(sysOrgUserBean);
            }
        }
        return JsonMessage.success();
    }


    /**
     * 批量删除用户组织的绑定关系
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/delete")
    public JsonMessage delete(@RequestParam Long[] ids) {
        sysOrgUserService.deleteBatch(ids);
        return JsonMessage.success();
    }
}
