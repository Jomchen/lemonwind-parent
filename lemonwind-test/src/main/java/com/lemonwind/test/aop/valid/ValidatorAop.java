package com.lemonwind.test.aop.valid;

import com.lemonwind.test.annotations.ReqValidGroup;
import com.lemonwind.test.common.LemonException;
import com.lemonwind.test.common.Responsecode;
import com.lemonwind.test.common.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.assertj.core.util.Arrays;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final String BUILD_PARAM_METHOD_NAME = "buildActualParam";

    /** 此处选择对象验证，如果选择方法验证则错误信息会在 springValidator 的参数序列化注入后即验证，不会走 aop */
    /*private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();*/

    /** 如果使用了对某种类型的自定义验证，则如果验证不通过，请求会变为 400 */
    private ExecutableValidator executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void webPointCut() {}
    @Pointcut("@annotation(com.lemonwind.test.annotations.ReqValidGroup)")
    public void reqValidGroup() {}

    @Around("webPointCut() && reqValidGroup()")
    public Object proccess(ProceedingJoinPoint pjp) {

        log.info("进入了参数验证 Aop ...");

        Object[] args = pjp.getArgs();
        Object target = pjp.getTarget();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        ReqValidGroup reqValidGroup = method.getAnnotation(ReqValidGroup.class);
        Class<?>[] groupsPreValidated = reqValidGroup.value();
        Set<ConstraintViolation<Object>> errorSet;
        if (Arrays.isNullOrEmpty(groupsPreValidated)) {
            errorSet = executableValidator.validateParameters(target, method, args);
        } else {
            errorSet = executableValidator.validateParameters(target, method, args, groupsPreValidated);
        }

        List<String> errorList = new LinkedList<>();
        if (reqValidGroup.onlyOneError()) {
            errorSet.stream().findFirst().map(ConstraintViolation::getMessage).ifPresent(errorList::add);
        } else {
            errorSet.stream().map(ConstraintViolation::getMessage).forEach(errorList::add);
        }

        // 获取参数错误信息
        if ( ! CollectionUtils.isEmpty(errorList)) {
            String errorMsg = errorList.stream().collect(Collectors.joining("，"));
            return ResultObj.fail(Responsecode.PARAM_ERROR).msg(errorMsg);
        }

        // 执行构建参数的方法
        /*Arrays.stream(args).forEach(this::buildActualParam);*/

        Object errorResult;
        try {
            return pjp.proceed();
        } catch (LemonException lemonException) {
            log.error(lemonException.getMessage(), lemonException);
            errorResult = ResultObj.fail(lemonException);
        } catch (Throwable throwable) {
            log.error(Responsecode.SYSTEM_ERROR.getMsg(), throwable);
            errorResult = ResultObj.fail(Responsecode.SYSTEM_ERROR);
        }

        return errorResult;
    }

    /**
     * @author Jomkie
     * @since 2021-05-07 22:46:38
     * 反涉调度构建参数方法
     */
    private void buildActualParam(Object obj) {
        if (Objects.isNull(obj)) { return; }
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
