package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.filterChain.FilterChainResolver;
import com.incarcloud.ics.core.security.DefaultSecurityManager;
import com.incarcloud.ics.core.security.SecurityManager;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.servlet.AmbitoHttpServletRequest;
import com.incarcloud.ics.core.servlet.AmbitoHttpServletResponse;
import com.incarcloud.ics.core.session.Session;
import com.incarcloud.ics.core.subject.DefaultSubjectContext;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.subject.SubjectContext;
import com.incarcloud.ics.core.utils.ThreadContext;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class AbstractAmbitoFilter extends OncePerRequestFilter{

    private Logger logger = LoggerFactory.getLogger(AbstractAmbitoFilter.class);
    private FilterChainResolver filterChainResolver;
    private SecurityManager securityManager;

//    public AbstractAmbitoFilter(FilterChainResolver filterChainResolver, SecurityManager securityManager) {
//        this.filterChainResolver = filterChainResolver;
//        this.securityManager = securityManager;
//    }

    public FilterChainResolver getFilterChainResolver() {
        return filterChainResolver;
    }

    public void setFilterChainResolver(FilterChainResolver filterChainResolver) {
        this.filterChainResolver = filterChainResolver;
    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    protected final void onFilterConfigSet() throws Exception {
//        this.applyStaticSecurityManagerEnabledConfig();
//        this.init();
        this.ensureSecurityManager();
        SecurityUtils.setSecurityManager(this.getSecurityManager());
    }

    private void ensureSecurityManager() {
        SecurityManager securityManager = this.getSecurityManager();
        if (securityManager == null) {
            securityManager = new DefaultSecurityManager();
            this.setSecurityManager(securityManager);
        }
    }


    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, final FilterChain chain)
            throws ServletException, IOException {

        Throwable t = null;

        try {
            final ServletRequest request = prepareServletRequest(servletRequest, servletResponse, chain);
            final ServletResponse response = prepareServletResponse(request, servletResponse, chain);

            createSubject(request, response);
            //noinspection unchecked
//            subject.execute(new Callable() {
//                public Object call() throws Exception {
////                    updateSessionLastAccessTime(request, response);
//                    executeChain(request, response, chain);
//                    return null;
//                }
//            });
            updateSessionLastAccessTime(request, response);
            executeChain(request, response, chain);
        } catch (RuntimeException ex) {
            t = ex.getCause();
        } catch (Throwable throwable) {
            t = throwable;
        }

        if (t != null) {
            if (t instanceof ServletException) {
                throw (ServletException) t;
            }
            if (t instanceof IOException) {
                throw (IOException) t;
            }
            //otherwise it's not one of the two exceptions expected by the filter method signature - wrap it in one:
            String msg = "Filtered request failed.";
            throw new ServletException(msg, t);
        }
    }

    protected void updateSessionLastAccessTime(ServletRequest request, ServletResponse response) {
        if (!this.isHttpSessions()) {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                Session session = subject.getSession(false);
                if (session != null) {
                    try {
                        session.touch();
                    } catch (Throwable e) {
                        logger.error("session.touch() method invocation has failed.  Unable to updatethe corresponding session's last access time based on the incoming request.");
                    }
                }
            }
        }

    }

    /**
     * Creates a {@link Subject} instance to associate with the incoming request/response pair which will be used
     * throughout the request/response execution.
     *
     * @param request  the incoming {@code ServletRequest}
     * @param response the outgoing {@code ServletResponse}
     * @return the {@code WebSubject} instance to associate with the request/response execution
     * @since 1.0
     */
    protected Subject createSubject(ServletRequest request, ServletResponse response) {
        SubjectContext subjectContext = new DefaultSubjectContext();
        subjectContext.setSecurityManager(getSecurityManager());
        subjectContext.setServletRequest(request);
        subjectContext.setServletResponse(response);
        Subject subject = getSecurityManager().createSubject(subjectContext);
        ThreadContext.bind(subject);
        return subject;
    }

    /**
     * Prepares the {@code ServletRequest} instance that will be passed to the {@code FilterChain} for request
     * processing.
     * <p/>
     * If the {@code ServletRequest} is an instance of {@link HttpServletRequest}, the value returned from this method
     * is obtained by calling {@link #wrapServletRequest(javax.servlet.http.HttpServletRequest)} to allow Shiro-specific
     * HTTP behavior, otherwise the original {@code ServletRequest} argument is returned.
     *
     * @param request  the incoming ServletRequest
     * @param response the outgoing ServletResponse
     * @param chain    the Servlet Container provided {@code FilterChain} that will receive the returned request.
     * @return the {@code ServletRequest} instance that will be passed to the {@code FilterChain} for request processing.
     * @since 1.0
     */
    @SuppressWarnings({"UnusedDeclaration"})
    protected ServletRequest prepareServletRequest(ServletRequest request, ServletResponse response, FilterChain chain) {
        ServletRequest toUse = request;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest http = (HttpServletRequest) request;
            toUse = wrapServletRequest(http);
        }
        return toUse;
    }

    protected ServletRequest wrapServletRequest(HttpServletRequest orig) {
        return new AmbitoHttpServletRequest(orig, getServletContext(), isHttpSessions());
    }

    public boolean isHttpSessions(){
        return this.getSecurityManager().isHttpSessionMode();
    }

    /**
     * Prepares the {@code ServletResponse} instance that will be passed to the {@code FilterChain} for request
     * processing.
     * <p/>
     * This implementation delegates to {@link #wrapServletRequest(javax.servlet.http.HttpServletRequest)}
     * only if Shiro-based sessions are enabled (that is, !{@link #isHttpSessions()}) and the request instance is a
     * {@link AmbitoHttpServletRequest}.  This ensures that any URL rewriting that occurs is handled correctly using the
     * Shiro-managed Session's sessionId and not a servlet container session ID.
     * <p/>
     * If HTTP-based sessions are enabled (the default), then this method does nothing and just returns the
     * {@code ServletResponse} argument as-is, relying on the default Servlet Container URL rewriting logic.
     *
     * @param request  the incoming ServletRequest
     * @param response the outgoing ServletResponse
     * @param chain    the Servlet Container provided {@code FilterChain} that will receive the returned request.
     * @return the {@code ServletResponse} instance that will be passed to the {@code FilterChain} during request processing.
     * @since 1.0
     */
    @SuppressWarnings({"UnusedDeclaration"})
    protected ServletResponse prepareServletResponse(ServletRequest request, ServletResponse response, FilterChain chain) {
        ServletResponse toUse = response;
        if ((request instanceof AmbitoHttpServletRequest) &&
                (response instanceof HttpServletResponse)) {
            //the AmbitoHttpServletResponse exists to support URL rewriting for session ids.  This is only needed if
            //using Shiro sessions (i.e. not simple HttpSession based sessions):
            toUse = wrapServletResponse((HttpServletResponse) response, (AmbitoHttpServletRequest) request);
        }
        return toUse;
    }

    protected ServletResponse wrapServletResponse(HttpServletResponse orig, AmbitoHttpServletRequest request) {
        return new AmbitoHttpServletResponse(orig, getServletContext(), request);
    }

    /**
     * Executes a {@link FilterChain} for the given request.
     * <p/>
     * This implementation first delegates to
     * <code>{@link #getExecutionChain(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain) getExecutionChain}</code>
     * to allow the application's Shiro configuration to determine exactly how the chain should execute.  The resulting
     * value from that call is then executed directly by calling the returned {@code FilterChain}'s
     * {@link FilterChain#doFilter doFilter} method.  That is:
     * <pre>
     * FilterChain chain = {@link #getExecutionChain}(request, response, origChain);
     * chain.{@link FilterChain#doFilter doFilter}(request,response);</pre>
     *
     * @param request   the incoming ServletRequest
     * @param response  the outgoing ServletResponse
     * @param origChain the Servlet Container-provided chain that may be wrapped further by an application-configured
     *                  chain of Filters.
     * @throws IOException      if the underlying {@code chain.doFilter} call results in an IOException
     * @throws ServletException if the underlying {@code chain.doFilter} call results in a ServletException
     * @since 1.0
     */
    protected void executeChain(ServletRequest request, ServletResponse response, FilterChain origChain)
            throws IOException, ServletException {
        FilterChain chain = getExecutionChain(request, response, origChain);
        chain.doFilter(request, response);
    }


    /**
     * Returns the {@code FilterChain} to execute for the given request.
     * <p/>
     * The {@code origChain} argument is the
     * original {@code FilterChain} supplied by the Servlet Container, but it may be modified to provide
     * more behavior by pre-pending further chains according to the Shiro configuration.
     * <p/>
     * This implementation returns the chain that will actually be executed by acquiring the chain from a
     * {@link #getFilterChainResolver() filterChainResolver}.  The resolver determines exactly which chain to
     * execute, typically based on URL configuration.  If no chain is returned from the resolver call
     * (returns {@code null}), then the {@code origChain} will be returned by default.
     *
     * @param request   the incoming ServletRequest
     * @param response  the outgoing ServletResponse
     * @param origChain the original {@code FilterChain} provided by the Servlet Container
     * @return the {@link FilterChain} to execute for the given request
     * @since 1.0
     */
    protected FilterChain getExecutionChain(ServletRequest request, ServletResponse response, FilterChain origChain) {
        FilterChain chain = origChain;

        FilterChainResolver resolver = getFilterChainResolver();
        if (resolver == null) {
            logger.debug("No FilterChain configured for the current request.  Using the default.");
            return origChain;
        }

        FilterChain resolved = resolver.getChain(request, response, origChain);
        if (resolved != null) {
            chain = resolved;
        }else {
            logger.debug("No FilterChain configured for the current request.  Using the default.");
        }
        return chain;
    }




}
