package com.lemonwind.test.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lemonwind
 * @since 2021-04-3 0:28
 * 声明需要验证的组的注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqValidGroup {

    /** the groups to validate */
    Class<?>[] value() default {};

    /** whether acquire one error message for your violated messages, default is true */
    boolean onlyOneError() default true;

}
