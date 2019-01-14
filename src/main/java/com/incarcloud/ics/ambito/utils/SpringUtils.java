package com.incarcloud.ics.ambito.utils;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SpringUtils implements ApplicationContextAware {
    private static Logger logger = Logger.getLogger(SpringUtils.class.getName());

    private static ApplicationContext applicationContext = null;

    public SpringUtils() {
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public void destroy() throws Exception {
        applicationContext = null;
    }
}
