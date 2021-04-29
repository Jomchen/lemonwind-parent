package com.jomkie.aop;

import com.jomkie.annotations.ReqValidGroup;
import com.jomkie.common.BaseResponse;
import com.jomkie.common.LemonException;
import com.jomkie.common.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Jomkie
 * @since 2021-04-3 0:30
 * 数据验证拦截
 */
@Component
@Aspect
@Slf4j
@Order(1)
public class ValidatorAop {

    final String BUILD_PARAM_METHOD_NAME = "buildActualParam";

    @Autowired
    private Validator validator;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void webPointCut() {}
    @Pointcut("@args(com.jomkie.aop.valid.NeedValidating)")
    public void validatPointCut() {}
    @Pointcut("args(com.jomkie.common.PreBuildParamDto)")
    public void preBuildParamCut() {}

    @Around("webPointCut() && validatPointCut() && preBuildParamCut()")
    public Object proccess(ProceedingJoinPoint pjp) {

        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();

        // 一个参数只能有一个 RequiredValidGroup 注解，否则只获取对应参数的第一个 RequiredValidGroup 注解
        log.info("Entered validator around aspect ... Start");
        List<String> errorList = new ArrayList<>();
        if (null != paramAnnotations && paramAnnotations.length > 0) {
            IntStream.range(0, paramAnnotations.length).forEach(index ->
                Arrays.stream(paramAnnotations[index])
                        .filter(anno -> anno instanceof ReqValidGroup)
                        .findFirst()
                        .map(anno ->  (ReqValidGroup) anno)
                        .ifPresent(anno -> {
                            Class<?>[] validGroup = anno.value();
                            Set<ConstraintViolation<Object>> errorSet;
                            if (validGroup.length > 0) {
                                errorSet = validator.validate(args[index], validGroup);
                            } else {
                                errorSet = validator.validate(args[index]);
                            }
                            errorSet.stream().map(ConstraintViolation::getMessage).forEach(errorList::add);
                        }
                )
            );
        }
        log.info("Entered validator around aspect ... End");

        // 获取参数错误信息
        if (!CollectionUtils.isEmpty(errorList)) {
            String errorMsg = errorList.stream().collect(Collectors.joining("，"));
            return ResultObj.fail(BaseResponse.PARAM_ERROR).msg(errorMsg);
        }

        // 执行构建参数的方法
        Arrays.stream(args).forEach(this::buildActualParam);

        // 正常方法执行
        try {
            return pjp.proceed();
        } catch (LemonException lemonException) {
            log.error(lemonException.getMessage(), lemonException);
            return ResultObj.fail(lemonException);
        } catch (Throwable throwable) {
            log.error("系统异常", throwable);
            return ResultObj.fail();
        }
    }

    private void buildActualParam(Object obj) {
        Class clazz = obj.getClass();
        try {
            Method method = clazz.getMethod(BUILD_PARAM_METHOD_NAME);
            method.invoke(obj);
        } catch (NoSuchMethodException e) {
            log.info("have no {} method.", BUILD_PARAM_METHOD_NAME, e);
        } catch (InvocationTargetException e) {
            log.error("invok {} failed.", BUILD_PARAM_METHOD_NAME, e);
        } catch (IllegalAccessException e) {
            log.error("invok {} failed.", BUILD_PARAM_METHOD_NAME, e);
        }
    }

}
