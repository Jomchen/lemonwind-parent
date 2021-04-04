package com.jomkie.aop;

import com.jomkie.annotations.RequiredValidGroup;
import com.jomkie.common.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Jomkie
 * @date 2021-04-3 0:30
 * 数据验证拦截
 */
@Component
@Aspect
@Slf4j
@Order(1)
public class ValidatorAop {

    @Around("within(org.springframework.web.bind.annotation.RestController)")
    public Object proccess(ProceedingJoinPoint pjp) {

        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();

        // 一个参数只能有一个 RequiredValidGroup 注解，否则只获取对应参数的第一个 RequiredValidGroup 注解
        if (null != paramAnnotations && paramAnnotations.length > 0) {
            IntStream.range(0, paramAnnotations.length).forEach(index ->
                Arrays.stream(paramAnnotations[index])
                        .filter(anno -> anno.getClass().equals(RequiredValidGroup.class))
                        .findFirst()
                        .map(anno ->  (RequiredValidGroup) anno)
                        .ifPresent(anno -> {
                            Class<?>[] validGroup = anno.value();
                            /* 验证 args[index] 的验证组信息 */
                            /* 验证 args[index] 的验证组信息 */
                            /* 验证 args[index] 的验证组信息 */
                            /* 收集错误信息 */
                            /* 收集错误信息 */
                            /* 收集错误信息 */
                        })
            );
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.error("系统异常", throwable);
            return ResultObj.fail();
        }
    }
}
