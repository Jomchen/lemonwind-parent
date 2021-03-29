package com.jomkie.aop;

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

        @Pointcut("execution(public * com.jomkie.web..*.*(..))")
        public void testPointCut() {}

        @After("testPointCut()")
        public void aroundAop() {
            System.out.println("进入了 after 切面 Start");
            System.out.println("这里是 After");
            System.out.println("进入了 after 切面 End");
        }

}
