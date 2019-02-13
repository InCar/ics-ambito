package com.incarcloud.ics.log;


import com.incarcloud.ics.config.Config;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/2/1
 */
public class LoggerFactory extends com.incarcloud.skeleton.log.LoggerFactory {

    private static class Holder {
        private static java.util.logging.Logger jdkLogger = getLogger(Config.getConfig().getLogConfig());
    }

    public synchronized static Logger getLogger(Class<?> clazz){
        java.util.logging.Logger logger = Holder.jdkLogger;
        return new DelegatingLogger(clazz.getName(), logger);
    }
}
