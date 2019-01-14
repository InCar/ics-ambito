package com.incarcloud;

import com.incarcloud.ics.ambito.auth.JdbcRealm;
import com.incarcloud.ics.core.ambito.DefaultSecurityManager;
import com.incarcloud.ics.core.ambito.SecurityManager;
import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.session.SessionManager;
import com.incarcloud.ics.core.utils.SecurityUtils;
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
        SecurityManager securityManager = new DefaultSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean(name = "jdbcRealm")
    public Realm myRealm(){
        Realm realm = new JdbcRealm();
        return realm;
    }


}
