
package com.incarcloud.ics.core.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {

    public static final String EMPTY_STRING = "";

    /**
     * 判断是否为空字符串""或null
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     *
     * 判断不是空字符串""且不为null
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     *
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断是否空字符串""或null或空白字符如"  "
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     *
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串不是空字符串""或空白字符串如" "或null
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }


    public static String clean(String in) {
        String out = in;

        if (in != null) {
            out = in.trim();
            if (out.equals(EMPTY_STRING)) {
                out = null;
            }
        }

        return out;
    }



    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    @SuppressWarnings({"unchecked"})
    public static String[] tokenizeToStringArray(
            String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        if (str == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List tokens = new ArrayList();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    public static String[] toStringArray(Collection collection) {
        if (collection == null) {
            return null;
        }
        return (String[]) collection.toArray(new String[collection.size()]);
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str == null || prefix == null) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }


    public static String[] split(String line) {
        return split(line, ',');
    }

    public static String[] split(String line, char delimiter) {
        return split(line, delimiter, '"');
    }

    public static String[] split(String line, char delimiter, char quoteChar) {
        return split(line, delimiter, quoteChar, quoteChar);
    }

    public static String[] split(String line, char delimiter, char beginQuoteChar, char endQuoteChar) {
        return split(line, delimiter, beginQuoteChar, endQuoteChar, false, true);
    }


    @SuppressWarnings("unchecked")
    public static String[] split(String aLine, char delimiter, char beginQuoteChar, char endQuoteChar, boolean retainQuotes, boolean trimTokens) {
        String line = clean(aLine);
        if (line == null) {
            return null;
        } else {
            List<String> tokens = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            boolean inQuotes = false;

            for(int i = 0; i < line.length(); ++i) {
                char c = line.charAt(i);
                if (c == beginQuoteChar) {
                    if (inQuotes && line.length() > i + 1 && line.charAt(i + 1) == beginQuoteChar) {
                        sb.append(line.charAt(i + 1));
                        ++i;
                    } else {
                        inQuotes = !inQuotes;
                        if (retainQuotes) {
                            sb.append(c);
                        }
                    }
                } else if (c == endQuoteChar) {
                    inQuotes = !inQuotes;
                    if (retainQuotes) {
                        sb.append(c);
                    }
                } else if (c == delimiter && !inQuotes) {
                    String s = sb.toString();
                    if (trimTokens) {
                        s = s.trim();
                    }

                    tokens.add(s);
                    sb = new StringBuilder();
                } else {
                    sb.append(c);
                }
            }

            String s = sb.toString();
            if (trimTokens) {
                s = s.trim();
            }

            tokens.add(s);
            return (String[])tokens.toArray(new String[tokens.size()]);
        }
    }

}
