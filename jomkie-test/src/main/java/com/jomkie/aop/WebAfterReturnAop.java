package com.jomkie.aop;

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


    @Pointcut("execution(public * com.jomkie.web..*.*(..))")
    public void testPointCut() {}

    @AfterReturning(pointcut = "testPointCut()", returning = "result")
    public void afterReturnAop(Object result) {
        System.out.println("进入了 afterReturn 切面 Start");
        System.out.println(String.format("-------------->%s", JSONObject.toJSONString(result)));
        System.out.println("进入了 afterReturn 切面 End");
    }

}
