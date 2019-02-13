
package com.incarcloud.ics.log;

public interface Logger {
    boolean isFatalEnabled();

    boolean isErrorEnabled();

    boolean isWarnEnabled();

    boolean isInfoEnabled();

    boolean isDebugEnabled();

    boolean isTraceEnabled();

    void fatal(Object obj);

    void fatal(Object obj, Throwable throwable);

    void error(Object obj);

    void error(Object obj, Throwable throwable);

    void warn(Object obj);

    void warn(Object obj, Throwable throwable);

    void info(Object obj);

    void info(Object obj, Throwable throwable);

    void debug(Object obj);

    void debug(Object obj, Throwable throwable);

    void trace(Object obj);

    void trace(Object obj, Throwable throwable);
}
