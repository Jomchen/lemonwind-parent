package com.jomkie.aop.valid;

import com.jomkie.annotations.LogAop;
import com.jomkie.common.ResultObj;
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

/**
 * @author Jomkie
 * @since 2021-05-08 11:28:20
 * 拦截一个接口多实现的方法
 */
@Aspect
@Slf4j
@Component
public class MultiInstanceServiceAop {

    @Autowired
    ApplicationContext applicationContext;

    @Pointcut("this(com.jomkie.service.AopService)")
    public void cutPoint() {}

    @Pointcut("@annotation(com.jomkie.annotations.LogAop)")
    public void cutPoint2() {}

    /** TODO 拦截接口，反射调度两个实现类未实现，会造成递归拦截 */
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
                .filter(i -> Arrays.stream(i.getAnnotations()).filter(anno -> anno instanceof LogAop).findAny().isPresent())
                .findFirst()
                .orElse(null);
        log.info("targetProxy is : " + targetProxy.getClass().getName());
        if (null != superInterface) {
            log.info("superInterface's name is : " + superInterface.getName());
            log.info("superInterface's simepleName is : " + superInterface.getSimpleName());
            log.info("superInterface's typeName is : " + superInterface.getTypeName());
            log.info("superInterface's canonicalName is : " + superInterface.getCanonicalName());
        }

        Map<String, Object> map = applicationContext.getBeansOfType((Class<Object>) superInterface);
        Object finalResult = null;

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String instanceName = entry.getKey();
            Object targetObj = entry.getValue();
            log.info("instanceName is {}", instanceName);

            Method method = null;
            try {
                method = targetObj.getClass().getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            Object result = null;
            try {
                result = method.invoke(targetObj, parameters);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            if (instanceName.endsWith("Impl")) {
                finalResult = result;
            }
        }



        return finalResult;
        /*try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.error("系统异常", throwable);
            return ResultObj.fail();
        }*/
    }

}
