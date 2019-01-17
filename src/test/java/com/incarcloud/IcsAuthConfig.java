package com.incarcloud;

import com.incarcloud.ics.ambito.security.DatabaseSessionDAO;
import com.incarcloud.ics.ambito.security.JdbcRealm;
import com.incarcloud.ics.core.filter.AmbitoFilter;
import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.security.DefaultSecurityManager;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.session.DefaultWebSessionManager;
import com.incarcloud.ics.core.session.SessionDAO;
import com.incarcloud.ics.core.session.SessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
@Configuration
public class IcsAuthConfig {


    @Bean
    public SessionManager securityManager(Realm realm){
        DefaultSecurityManager securityManager = new DefaultSecurityManager(realm);
        securityManager.setSessionManager(sessionManager());
        SecurityUtils.setSecurityManager(securityManager);

        return securityManager;
    }

    @Bean(name = "jdbcRealm")
    public Realm myRealm(){
        Realm realm = new JdbcRealm();
        return realm;
    }

    @Bean
    public AmbitoFilter filter(){
        return new AmbitoFilter();
    }


    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDao(sessionDAO());
        return defaultWebSessionManager;
    }

    @Bean
    public SessionDAO sessionDAO(){
        return new DatabaseSessionDAO();
    }
}
