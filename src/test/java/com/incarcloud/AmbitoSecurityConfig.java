package com.incarcloud;

import com.incarcloud.ics.ambito.security.JdbcRealm;
import com.incarcloud.ics.ambito.security.config.AopEnabledSecurityConfiguration;
import com.incarcloud.ics.core.realm.Realm;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;


/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
@Configuration
public class AmbitoSecurityConfig extends AopEnabledSecurityConfiguration {

    @Override
    public Realm realm() {
        return new JdbcRealm();
    }

    /**
     * 该方法用于自定义过滤器链，定义方式和shiro相同
     * @param filterChainDefinitions
     */
    @Override
    protected void loadFilterChainDefinitions(LinkedHashMap<String, String> filterChainDefinitions) {
        filterChainDefinitions.put("/ics/**", "anon");
    }
}
