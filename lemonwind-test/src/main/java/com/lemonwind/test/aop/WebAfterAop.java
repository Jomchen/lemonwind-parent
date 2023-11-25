package com.lemonwind.test.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
@Order(1)
public class WebAfterAop {

        @Pointcut("execution(public * com.lemonwind.web..*.*(..))")
        public void testPointCut() {}

        @After("testPointCut()")
        public void aroundAop() {
            log.info("进入了 after 切面");
        }

}
