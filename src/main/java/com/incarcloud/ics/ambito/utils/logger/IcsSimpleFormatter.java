//package com.incarcloud.ics.ambito.utils.logger;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.time.Instant;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.logging.LogRecord;
//import java.util.logging.SimpleFormatter;
//
//
//public class IcsSimpleFormatter extends SimpleFormatter {
//
//    private static final String DEFAULT_FORMAT = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %8$s %4$s --- [%7$s] %3$s: %5$s%6$s%n";
//    private final String format = getOrUseDefault("LOG_FORMAT", DEFAULT_FORMAT);
//    private final String pid = getOrUseDefault("PID", "????");
//    private final Date date = new Date();
//
//    @Override
//    public synchronized String format(LogRecord record) {
//        this.date.setTime(record.getMillis());
//        String source = record.getLoggerName();
//        String message = this.formatMessage(record);
//        String throwable = this.getThrowable(record);
//        String thread = this.getThreadName();
//        return String.format(this.format, this.date, source, record.getLoggerName(), record.getLevel(), message, throwable, thread, this.pid);
//    }
//
//    private String getThrowable(LogRecord record) {
//        if (record.getThrown() == null) {
//            return "";
//        } else {
//            StringWriter stringWriter = new StringWriter();
//            PrintWriter printWriter = new PrintWriter(stringWriter);
//            printWriter.println();
//            record.getThrown().printStackTrace(printWriter);
//            printWriter.close();
//            return stringWriter.toString();
//        }
//    }
//
//    private String getThreadName() {
//        String name = Thread.currentThread().getName();
//        return name != null ? name : "";
//    }
//
//    private static String getOrUseDefault(String key, String defaultValue) {
//        String value = null;
//
//        try {
//            value = System.getenv(key);
//        } catch (Exception var4) {
//            ;
//        }
//
//        if (value == null) {
//            value = defaultValue;
//        }
//
//        return System.getProperty(key, value);
//    }
//}