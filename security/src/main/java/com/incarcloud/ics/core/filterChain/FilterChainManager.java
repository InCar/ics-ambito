package com.incarcloud.ics.core.filterChain;

import javax.servlet.FilterChain;
import java.util.Set;


public interface FilterChainManager {
    Set<String> getChainNames();
    NamedFilterChain getChain(String chainName);
    boolean hasChains();
    void createChain(String chainName, String chainDefinition);
    void addToChain(String chainName, String filterName);
    void addToChain(String chainName, String filterName, String chainSpecificFilterConfig);
    FilterChain proxy(FilterChain original, String chainName);
}
