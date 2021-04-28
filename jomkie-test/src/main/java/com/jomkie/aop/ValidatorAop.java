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

    @Autowired
    private Validator validator;

    // execution: For matching method execution join points. This is the primary pointcut designator to use when working with Spring AOP.
    // within: Limits matching to join points within certain types (the execution of a method declared within a matching type when using Spring AOP).
    // this: Limits matching to join points (the execution of methods when using Spring AOP) where the bean reference (Spring AOP proxy) is an instance of the given type.
    // target: Limits matching to join points (the execution of methods when using Spring AOP) where the target object (application object being proxied) is an instance of the given type.
    // args: Limits matching to join points (the execution of methods when using Spring AOP) where the arguments are instances of the given types.
    // @target: Limits matching to join points (the execution of methods when using Spring AOP) where the class of the executing object has an annotation of the given type.
    // @args: Limits matching to join points (the execution of methods when using Spring AOP) where the runtime type of the actual arguments passed have annotations of the given types.
    // @within: Limits matching to join points within types that have the given annotation (the execution of methods declared in types with the given annotation when using Spring AOP).
    // @annotation: Limits matching to join points where the subject of the join point (the method being run in Spring AOP) has the given annotation.

    // Before advice: Advice that runs before a join point but that does not have the ability to prevent execution flow proceeding to the join point (unless it throws an exception).
    // After returning advice: Advice to be run after a join point completes normally (for example, if a method returns without throwing an exception).
    // After throwing advice: Advice to be run if a method exits by throwing an exception.
    // After (finally) advice: Advice to be run regardless of the means by which a join point exits (normal or exceptional return).
    // Around advice: Advice that surrounds a join point such as a method invocation. This is the most powerful kind of advice. Around advice can perform custom behavior before and after the method invocation. It is also responsible for choosing whether to proceed to the join point or to shortcut the advised method execution by returning its own return value or throwing an exception.

    // execution("修饰符? 返回值 类路径名？方法名(参数) 异常?")
        // 修饰符，类路径名，异常 都是可选的，其它是必填的
        // 类路径名： * 通配符表示 | .. 表示一个或多个子路径
        // 方法名也： * 通配符表示部分或全部
        // 参数：(*) 占位一个参数 | (..) 表示参数零个或多个 | (String,..) 表示第一个参数是String，而之后可能有参数也可能没有
    // @args 方法的参数必须有指定的注解，这是的注解并非是方法声明时的形参上的注解，而是参数类声明时类上必须有指定注解
        // @args("xxx.xxx.xxx.MyAnnotation")
        //                           @MyAnnotation
        // public void test(A a)  -> public class A {
        // Note that the pointcut given in this example is different from execution(* *(java.io.Serializable)).
            // The args version matches if the argument passed at runtime is Serializable,
            // and the execution version matches if the method signature declares a single parameter of type Serializable.
            // 注意 execution(Result methodName(java.io.Serializable) 表示传入的参数确确实实是 Serializable 实体
            // 注意 args(java.io.Serializable) 表示传入的参数是 Serializable 的实现类

    // The difference between @target and @within: https://blog.csdn.net/demon7552003/article/details/97601209

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void webPointCut() { }
    @Pointcut("@args(com.jomkie.aop.valid.NeedValidating)")
    public void validatPointCut() { }

    @Around("webPointCut() && validatPointCut()")
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

}
