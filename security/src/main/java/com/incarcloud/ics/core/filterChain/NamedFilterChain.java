package com.incarcloud.ics.core.filterChain;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/25
 */
public interface NamedFilterChain  extends List<Filter> {
    String getName();
    FilterChain proxy(FilterChain filterChain);
}
