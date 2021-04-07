package com.jomkie.aop;

import com.jomkie.annotations.ReqValidGroup;
import com.jomkie.common.BaseCodeResult;
import com.jomkie.common.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Jomkie
 * @date 2021-04-3 0:30
 * 数据验证拦截
 */
@Component
@Aspect
@Slf4j
@Order(2)
public class ValidatorAop {

    private Validator validator;

    @PostConstruct
    public void before() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @Around("within(org.springframework.web.bind.annotation.RestController)")
    public Object proccess(ProceedingJoinPoint pjp) {

        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();

        // 一个参数只能有一个 RequiredValidGroup 注解，否则只获取对应参数的第一个 RequiredValidGroup 注解
        log.info("进入了验证参数切面。。。。。。 Start");
        List<String> errorList = new ArrayList<>();
        if (null != paramAnnotations && paramAnnotations.length > 0) {
            IntStream.range(0, paramAnnotations.length).forEach(index ->
                Arrays.stream(paramAnnotations[index])
                        .filter(anno -> anno.getClass().equals(ReqValidGroup.class))
                        .findFirst()
                        .map(anno ->  (ReqValidGroup) anno)
                        .ifPresent(anno -> {
                            Class<?>[] validGroup = anno.value();
                            Set<ConstraintViolation<Object>> errorSet = validator.validate(args[index], validGroup);
                            if (!CollectionUtils.isEmpty(errorList)) {
                                errorSet.stream().map(ConstraintViolation::getMessage).forEach(errorList::add);
                            }

                            /* 验证 args[index] 的验证组信息 */
                            /* 验证 args[index] 的验证组信息 */
                            /* 验证 args[index] 的验证组信息 */
                            /* 收集错误信息 */
                            /* 收集错误信息 */
                            /* 收集错误信息 */
                        })
            );
        }
        log.info("进入了验证参数切面。。。。。。 End");

        // 获取参数错误信息
        if (!CollectionUtils.isEmpty(errorList)) {
            String errorMsg = errorList.stream().collect(Collectors.joining("，"));
            return ResultObj.fail(BaseCodeResult.PARAM_ERROR).msg(errorMsg);
        }

        // 正常方法执行
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.error("系统异常", throwable);
            return ResultObj.fail();
        }
    }

}
