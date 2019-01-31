package com.incarcloud.ics.core.filter;

import javax.servlet.FilterConfig;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/11
 */
public abstract class NameableFilter extends AbstractFilter implements Nameable {

    private String name;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    protected String getName(){
        if (this.name == null) {
            FilterConfig config = getFilterConfig();
            if (config != null) {
                this.name = config.getFilterName();
            }
        }
        return this.name;
    }

    protected StringBuilder toStringBuilder() {
        String name = getName();
        if (name == null) {
            return super.toStringBuilder();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            return sb;
        }
    }
}
