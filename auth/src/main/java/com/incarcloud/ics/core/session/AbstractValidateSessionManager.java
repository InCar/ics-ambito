package com.incarcloud.ics.core.session;

import com.incarcloud.ics.core.exception.ExpiredSessionException;
import com.incarcloud.ics.core.exception.InvalidSessionException;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractValidateSessionManager extends AbstractNativeSessionManager implements ValidateSessionManager {

    private static final Logger logger = Logger.getLogger(AbstractValidateSessionManager.class.getName());

    protected void validate(Session session, SessionKey key) throws InvalidSessionException {
        try {
            doValidate(session);
        } catch (ExpiredSessionException ese) {
//            onExpiration(session, ese, key);
            throw ese;
        } catch (InvalidSessionException ise) {
//            onInvalidation(session, ise, key);
            throw ise;
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

    protected abstract Collection<Session> getActiveSessions();
}
