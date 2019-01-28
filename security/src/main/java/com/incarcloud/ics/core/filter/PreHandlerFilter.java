package com.incarcloud.ics.core.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/25
 */
public abstract class PreHandlerFilter extends OncePerRequestFilter {
    private Logger log = Logger.getLogger(PreHandlerFilter.class.getName());

    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Exception e = null;
        try {

            boolean continueChain = preHandle(request, response);
            if (log.isLoggable(Level.FINE)) {
                log.fine("Invoked preHandle method.  Continuing chain?: [" + continueChain + "]");
            }
            if (continueChain) {
                executeChain(request, response, chain);
            }

        } catch (Exception ex) {
            e = ex;
        }finally {
            cleanup(request, response, e);
        }
    }

    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
        Exception exception = existing;

        try {
            this.afterCompletion(request, response, exception);
        } catch (Exception var6) {
            if (existing == null) {
                exception = var6;
            }
        }

        if (exception != null) {
            if (exception instanceof ServletException) {
                throw (ServletException)exception;
            } else if (exception instanceof IOException) {
                throw (IOException)exception;
            } else {
                throw new ServletException(exception);
            }
        }
    }

    protected void afterCompletion(ServletRequest request, ServletResponse response, Exception exception){}

    protected void executeChain(ServletRequest request, ServletResponse response, FilterChain chain) throws Exception {
        chain.doFilter(request, response);
    }

    protected abstract boolean preHandle(ServletRequest request, ServletResponse response) throws Exception;
}
