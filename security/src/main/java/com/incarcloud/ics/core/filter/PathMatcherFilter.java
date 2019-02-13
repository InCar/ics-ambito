package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.filterChain.PathConfigProcessor;
import com.incarcloud.ics.core.utils.StringUtils;
import com.incarcloud.ics.core.utils.WebUtils;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;


public abstract class PathMatcherFilter extends PreHandlerFilter implements PathConfigProcessor {

    private Logger logger = LoggerFactory.getLogger(PathMatcherFilter.class);

    private PatternMatcher pathMatcher = new AntPathMatcher();
    protected Map<String, Object> appliedPaths = new LinkedHashMap<String, Object>();


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        if (this.appliedPaths == null || this.appliedPaths.isEmpty()) {
            return true;
        }

        for (String path : this.appliedPaths.keySet()) {
            // If the path does match, then pass on to the subclass implementation for specific checks
            //(first match 'wins'):
            if (pathsMatch(path, request)) {
                logger.debug("Current requestURI matches pattern '{"+path+"}'.  Determining filter chain execution...");
                Object config = this.appliedPaths.get(path);
                return isFilterChainContinued(request, response, path, config);
            }
        }

        //no path matched, allow the request to go through:
        return true;
    }

    private boolean isFilterChainContinued(ServletRequest request, ServletResponse response,
                                           String path, Object pathConfig) throws Exception {

        if (isEnabled(request, response, path, pathConfig)) { //isEnabled check added in 1.2

            //The filter is enabled for this specific request, so delegate to subclass implementations
            //so they can decide if the request should continue through the chain or not:
            return onPreHandle(request, response, pathConfig);
        }

        //This filter is disabled for this specific request,
        //return 'true' immediately to indicate that the filter will not process the request
        //and let the request/response to continue through the filter chain:
        return true;
    }

    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return true;
    }


    protected String getPathWithinApplication(ServletRequest request) {
        return WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
    }

    protected boolean pathsMatch(String path, ServletRequest request) {
        String requestURI = getPathWithinApplication(request);
        logger.debug("Attempting to match pattern '{"+path+"}' with current requestURI '{"+requestURI+"}'...");
        return pathsMatch(path, requestURI);
    }

    protected boolean pathsMatch(String pattern, String path) {
        return pathMatcher.matches(pattern, path);
    }

    @SuppressWarnings({"UnusedParameters"})
    protected boolean isEnabled(ServletRequest request, ServletResponse response, String path, Object mappedValue)
            throws Exception {
        return isEnabled(request, response);
    }

    public Filter processPathConfig(String path, String config) {
        String[] values = null;
        if (config != null) {
            values = StringUtils.split(config);
        }
        this.appliedPaths.put(path, values);
        return this;
    }
}
