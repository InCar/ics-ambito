package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.exception.UnAuthorizeException;
import com.incarcloud.ics.core.exception.ExpiredSessionException;
import com.incarcloud.ics.core.exception.InvalidSessionException;
import com.incarcloud.ics.core.exception.SessionException;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractValidateSessionManager extends AbstractNativeSessionManager implements ValidateSessionManager {

    private static final Logger logger = Logger.getLogger(AbstractValidateSessionManager.class.getName());
    /**
     * 默认每个小时检测一次session是否过期
     */
    public static final long DEFAULT_SESSION_VALIDATE_INTERVAL = MILLIS_PER_HOUR;
    /**
     * 是否允许
     */
    protected boolean sessionValidationSchedulerEnabled;
    protected long sessionValidateInterval;
    protected SessionValidationScheduler sessionValidationScheduler;

    public AbstractValidateSessionManager() {
        this.sessionValidationSchedulerEnabled = true;
        this.sessionValidateInterval = DEFAULT_SESSION_VALIDATE_INTERVAL;
    }

    public boolean isSessionValidationSchedulerEnabled() {
        return sessionValidationSchedulerEnabled;
    }

    public void setSessionValidationSchedulerEnabled(boolean sessionValidationSchedulerEnabled) {
        this.sessionValidationSchedulerEnabled = sessionValidationSchedulerEnabled;
    }

    public long getSessionValidateInterval() {
        return sessionValidateInterval;
    }

    public void setSessionValidateInterval(long sessionValidateInterval) {
        this.sessionValidateInterval = sessionValidateInterval;
    }

    public SessionValidationScheduler getSessionValidationScheduler() {
        return sessionValidationScheduler;
    }

    public void setSessionValidationScheduler(SessionValidationScheduler sessionValidationScheduler) {
        this.sessionValidationScheduler = sessionValidationScheduler;
    }

    @Override
    protected Session createSession(SessionContext context) throws UnAuthorizeException {
        enableSessionValidationIfNecessary();
        return doCreateSession(context);
    }

    public abstract Session doCreateSession(SessionContext context) throws UnAuthorizeException;

    @Override
    public Session doGetSession(SessionKey key) throws SessionException {
        enableSessionValidationIfNecessary();
        Session s = retrieveSession(key);
        if (s != null) {
            validate(s, key);
        }
        return s;
    }

    protected abstract Session retrieveSession(SessionKey key);

    protected void validate(Session session, SessionKey key) throws InvalidSessionException {
        try {
            doValidate(session);
        } catch (ExpiredSessionException ese) {
            onExpiration(session, ese, key);
            throw ese;
        } catch (InvalidSessionException ise) {
            onInvalidation(session, ise, key);
            throw ise;
        }
    }

    protected void onExpiration(Session s, ExpiredSessionException ese, SessionKey key) {
        logger.fine("Session with id [{"+s.getId()+"}] has expired.");
        try {
            onExpiration(s);
//            notifyExpiration(s);
        } finally {
            afterExpired(s);
        }
    }

    protected void afterExpired(Session s){
    }

    protected void onExpiration(Session s){
    }

    private void onInvalidation(Session s, InvalidSessionException ise, SessionKey key) {
        if (ise instanceof ExpiredSessionException) {
            onExpiration(s, (ExpiredSessionException) ise, key);
            return;
        }
        logger.fine("Session with id [{"+s.getId()+"}] is invalid.");
        try {
            onStop(s);
//            notifyStop(s);
        } finally {
            afterStopped(s);
        }
    }


    protected void doValidate(Session session) throws InvalidSessionException {
        if (session instanceof ValidatingSession) {
            ((ValidatingSession) session).validate();
        } else {
            String msg = "The " + getClass().getName() + " implementation only supports validating " +
                    "Session implementations of the " + ValidatingSession.class.getName() + " interface.  " +
                    "Please either implement this interface in your session implementation or override the " +
                    AbstractValidateSessionManager.class.getName() + ".doValidate(Session) method to perform validation.";
            throw new IllegalStateException(msg);
        }
    }



    @Override
    public void validateSessions() {
        int invalidCount = 0;

        Collection<Session> activeSessions = getActiveSessions();

        if (activeSessions != null && !activeSessions.isEmpty()) {
            for (Session s : activeSessions) {
                try {
                    //simulate a lookup key to satisfy the method signature.
                    //this could probably stand to be cleaned up in future versions:
                    SessionKey key = new WebSessionKey(s.getId());
                    validate(s, key);
                } catch (InvalidSessionException e) {
                    if (logger.isLoggable(Level.FINE)) {
                        boolean expired = (e instanceof ExpiredSessionException);
                        String msg = "Invalidated session with id [" + s.getId() + "]" +
                                (expired ? " (expired)" : " (stopped)");
                        logger.fine(msg);
                    }
                    invalidCount++;
                }
            }
        }

        if (logger.isLoggable(Level.INFO)) {
            String msg = "Finished session validation.";
            if (invalidCount > 0) {
                msg += "  [" + invalidCount + "] sessions were stopped.";
            } else {
                msg += "  No sessions were stopped.";
            }
            logger.info(msg);
        }
    }


    private void enableSessionValidationIfNecessary() {
        SessionValidationScheduler scheduler = getSessionValidationScheduler();
        if (isSessionValidationSchedulerEnabled() && (scheduler == null || !scheduler.isEnabled())) {
            enableSessionValidation();
        }
    }

    protected synchronized void enableSessionValidation() {
        SessionValidationScheduler scheduler = getSessionValidationScheduler();
        if (scheduler == null) {
            scheduler = createSessionValidationScheduler();
            setSessionValidationScheduler(scheduler);
        }
        // it is possible that that a scheduler was already created and set via 'setSessionValidationScheduler()'
        // but would not have been enabled/started yet
        if (!scheduler.isEnabled()) {
            if (logger.isLoggable(Level.FINE)) {
                logger.info("Enabling session validation scheduler...");
            }
            scheduler.enableSessionValidation();
        }
    }

    protected SessionValidationScheduler createSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler;

        if (logger.isLoggable(Level.FINE)) {
            logger.fine("No sessionValidationScheduler set.  Attempting to create default instance.");
        }
        scheduler = new ExecutorServiceSessionValidationScheduler(this);
        scheduler.setInterval(getSessionValidateInterval());
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Created default SessionValidationScheduler instance of type [" + scheduler.getClass().getName() + "].");
        }
        return scheduler;
    }

    protected long getTimeout(Session session) {
        return session.getTimeout();
    }


    protected abstract Collection<Session> getActiveSessions();
}
