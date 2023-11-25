package com.lemonwind.test.aop;


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
