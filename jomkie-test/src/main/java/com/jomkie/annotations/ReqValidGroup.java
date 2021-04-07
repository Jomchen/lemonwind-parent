package com.jomkie.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jomkie
 * @date 2021-04-3 0:28
 * 声明需要验证的组的注解
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqValidGroup {

    /** the groups to validate */
    Class<?>[] value() default {};

}
