
package com.incarcloud.ics.core.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AnonymousFilter extends PathMatcherFilter {
    public AnonymousFilter() {
    }

    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        return true;
    }
}
