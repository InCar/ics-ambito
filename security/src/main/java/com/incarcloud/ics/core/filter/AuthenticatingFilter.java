//
//package com.incarcloud.ics.core.filter;
//
//
//import com.incarcloud.ics.core.authc.AuthenticateToken;
//import com.incarcloud.ics.core.authc.UsernamePasswordToken;
//import com.incarcloud.ics.core.exception.AuthenticationException;
//import com.incarcloud.ics.core.exception.UnauthenticatedException;
//import com.incarcloud.ics.core.subject.Subject;
//
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//
//public abstract class AuthenticatingFilter extends AuthenticationFilter {
//    public static final String PERMISSIVE = "permissive";
//
//    public AuthenticatingFilter() {
//    }
//
//    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//        AuthenticateToken token = this.createToken(request, response);
//        if (token == null) {
//            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
//            throw new IllegalStateException(msg);
//        } else {
//            try {
//                Subject subject = this.getSubject(request, response);
//                subject.login(token);
//                return this.onLoginSuccess(token, subject, request, response);
//            } catch (AuthenticationException var5) {
//                return this.onLoginFailure(token, var5, request, response);
//            }
//        }
//    }
//
//    protected abstract AuthenticateToken createToken(ServletRequest var1, ServletResponse var2) throws Exception;
//
//    protected AuthenticateToken createToken(String username, String password, ServletRequest request, ServletResponse response) {
//        boolean rememberMe = this.isRememberMe(request);
//        String host = this.getHost(request);
//        return this.createToken(username, password, rememberMe, host);
//    }
//
//    protected AuthenticateToken createToken(String username, String password, boolean rememberMe, String host) {
//        return new UsernamePasswordToken(username, password);
//    }
//
//    protected boolean onLoginSuccess(AuthenticateToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
//        return true;
//    }
//
//    protected boolean onLoginFailure(AuthenticateToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
//        return false;
//    }
//
//    protected String getHost(ServletRequest request) {
//        return request.getRemoteHost();
//    }
//
//    protected boolean isRememberMe(ServletRequest request) {
//        return false;
//    }
//
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        return super.isAccessAllowed(request, response, mappedValue) || !this.isLoginRequest(request, response) && this.isPermissive(mappedValue);
//    }
//
//    protected boolean isPermissive(Object mappedValue) {
//        if (mappedValue != null) {
//            String[] values = ((String[])mappedValue);
//            return Arrays.binarySearch(values, "permissive") >= 0;
//        } else {
//            return false;
//        }
//    }
//
//    protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
//        return this.pathsMatch(this.getLoginUrl(), request);
//    }
//
//    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
//        if (existing instanceof UnauthenticatedException || existing instanceof ServletException && existing.getCause() instanceof UnauthenticatedException) {
//            try {
//                this.onAccessDenied(request, response);
//                existing = null;
//            } catch (Exception var5) {
//                existing = var5;
//            }
//        }
//
//        super.cleanup(request, response, existing);
//    }
//}
