package com.incarcloud.ics.ambito.utils.logger;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/2/1
 */
public class LoggerFactory {

    public synchronized static Logger getLogger(Class<?> clazz){
        return new IcsDelegateLogger(clazz.getName());
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
        logger.debug("1111");
    }
}
