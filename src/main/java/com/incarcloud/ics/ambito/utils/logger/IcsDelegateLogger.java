package com.incarcloud.ics.ambito.utils.logger;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class IcsDelegateLogger implements Logger, Serializable {
        private String name;
        private transient java.util.logging.Logger logger;

        public IcsDelegateLogger(String name) {
            this.name = name;
            this.logger = java.util.logging.Logger.getLogger(name);
        }

        public boolean isFatalEnabled() {
            return this.isErrorEnabled();
        }

        public boolean isErrorEnabled() {
            return this.logger.isLoggable(Level.SEVERE);
        }

        public boolean isWarnEnabled() {
            return this.logger.isLoggable(Level.WARNING);
        }

        public boolean isInfoEnabled() {
            return this.logger.isLoggable(Level.INFO);
        }

        public boolean isDebugEnabled() {
            return this.logger.isLoggable(Level.FINE);
        }

        public boolean isTraceEnabled() {
            return this.logger.isLoggable(Level.FINEST);
        }

        public void fatal(Object message) {
            this.error(message);
        }

        public void fatal(Object message, Throwable exception) {
            this.error(message, exception);
        }

        public void error(Object message) {
            this.log(Level.SEVERE, message, (Throwable)null);
        }

        public void error(Object message, Throwable exception) {
            this.log(Level.SEVERE, message, exception);
        }

        public void warn(Object message) {
            this.log(Level.WARNING, message, (Throwable)null);
        }

        public void warn(Object message, Throwable exception) {
            this.log(Level.WARNING, message, exception);
        }

        public void info(Object message) {
            this.log(Level.INFO, message, (Throwable)null);
        }

        public void info(Object message, Throwable exception) {
            this.log(Level.INFO, message, exception);
        }

        public void debug(Object message) {
            this.log(Level.FINE, message, (Throwable)null);
        }

        public void debug(Object message, Throwable exception) {
            this.log(Level.FINE, message, exception);
        }

        public void trace(Object message) {
            this.log(Level.FINEST, message, (Throwable)null);
        }

        public void trace(Object message, Throwable exception) {
            this.log(Level.FINEST, message, exception);
        }

        private void log(Level level, Object message, Throwable exception) {
            if (this.logger.isLoggable(level)) {
                Object rec;
                if (message instanceof LogRecord) {
                    rec = (LogRecord)message;
                } else {
                    rec = new LocationResolvingLogRecord(level, String.valueOf(message));
                    ((LogRecord)rec).setLoggerName(this.name);
                    ((LogRecord)rec).setResourceBundleName(this.logger.getResourceBundleName());
                    ((LogRecord)rec).setResourceBundle(this.logger.getResourceBundle());
                    ((LogRecord)rec).setThrown(exception);
                }
                this.logger.log((LogRecord)rec);
            }
        }

        protected Object readResolve() {
            return new IcsDelegateLogger(this.name);
        }
    }