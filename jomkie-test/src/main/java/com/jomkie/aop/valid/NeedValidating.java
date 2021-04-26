package com.jomkie.aop.valid;

import java.lang.annotation.*;

/**
 * @author Jomkie
 * @since 2021-04-26 9:58:29
 * 用于需要验证的参数对象上，配合参数aop拦截
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedValidating {

}
