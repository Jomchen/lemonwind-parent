package com.lemonwind.aop;

import com.lemonwind.common.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/*@Aspect
@Component
@Slf4j
@Order(1)
public class WebAroundAop {

    @Pointcut("execution(public * com.lemonwind.web..*.*(..))")
    public void testPointCut() {}

    @Around("testPointCut()")
    public Object aroundAop(ProceedingJoinPoint pjp) {

        try {
            System.out.println("进入了 aroundAop Start");
            System.out.println("进入了 aroundAop End");
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.error("服务器异常", throwable);
            return ResultObj.fail();
        }

    }

}*/
