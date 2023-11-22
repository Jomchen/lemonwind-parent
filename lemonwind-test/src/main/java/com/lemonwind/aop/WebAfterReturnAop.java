package com.lemonwind.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(1)
public class WebAfterReturnAop {


    @Pointcut("execution(public * com.lemonwind.web..*.*(..))")
    public void testPointCut() {}

    @AfterReturning(pointcut = "testPointCut()", returning = "result")
    public void afterReturnAop(Object result) {
        log.info("进入了 afterReturn 切面");
    }

}
