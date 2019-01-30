
package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.exception.BeanInitializationException;
import com.incarcloud.ics.core.filterChain.DefaultFilterChainManager;
import com.incarcloud.ics.core.filterChain.FilterChainManager;
import com.incarcloud.ics.core.filterChain.FilterChainResolver;
import com.incarcloud.ics.core.filterChain.PathMatchingFilterChainResolver;
import com.incarcloud.ics.core.security.SecurityManager;
import com.incarcloud.ics.core.utils.CollectionUtils;

import javax.servlet.Filter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class FilterFactoryBean {
    private static final transient Logger log = Logger.getLogger(FilterFactoryBean.class.getName());
    private SecurityManager securityManager;
    private Map<String, Filter> filters = new LinkedHashMap<>();
    private Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
    private String loginUrl;
    private String successUrl;
    private String unauthorizedUrl;
    private AbstractAmbitoFilter instance;

    public FilterFactoryBean() {
    }

    public SecurityManager getSecurityManager() {
        return this.securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public String getLoginUrl() {
        return this.loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return this.successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getUnauthorizedUrl() {
        return this.unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public Map<String, Filter> getFilters() {
        return this.filters;
    }

    public void setFilters(Map<String, Filter> filters) {
        this.filters = filters;
    }

    public Map<String, String> getFilterChainDefinitionMap() {
        return this.filterChainDefinitionMap;
    }

    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        this.filterChainDefinitionMap = filterChainDefinitionMap;
    }

//    public void setFilterChainDefinitions(String definitions) {
//        Ini ini = new Ini();
//        ini.load(definitions);
//        Section section = ini.getSection("urls");
//        if (CollectionUtils.isEmpty(section)) {
//            section = ini.getSection("");
//        }
//
//        this.setFilterChainDefinitionMap(section);
//    }

    public Object getObject() {
        if (this.instance == null) {
            this.instance = this.createInstance();
        }
        return this.instance;
    }

//    public Class getObjectType() {
//        return FilterFactoryBean.SpringShiroFilter.class;
//    }
//
//    public boolean isSingleton() {
//        return true;
//    }

    @SuppressWarnings("unchecked")
    protected FilterChainManager createFilterChainManager() {
        DefaultFilterChainManager manager = new DefaultFilterChainManager();
//        Map<String, Filter> defaultFilters = manager.getFilters();
//        Iterator var3 = defaultFilters.values().iterator();

//        while(var3.hasNext()) {
//            Filter filter = (Filter)var3.next();
////            this.applyGlobalPropertiesIfNecessary(filter);
//        }

        Map<String, Filter> filters = this.getFilters();
        String name;
        Filter filter;
        if (!CollectionUtils.isEmpty(filters)) {
            for(Iterator iterator = filters.entrySet().iterator(); iterator.hasNext(); manager.addFilter(name, filter, false)) {
                Entry<String, Filter> entry = (Entry)iterator.next();
                name = entry.getKey();
                filter = entry.getValue();
//                this.applyGlobalPropertiesIfNecessary(filter);
                if (filter instanceof Nameable) {
                    ((Nameable)filter).setName(name);
                }
            }
        }

        Map<String, String> chains = this.getFilterChainDefinitionMap();
        if (!CollectionUtils.isEmpty(chains)) {
            Iterator iterator = chains.entrySet().iterator();
            while(iterator.hasNext()) {
                Entry<String, String> entry = (Entry)iterator.next();
                String url = entry.getKey();
                String chainDefinition = entry.getValue();
                manager.createChain(url, chainDefinition);
            }
        }

        return manager;
    }

    protected AbstractAmbitoFilter createInstance()  {
        log.fine("Creating Filter instance.");
        SecurityManager securityManager = this.getSecurityManager();
        String msg;
        if (securityManager == null) {
            msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        } else {
            FilterChainManager manager = this.createFilterChainManager();
            PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
            chainResolver.setFilterChainManager(manager);
            return new FilterFactoryBean.SpringAmbitoFilter(securityManager, chainResolver);
        }
    }

//    private void applyLoginUrlIfNecessary(Filter filter) {
//        String loginUrl = this.getLoginUrl();
//        if (StringUtils.isNotBlank(loginUrl) && filter instanceof AccessControllerFilter) {
//            AccessControllerFilter acFilter = (AccessControllerFilter)filter;
//            String existingLoginUrl = acFilter.getLoginUrl();
//            if ("/login.jsp".equals(existingLoginUrl)) {
//                acFilter.setLoginUrl(loginUrl);
//            }
//        }
//
//    }

//    private void applySuccessUrlIfNecessary(Filter filter) {
//        String successUrl = this.getSuccessUrl();
//        if (StringUtils.isNotBlank(successUrl) && filter instanceof AuthenticationFilter) {
//            AuthenticationFilter authcFilter = (AuthenticationFilter)filter;
//            String existingSuccessUrl = authcFilter.getSuccessUrl();
//            if ("/".equals(existingSuccessUrl)) {
//                authcFilter.setSuccessUrl(successUrl);
//            }
//        }
//
//    }
//
//    private void applyUnauthorizedUrlIfNecessary(Filter filter) {
//        String unauthorizedUrl = this.getUnauthorizedUrl();
//        if (StringUtils.isNotBlank(unauthorizedUrl) && filter instanceof AuthorizationFilter) {
//            AuthorizationFilter authzFilter = (AuthorizationFilter)filter;
//            String existingUnauthorizedUrl = authzFilter.getUnauthorizedUrl();
//            if (existingUnauthorizedUrl == null) {
//                authzFilter.setUnauthorizedUrl(unauthorizedUrl);
//            }
//        }
//    }
//
//    private void applyGlobalPropertiesIfNecessary(Filter filter) {
//        this.applyLoginUrlIfNecessary(filter);
//        this.applySuccessUrlIfNecessary(filter);
//        this.applyUnauthorizedUrlIfNecessary(filter);
//    }

//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        if (bean instanceof Filter) {
//            log.fine("Found filter chain candidate filter '"+beanName+"'");
//            Filter filter = (Filter)bean;
//            this.applyGlobalPropertiesIfNecessary(filter);
//            this.getFilters().put(beanName, filter);
//        } else {
//            log.fine("Ignoring non-Filter bean "+beanName);
//        }
//        return bean;
//    }
//
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }

    private static final class SpringAmbitoFilter extends AbstractAmbitoFilter {
        protected SpringAmbitoFilter(SecurityManager securityManager, FilterChainResolver resolver) {
            if (securityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            } else {
                this.setSecurityManager(securityManager);
                if (resolver != null) {
                    this.setFilterChainResolver(resolver);
                }
            }
        }
    }
}
