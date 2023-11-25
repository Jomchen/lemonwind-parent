package com.lemonwind.test.aop;

import com.lemonwind.test.annotations.LogRecorder;
import com.lemonwind.test.common.Responsecode;
import com.lemonwind.test.common.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author lemonwind
 * @since 2021-05-08 11:28:20
 * 拦截一个接口多实现的方法
 */
@Aspect
@Slf4j
@Component
public class MultiInstanceServiceAop {

    @Autowired
    ApplicationContext applicationContext;

    @Pointcut("execution(public * com.lemonwind.test.service.impl.*.*(..))")
    public void cutPoint() {}

    /**
     * @author lemonwind
     * @since 2021-05-08 14:52:35
     * 拦截 com.lemonwind.service.impl 包及其所有子包的公共方法，并反射调用日志记录
     */
    @Around("cutPoint()")
    public Object proccess(ProceedingJoinPoint pjp) {

        log.info("****************************************** 进入了 MultiInstanceServiceAop");
        Object targetProxy = pjp.getTarget();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Method proxyMethod = methodSignature.getMethod();
        Object[] parameters = pjp.getArgs();
        String methodName = proxyMethod.getName();
        Class[] parameterTypes = proxyMethod.getParameterTypes();

        Class<?> superInterface = Arrays.stream(targetProxy.getClass().getInterfaces())
                .filter(i -> Arrays.stream(i.getAnnotations()).filter(anno -> anno instanceof LogRecorder).findAny().isPresent())
                .findFirst()
                .orElse(null);
        if (Objects.nonNull(superInterface)) {
            Map<String, Object> map = applicationContext.getBeansOfType((Class<Object>) superInterface);
            Object logTarget = map.entrySet().stream()
                .filter(entry -> entry.getKey().endsWith("Log"))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
            if (Objects.nonNull(logTarget)) {
                Method logMethod;
                try {
                    logMethod = logTarget.getClass().getDeclaredMethod(methodName, parameterTypes);
                } catch (NoSuchMethodException e) {
                    log.error("没有找到日志记录的方法", e);
                    return ResultObj.fail(Responsecode.FAILE).msg("没有找到日志记录的方法");
                }

                try {
                    logMethod.invoke(logTarget, parameters);
                } catch (IllegalAccessException e) {
                    log.error("调用日志记录方法失败", e);
                    return ResultObj.fail(Responsecode.FAILE).msg("调用日志记录方法失败");
                } catch (InvocationTargetException e) {
                    log.error("调用日志记录方法失败", e);
                    return ResultObj.fail(Responsecode.FAILE).msg("调用日志记录方法失败");
                }
            }
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.error("系统异常", throwable);
            return ResultObj.fail();
        }
    }

}
