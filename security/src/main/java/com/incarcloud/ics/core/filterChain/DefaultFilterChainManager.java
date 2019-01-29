package com.incarcloud.ics.core.filterChain;

import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.filter.DefaultFilter;
import com.incarcloud.ics.core.filter.Nameable;
import com.incarcloud.ics.core.utils.CollectionUtils;
import com.incarcloud.ics.core.utils.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class DefaultFilterChainManager implements FilterChainManager {

    private Map<String,NamedFilterChain> filterChains = new LinkedHashMap<>();
    private Map<String, Filter> filters = new LinkedHashMap<>(); //pool of filters available for creating chains
    private FilterConfig filterConfig;


    public DefaultFilterChainManager() {
        this.addDefaultFilters(false);
    }

    public DefaultFilterChainManager(FilterConfig filterConfig) {
        setFilterConfig(filterConfig);
        addDefaultFilters(true);
    }

    public Map<String, Filter> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Filter> filters) {
        this.filters = filters;
    }

    public Filter getFilter(String name) {
        return (Filter)this.filters.get(name);
    }

    public void addFilter(String name, Filter filter) {
        this.addFilter(name, filter, false);
    }

    public void addFilter(String name, Filter filter, boolean init) {
        this.addFilter(name, filter, init, true);
    }

    protected void addFilter(String name, Filter filter, boolean init, boolean overwrite) {
        Filter existing = this.getFilter(name);
        if (existing == null || overwrite) {
            if (filter instanceof Nameable) {
                ((Nameable)filter).setName(name);
            }
            if (init) {
                this.initFilter(filter);
            }
            this.filters.put(name, filter);
        }

    }

    protected void initFilter(Filter filter) {
        FilterConfig filterConfig = this.getFilterConfig();
        if (filterConfig == null) {
            throw new IllegalStateException("FilterConfig attribute has not been set.  This must occur before filter initialization can occur.");
        } else {
            try {
                filter.init(filterConfig);
            } catch (ServletException ex) {
                throw new SecurityException(ex);
            }
        }
    }

    @Override
    public Set<String> getChainNames() {
        return filterChains.keySet();
    }

    @Override
    public NamedFilterChain getChain(String chainName) {
        return filterChains.get(chainName);
    }

    @Override
    public boolean hasChains() {
        return CollectionUtils.isNotEmpty(filterChains);
    }

    @Override
    public void createChain(String chainName, String chainDefinition) {
        if (StringUtils.isBlank(chainName)) {
            throw new NullPointerException("chainName cannot be null or empty.");
        } else if (StringUtils.isBlank(chainDefinition)) {
            throw new NullPointerException("chainDefinition cannot be null or empty.");
        } else {
            String[] filterTokens = this.splitChainDefinition(chainDefinition);
            String[] var4 = filterTokens;
            int var5 = filterTokens.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String token = var4[var6];
                String[] nameConfigPair = this.toNameConfigPair(token);
                this.addToChain(chainName, nameConfigPair[0], nameConfigPair[1]);
            }

        }
    }

    protected String[] toNameConfigPair(String token) {
        String name;
        try {
            String[] pair = token.split("\\[", 2);
            name = StringUtils.clean(pair[0]);
            if (name == null) {
                throw new IllegalArgumentException("Filter name not found for filter chain definition token: " + token);
            } else {
                String config = null;
                if (pair.length == 2) {
                    config = StringUtils.clean(pair[1]);
                    config = config.substring(0, config.length() - 1);
                    config = StringUtils.clean(config);
                    if (config != null && config.startsWith("\"") && config.endsWith("\"")) {
                        String stripped = config.substring(1, config.length() - 1);
                        stripped = StringUtils.clean(stripped);
                        if (stripped != null && stripped.indexOf(34) == -1) {
                            config = stripped;
                        }
                    }
                }

                return new String[]{name, config};
            }
        } catch (Exception ex) {
            name = "Unable to parse filter chain definition token: " + token;
            throw new SecurityException(name, ex);
        }
    }


    protected String[] splitChainDefinition(String chainDefinition) {
        return StringUtils.split(chainDefinition, ',', '[', ']', true, true);
    }

    @Override
    public void addToChain(String chainName, String filterName) {
        addToChain(chainName, filterName, null);
    }

    @Override
    public void addToChain(String chainName, String filterName, String chainSpecificFilterConfig) {
        if (!StringUtils.isNotBlank(chainName)) {
            throw new IllegalArgumentException("chainName cannot be null or empty.");
        }
        Filter filter = getFilter(filterName);
        if (filter == null) {
            throw new IllegalArgumentException("There is no filter with name '" + filterName +
                    "' to apply to chain [" + chainName + "] in the pool of available Filters.  Ensure a " +
                    "filter with that name/path has first been registered with the addFilter method(s).");
        }

        applyChainConfig(chainName, filter, chainSpecificFilterConfig);

        NamedFilterChain chain = ensureChain(chainName);
        chain.add(filter);
    }

    protected NamedFilterChain ensureChain(String chainName) {
        NamedFilterChain chain = this.getChain(chainName);
        if (chain == null) {
            chain = new SimpleNamedFilterChain(chainName);
            this.filterChains.put(chainName, chain);
        }
        return chain;
    }


    protected void applyChainConfig(String chainName, Filter filter, String chainSpecificFilterConfig) {

        if (filter instanceof PathConfigProcessor) {
            ((PathConfigProcessor)filter).processPathConfig(chainName, chainSpecificFilterConfig);
        } else if (StringUtils.isNotBlank(chainSpecificFilterConfig)) {
            String msg = "chainSpecificFilterConfig was specified, but the underlying Filter instance is not an 'instanceof' " + PathConfigProcessor.class.getName() + ".  This is required if the filter is to accept " + "chain-specific configuration.";
            throw new IllegalArgumentException(msg);
        }

    }

    @Override
    public FilterChain proxy(FilterChain original, String chainName) {
        NamedFilterChain filterChain = getChain(chainName);
        if(filterChain == null){
            throw new IllegalArgumentException("There is no configured chain under the name/key [" + chainName + "].");
        }
        return filterChain.proxy(original);
    }

    protected void addDefaultFilters(boolean init) {
        DefaultFilter[] var2 = DefaultFilter.values();
        int var3 = var2.length;
        for(int var4 = 0; var4 < var3; ++var4) {
            DefaultFilter defaultFilter = var2[var4];
            this.addFilter(defaultFilter.name(), defaultFilter.newInstance(), init, false);
        }
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
