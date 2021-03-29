package com.jomkie.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(1)
public class WebAfterThrowingAop {

    @Pointcut("execution(public * com.jomkie.web..*.*(..))")
    public void testPointCut() {}

    @AfterThrowing("testPointCut()")
    public void afterThrowing() {
        System.out.println("进入了 afterThrowing 切面 Start");
        System.out.println("进入了 afterThrowing 切面 End");
    }

}
