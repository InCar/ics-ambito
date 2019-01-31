package com.incarcloud.ics.core.aspect.intercepter;


import com.incarcloud.ics.core.aspect.MethodInvocation;

public interface MethodInterceptor {
    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
