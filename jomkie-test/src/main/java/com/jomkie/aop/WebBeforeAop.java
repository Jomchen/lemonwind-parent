package com.jomkie.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(1)
public class WebBeforeAop {

    @Pointcut("execution(public * com.jomkie.web..*.*(..))")
    public void testPointCut() {}

    @Before("testPointCut()")
    public void aroundAop() {
        log.info("进入了 before 切面");
    }

}
