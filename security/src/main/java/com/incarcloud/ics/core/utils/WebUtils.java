package com.incarcloud.ics.core.utils;


import com.incarcloud.ics.core.session.ServletPairSource;
import com.incarcloud.ics.core.session.SessionContext;
import com.incarcloud.ics.core.session.SessionKey;
import com.incarcloud.ics.core.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class WebUtils {
    public static final String INCLUDE_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.include.context_path";
    public static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";
    public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";

    public static ServletRequest getRequest(SessionKey sessionKey){
        ServletRequest servletRequest = null;
        if(sessionKey instanceof ServletPairSource){
            servletRequest = ((ServletPairSource)sessionKey).getServletRequest();
        }
        return servletRequest;
    }

    public static ServletResponse getResponse(SessionKey sessionKey){
        ServletResponse servletResponse = null;
        if(sessionKey instanceof ServletPairSource){
            servletResponse = ((ServletPairSource)sessionKey).getServletResponse();
        }
        return servletResponse;
    }

    public static HttpServletRequest getHttpRequest(SessionContext sessionContext) {
        if(sessionContext != null){
            ServletRequest servletRequest = sessionContext.getServletRequest();
            if(servletRequest instanceof HttpServletRequest){
                return (HttpServletRequest) servletRequest;
            }
        }
        return null;
    }

    public static HttpServletRequest getHttpRequest(Subject subject) {
        if(subject != null){
            ServletRequest servletRequest = subject.getServletRequest();
            if(servletRequest instanceof HttpServletRequest){
                return (HttpServletRequest) servletRequest;
            }
        }
        return null;
    }

    public static HttpServletResponse getHttpResponse(Subject subject) {
        if(subject != null){
            ServletResponse servletResponse = subject.getServletResponse();
            if(servletResponse instanceof HttpServletResponse){
                return (HttpServletResponse) servletResponse;
            }
        }
        return null;
    }


    public static HttpServletResponse getHttpResponse(SessionContext sessionContext) {
        if(sessionContext != null){
            ServletResponse servletResponse = sessionContext.getServletResponse();
            if(servletResponse instanceof HttpServletResponse){
                return (HttpServletResponse) servletResponse;
            }
        }
        return null;
    }


    public static HttpServletRequest getHttpRequest(SessionKey sessionKey) {
        ServletRequest request = getRequest(sessionKey);
        if(request instanceof HttpServletRequest){
            return (HttpServletRequest) request;
        }
        return null;
    }

    public static HttpServletResponse getHttpResponse(SessionKey sessionKey) {
        ServletResponse response = getResponse(sessionKey);
        if(response instanceof HttpServletResponse){
            return (HttpServletResponse) response;
        }
        return null;
    }


    public static HttpServletResponse toHttp(ServletResponse response) {
        if(response instanceof HttpServletResponse){
            return (HttpServletResponse)response;
        }
        return null;
    }


    public static String getContextPath(HttpServletRequest request) {
        String contextPath = (String) request.getAttribute(INCLUDE_CONTEXT_PATH_ATTRIBUTE);
        if (contextPath == null) {
            contextPath = request.getContextPath();
        }
        contextPath = normalize(decodeRequestString(request, contextPath));
        if ("/".equals(contextPath)) {
            // the normalize method will return a "/" and includes on Jetty, will also be a "/".
            contextPath = "";
        }
        return contextPath;
    }

    private static String decodeAndCleanUriString(HttpServletRequest request, String uri) {
        uri = decodeRequestString(request, uri);
        int semicolonIndex = uri.indexOf(';');
        return (semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri);
    }

    public static String decodeRequestString(HttpServletRequest request, String source) {
        String enc = determineEncoding(request);
        try {
            return URLDecoder.decode(source, enc);
        } catch (UnsupportedEncodingException ex) {
            return URLDecoder.decode(source);
        }
    }

    protected static String determineEncoding(HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null) {
            enc = DEFAULT_CHARACTER_ENCODING;
        }
        return enc;
    }

    public static String getRequestUri(HttpServletRequest request) {
        String uri = (String) request.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE);
        if (uri == null) {
            uri = request.getRequestURI();
        }
        return normalize(decodeAndCleanUriString(request, uri));
    }

    public static String normalize(String path) {
        return normalize(path, true);
    }


    private static String normalize(String path, boolean replaceBackSlash) {

        if (path == null)
            return null;

        // Create a place for the normalized path
        String normalized = path;

        if (replaceBackSlash && normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');

        if (normalized.equals("/."))
            return "/";

        // Add a leading "/" if necessary
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null);  // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // Return the normalized path that we have completed
        return (normalized);

    }

    public static String getPathWithinApplication(HttpServletRequest request) {
        String contextPath = getContextPath(request);
        String requestUri = getRequestUri(request);
        if (StringUtils.startsWithIgnoreCase(requestUri, contextPath)) {
            // Normal case: URI contains context path.
            String path = requestUri.substring(contextPath.length());
            return (StringUtils.isNotBlank(path) ? path : "/");
        } else {
            // Special case: rather unusual.
            return requestUri;
        }
    }

    public static HttpServletRequest toHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }


    public static String getCleanParam(ServletRequest request, String paramName) {
        return StringUtils.clean(request.getParameter(paramName));
    }


//    public static void saveRequest(ServletRequest request) {
//        Subject subject = SecurityUtils.getSubject();
//        Session session = subject.getSession();
//        HttpServletRequest httpRequest = toHttp(request);
//        SavedRequest savedRequest = new SavedRequest(httpRequest);
//        session.setAttribute("ambitoSavedRequest", savedRequest);
//    }
//



//    public static void issueRedirect(ServletRequest request, ServletResponse response, String url) throws IOException {
//        issueRedirect(request, response, url, (Map)null, true, true);
//    }
//
//    public static void issueRedirect(ServletRequest request, ServletResponse response, String url, Map queryParams) throws IOException {
//        issueRedirect(request, response, url, queryParams, true, true);
//    }
//
//    public static void issueRedirect(ServletRequest request, ServletResponse response, String url, Map queryParams, boolean contextRelative) throws IOException {
//        issueRedirect(request, response, url, queryParams, contextRelative, true);
//    }
//
//    public static void issueRedirect(ServletRequest request, ServletResponse response, String url, Map queryParams, boolean contextRelative, boolean http10Compatible) throws IOException {
//        RedirectView view = new RedirectView(url, contextRelative, http10Compatible);
//        view.renderMergedOutputModel(queryParams, toHttp(request), toHttp(response));
//    }
//
//    public static class SavedRequest implements Serializable {
//        private String method;
//        private String queryString;
//        private String requestURI;
//
//        public SavedRequest(HttpServletRequest request) {
//            this.method = request.getMethod();
//            this.queryString = request.getQueryString();
//            this.requestURI = request.getRequestURI();
//        }
//
//        public String getMethod() {
//            return this.method;
//        }
//
//        public String getQueryString() {
//            return this.queryString;
//        }
//
//        public String getRequestURI() {
//            return this.requestURI;
//        }
//
//        public String getRequestUrl() {
//            StringBuilder requestUrl = new StringBuilder(this.getRequestURI());
//            if (this.getQueryString() != null) {
//                requestUrl.append("?").append(this.getQueryString());
//            }
//
//            return requestUrl.toString();
//        }
//    }


}
