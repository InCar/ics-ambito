//package com.incarcloud.ics.core.handler;
//
//import com.incarcloud.ics.core.exception.SecurityException;
//
///**
// * @author ThomasChan
// * @version 1.0
// * @description
// * @date 2019/1/31
// */
//public class SimpleHttpExceptionHandlerFactory implements HttpExceptionHandlerFactory {
//
//    public static HttpExceptionHandlerFactory getInstance(){
//        return FactoryHolder.INSTANCE;
//    }
//    /**
//     * 当前类的实例持有者（静态内部类，延迟加载，懒汉式，线程安全的单例模式）
//     */
//    private static class FactoryHolder{
//        private static HttpExceptionHandlerFactory INSTANCE = new SimpleHttpExceptionHandlerFactory();
//    }
//
//    @Override
//    public HttpExceptionHandler newHandler(SecurityException ex) {
//        return new HttpSecurityExceptionHandler(ex) {};
//    }
//}
