package com.incarcloud.ics.core.filterChain;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import java.util.ArrayList;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/25
 */
public class SimpleNamedFilterChain extends ArrayList<Filter> implements NamedFilterChain {
    private static final long serialVersionUID = -6688217292403613404L;
    private String name;

    public SimpleNamedFilterChain(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FilterChain proxy(FilterChain filterChain) {
        return new ProxiedFilterChain(filterChain, this.iterator());
    }
}
