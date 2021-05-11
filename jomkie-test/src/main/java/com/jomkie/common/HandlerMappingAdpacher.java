package com.jomkie.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Objects;

@Component
public class HandlerMappingAdpacher implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handelMethods = requestMappingHandlerMapping.getHandlerMethods();
        handelMethods.entrySet().stream().forEach(entry -> {
            System.out.println("--------------------------------------------------------------------");
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            if (Objects.nonNull(requestMappingInfo)) {
                PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                patternsCondition.getPatterns().stream().forEach(e -> System.out.println("patthern: " + e));
            }
            if (Objects.nonNull(handlerMethod)) {
                System.out.println("beanType" + handlerMethod.getBeanType().getCanonicalName());
                System.out.println(handlerMethod.getMethod().getName());
            }
        });
    }

}
