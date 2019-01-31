package com.incarcloud.ics.core.privilege;

import com.incarcloud.ics.core.utils.CollectionUtils;
import com.incarcloud.ics.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class WildcardPrivilege implements Privilege {

    protected static final String WILDCARD_TOKEN = "*";
    protected static final String PART_DIVIDER_TOKEN = ":";
    protected static final String SUBPART_DIVIDER_TOKEN = ",";
    protected static final boolean DEFAULT_CASE_SENSITIVE = false;
    private List<Set<String>> parts;

    protected WildcardPrivilege() {
    }

    public WildcardPrivilege(String wildcardString) {
        this(wildcardString, false);
    }

    public WildcardPrivilege(String wildcardString, boolean caseSensitive) {
        this.setParts(wildcardString, caseSensitive);
    }

    protected void setParts(String wildcardString) {
        this.setParts(wildcardString, false);
    }

    protected void setParts(String wildcardString, boolean caseSensitive) {
        wildcardString = StringUtils.clean(wildcardString);
        if (wildcardString != null && !wildcardString.isEmpty()) {
            if (!caseSensitive) {
                wildcardString = wildcardString.toLowerCase();
            }

            List<String> parts = CollectionUtils.asList(wildcardString.split(":"));
            this.parts = new ArrayList<>();
            Iterator var4 = parts.iterator();

            while(var4.hasNext()) {
                String part = (String)var4.next();
                Set<String> subparts = CollectionUtils.asSet(part.split(","));
                if (subparts.isEmpty()) {
                    throw new IllegalArgumentException("Wildcard string cannot contain parts with only dividers. Make sure permission strings are properly formatted.");
                }

                this.parts.add(subparts);
            }

            if (this.parts.isEmpty()) {
                throw new IllegalArgumentException("Wildcard string cannot contain only dividers. Make sure permission strings are properly formatted.");
            }
        } else {
            throw new IllegalArgumentException("Wildcard string cannot be null or empty. Make sure permission strings are properly formatted.");
        }
    }

    protected List<Set<String>> getParts() {
        return this.parts;
    }

    protected void setParts(List<Set<String>> parts) {
        this.parts = parts;
    }

    public boolean implies(Privilege p) {
        if (!(p instanceof WildcardPrivilege)) {
            return false;
        } else {
            WildcardPrivilege wp = (WildcardPrivilege)p;
            List<Set<String>> otherParts = wp.getParts();
            int i = 0;

            for(Iterator iterator = otherParts.iterator(); iterator.hasNext(); ++i) {
                @SuppressWarnings("unchecked")
                Set<String> otherPart = (Set<String>) iterator.next();
                if (this.getParts().size() - 1 < i) {
                    return true;
                }

                Set<String> part = this.getParts().get(i);
                if (!part.contains("*") && !part.containsAll(otherPart)) {
                    return false;
                }
            }

            while(i < this.getParts().size()) {
                Set<String> part = this.getParts().get(i);
                if (!part.contains("*")) {
                    return false;
                }

                ++i;
            }

            return true;
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Iterator iterator = this.parts.iterator();

        while(iterator.hasNext()) {
            @SuppressWarnings("unchecked")
            Set<String> part = (Set<String>)iterator.next();
            if (buffer.length() > 0) {
                buffer.append(":");
            }

            Iterator partIt = part.iterator();

            while(partIt.hasNext()) {
                buffer.append((String)partIt.next());
                if (partIt.hasNext()) {
                    buffer.append(",");
                }
            }
        }

        return buffer.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof WildcardPrivilege) {
            WildcardPrivilege wp = (WildcardPrivilege)o;
            return this.parts.equals(wp.parts);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.parts.hashCode();
    }


}
