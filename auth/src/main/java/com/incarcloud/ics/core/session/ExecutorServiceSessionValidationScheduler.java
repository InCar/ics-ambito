/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.incarcloud.ics.core.session;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * SessionValidationScheduler implementation that uses a
 * {@link ScheduledExecutorService} to call {@link ValidateSessionManager#validateSessions()} every
 * <em>{@link #getInterval interval}</em> milliseconds.
 *
 * @since 0.9
 */
public class ExecutorServiceSessionValidationScheduler implements SessionValidationScheduler, Runnable {

    //TODO - complete JavaDoc

    /** Private internal log instance. */
    private static final Logger log = Logger.getLogger(ExecutorServiceSessionValidationScheduler.class.getName());

    ValidateSessionManager sessionManager;
    private ScheduledExecutorService service;
    private long interval = DefaultWebSessionManager.DEFAULT_SESSION_VALIDATE_INTERVAL;
    private boolean enabled = false;
    private String threadNamePrefix = "SessionValidationThread-";

    public ExecutorServiceSessionValidationScheduler() {
        super();
    }

    public ExecutorServiceSessionValidationScheduler(ValidateSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public ValidateSessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(ValidateSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public String getThreadNamePrefix() {
        return this.threadNamePrefix;
    }

    /**
     * Creates a single thread {@link ScheduledExecutorService} to validate sessions at fixed intervals 
     * and enables this scheduler. The executor is created as a daemon thread to allow JVM to shut down
     */
    //TODO Implement an integration test to test for jvm exit as part of the standalone example
    // (so we don't have to change the unit test execution model for the core module)
    public void enableSessionValidation() {
        if (this.interval > 0l) {
            this.service = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {  
	            private final AtomicInteger count = new AtomicInteger(1);

	            public Thread newThread(Runnable r) {  
	                Thread thread = new Thread(r);  
	                thread.setDaemon(true);  
	                thread.setName(threadNamePrefix + count.getAndIncrement());
	                return thread;  
	            }  
            });                  
            this.service.scheduleAtFixedRate(this, interval, interval, TimeUnit.MILLISECONDS);
        }
        this.enabled = true;
    }

    public void run() {
        if (log.isLoggable(Level.FINE)) {
            log.fine("Executing session validation...");
        }
        long startTime = System.currentTimeMillis();
        this.sessionManager.validateSessions();
        long stopTime = System.currentTimeMillis();
        if (log.isLoggable(Level.FINE)) {
            log.fine("Session validation completed successfully in " + (stopTime - startTime) + " milliseconds.");
        }
    }
    
    public void disableSessionValidation() {
        if (this.service != null) {
            this.service.shutdownNow();
        }
        this.enabled = false;
    }
}
