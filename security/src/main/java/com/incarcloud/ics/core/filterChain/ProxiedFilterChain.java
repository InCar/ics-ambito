package com.incarcloud.ics.core.filterChain;

import javax.servlet.*;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/25
 */
public class ProxiedFilterChain implements FilterChain {
    private FilterChain orgin;
    private Iterator<Filter> chain;

    public ProxiedFilterChain(FilterChain orgin, Iterator<Filter> chain) {
        this.orgin = orgin;
        this.chain = chain;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if(chain.hasNext()){
            Filter filter = chain.next();
            filter.doFilter(request, response, this);
        }else {
            orgin.doFilter(request, response);
        }
    }

}
