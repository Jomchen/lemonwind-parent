package com.jomkie.annotations;

import com.jomkie.common.validator.CheckDateTimeValidator;
import lombok.Getter;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

/**
 * @author Jomkie
 * @since 2021-04-26 10:14:17
 * 用于验证日期或时间的注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {CheckDateTimeValidator.class})
public @interface DateTimeValid {

    String message() default "格式有误";

    Format value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    @Getter
    enum Format {
        DATE_TIME_FORMAT("yyyy-MM-dd HH:mm:ss", CheckDateTimeValidator.DATE_TIME_FORMAT),
        DATE_FORMAT("yyyy-MM-dd", CheckDateTimeValidator.DATE_FORMAT),
        TIME_FORMAT("HH:mm:ss", CheckDateTimeValidator.TIME_FORMAT)
        ;

        private String formatStr;
        private String regx;

        Format(String formatStr, String regx) {
            this.formatStr = formatStr;
            this.regx = regx;
        }

    }

}
