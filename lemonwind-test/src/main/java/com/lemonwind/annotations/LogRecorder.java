package com.lemonwind.annotations;

import java.lang.annotation.*;

/**
 * @author Jomkie
 * @since 2021-05-08 11:45:29
 * 测试一个接口多实现拦截的专用注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecorder {

}
