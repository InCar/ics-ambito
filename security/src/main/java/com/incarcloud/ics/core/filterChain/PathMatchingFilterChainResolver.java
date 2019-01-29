//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.incarcloud.ics.core.filterChain;


import com.incarcloud.ics.core.filter.AntPathMatcher;
import com.incarcloud.ics.core.filter.PatternMatcher;
import com.incarcloud.ics.core.utils.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Iterator;
import java.util.logging.Logger;

public class PathMatchingFilterChainResolver implements FilterChainResolver {
    private static final transient Logger log = Logger.getLogger(PathMatchingFilterChainResolver.class.getName());
    private FilterChainManager filterChainManager;
    private PatternMatcher pathMatcher = new AntPathMatcher();

    public PathMatchingFilterChainResolver() {
        this.filterChainManager = new DefaultFilterChainManager();
    }

    public PathMatchingFilterChainResolver(FilterConfig filterConfig) {
        this.filterChainManager = new DefaultFilterChainManager(filterConfig);
    }

    public PatternMatcher getPathMatcher() {
        return this.pathMatcher;
    }

    public void setPathMatcher(PatternMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public FilterChainManager getFilterChainManager() {
        return this.filterChainManager;
    }

    public void setFilterChainManager(FilterChainManager filterChainManager) {
        this.filterChainManager = filterChainManager;
    }

    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = this.getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        } else {
            String requestURI = this.getPathWithinApplication(request);
            Iterator iterator = filterChainManager.getChainNames().iterator();

            String pathPattern;
            do {
                if (!iterator.hasNext()) {
                    return null;
                }
                pathPattern = (String)iterator.next();
            } while(!this.pathMatches(pathPattern, requestURI));
            return filterChainManager.proxy(originalChain, pathPattern);
        }
    }

    protected boolean pathMatches(String pattern, String path) {
        PatternMatcher pathMatcher = this.getPathMatcher();
        return pathMatcher.matches(pattern, path);
    }

    protected String getPathWithinApplication(ServletRequest request) {
        return WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
    }
}
