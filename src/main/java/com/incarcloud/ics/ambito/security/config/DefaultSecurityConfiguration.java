package com.incarcloud.ics.ambito.security.config;

import com.incarcloud.ics.ambito.security.DatabaseSessionDAO;
import com.incarcloud.ics.ambito.security.JdbcRealm;
import com.incarcloud.ics.config.Config;
import com.incarcloud.ics.core.filter.AbstractAmbitoFilter;
import com.incarcloud.ics.core.filter.FilterFactoryBean;
import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.security.DefaultSecurityManager;
import com.incarcloud.ics.core.security.SecurityManager;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.session.DefaultWebSessionManager;
import com.incarcloud.ics.core.session.SessionDAO;
import com.incarcloud.ics.core.session.SessionManager;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;


/**
 * @description 默认的权限配置类，如果想开启权限验证，需要继承该类并加上spring的@Configuration注解，对于自定义的类，可通过覆盖相应的方法
 * 进行实例化，比如自定义了HbaseRealm，可以通过覆盖realm()方法实现，方法头可以不加@Bean,spring容器会自动搜索父类的realm方法有无此注解
 *     protected Realm realm(){
 *         return new HbaseRealm();
 *     }
 * 也可以不继承此类，将权限验证所需的类自己注册到容器即可
 * @author ThomasChan
 * @version 1.0
 * @date 2019/1/9
 */
public abstract class DefaultSecurityConfiguration {

    public DefaultSecurityConfiguration() {
    }

    private Config config = Config.getConfig();

    @Bean
    protected SecurityManager securityManager(){
        DefaultSecurityManager securityManager = new DefaultSecurityManager(realm());
        securityManager.setSessionManager(sessionManager());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    protected Realm realm(){
        return new JdbcRealm();
    }

    @Bean
    public FilterFactoryBean filterFactoryBean(){
        FilterFactoryBean filterFactoryBean = new FilterFactoryBean();
        LinkedHashMap<String,String> filterChainDefinitions = new LinkedHashMap<>();
        loadFilterChainDefinitions(filterChainDefinitions);
        filterChainDefinitions.put("/ics/user/login", "anon");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        filterFactoryBean.setSecurityManager(securityManager());
        return filterFactoryBean;
    }

    protected void loadFilterChainDefinitions(LinkedHashMap<String,String> filterChainDefinitions){
    }

    @Bean
    protected AbstractAmbitoFilter filter(){
        return (AbstractAmbitoFilter) filterFactoryBean().getObject();
    }

    @Bean
    protected SessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDao(sessionDAO());
        return defaultWebSessionManager;
    }

    @Bean
    protected SessionDAO sessionDAO(){
        return new DatabaseSessionDAO();
    }

}

