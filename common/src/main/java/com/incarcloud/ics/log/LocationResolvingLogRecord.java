package com.incarcloud.ics.log;

import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * 用于对获取具体类名及方法进行打印，直接从apache.commons.logging包中复制过来
 */
public class LocationResolvingLogRecord extends LogRecord {
        private static final String FQCN = DelegatingLogger.class.getName();
        private volatile boolean resolved;

        public LocationResolvingLogRecord(Level level, String msg) {
            super(level, msg);
        }

        public String getSourceClassName() {
            if (!this.resolved) {
                this.resolve();
            }

            return super.getSourceClassName();
        }

        public void setSourceClassName(String sourceClassName) {
            super.setSourceClassName(sourceClassName);
            this.resolved = true;
        }

        public String getSourceMethodName() {
            if (!this.resolved) {
                this.resolve();
            }

            return super.getSourceMethodName();
        }

        public void setSourceMethodName(String sourceMethodName) {
            super.setSourceMethodName(sourceMethodName);
            this.resolved = true;
        }

        private void resolve() {
            StackTraceElement[] stack = (new Throwable()).getStackTrace();
            String sourceClassName = null;
            String sourceMethodName = null;
            boolean found = false;
            StackTraceElement[] stackTraceElements = stack;
            int length = stack.length;

            for(int i = 0; i < length; ++i) {
                StackTraceElement element = stackTraceElements[i];
                String className = element.getClassName();
                if (FQCN.equals(className)) {
                    found = true;
                } else if (found) {
                    sourceClassName = className;
                    sourceMethodName = element.getMethodName();
                    break;
                }
            }

            this.setSourceClassName(sourceClassName);
            this.setSourceMethodName(sourceMethodName);
        }

        protected Object writeReplace() {
            LogRecord serialized = new LogRecord(this.getLevel(), this.getMessage());
            serialized.setLoggerName(this.getLoggerName());
            serialized.setResourceBundle(this.getResourceBundle());
            serialized.setResourceBundleName(this.getResourceBundleName());
            serialized.setSourceClassName(this.getSourceClassName());
            serialized.setSourceMethodName(this.getSourceMethodName());
            serialized.setSequenceNumber(this.getSequenceNumber());
            serialized.setParameters(this.getParameters());
            serialized.setThreadID(this.getThreadID());
            serialized.setMillis(this.getMillis());
            serialized.setThrown(this.getThrown());
            return serialized;
        }
    }