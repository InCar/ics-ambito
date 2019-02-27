package com.incarcloud.ics.ambito.utils.sqlInit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


//@Component
public class ContextListener implements ServletContextListener {
    @Autowired
    private InitSqlScript initSqlScript;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("---------->初始化sql");
        try {
            initSqlScript.executeSqlFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------->初始化完成");
    }


}
