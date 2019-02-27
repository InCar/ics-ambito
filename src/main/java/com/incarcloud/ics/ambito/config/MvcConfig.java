package com.incarcloud.ics.ambito.config;


import com.incarcloud.ics.config.Config;
import com.incarcloud.ics.core.utils.ClassResolverUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/2/26
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    public static Set<Class<?>> controllers = ClassResolverUtils.findAnnotated(RestController.class, "com.incarcloud.ics.ambito.controller");
//    public static Set<String> exculdeSuffix = new HashSet<>(Arrays.asList(".js",".css",".svg",".html"));

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ApiAwareRequestMappingHandlerMapping();
    }

    private static class ApiAwareRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

        @Override
        protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
            Class<?> beanType = method.getDeclaringClass();
            if(controllers.contains(beanType)){
                PatternsRequestCondition apiPattern = new PatternsRequestCondition(Config.getConfig().getUrlPrefix())
                        .combine(mapping.getPatternsCondition());

                mapping = new RequestMappingInfo(mapping.getName(), apiPattern, mapping.getMethodsCondition(),
                        mapping.getParamsCondition(), mapping.getHeadersCondition(), mapping.getConsumesCondition(),
                        mapping.getProducesCondition(), mapping.getCustomCondition());
            }
            super.registerHandlerMethod(handler, method, mapping);
        }
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/");
        String prefix = Config.getConfig().getUrlPrefix();
        registry.addResourceHandler(prefix + "/**")
                .addResourceLocations("classpath:"+prefix+"/");
    }
}
