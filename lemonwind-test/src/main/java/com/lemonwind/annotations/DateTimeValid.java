package com.lemonwind.annotations;

import com.lemonwind.common.validator.CheckDateTimeValidator;
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

    String message() default "日期格式有误";

    Format value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Getter
    enum Format {
        TIME_FORMAT("HH:mm:ss", CheckDateTimeValidator.TIME_PATTERN) {
            @Override
            public Integer getYear(String value) { return null; }
            @Override
            public Integer getMonth(String value) { return null; }
            @Override
            public Integer getDayOfMonth(String value) { return null; }
        },

        TIME_NO_SEPARATOR_FORMAT("HHmmss", CheckDateTimeValidator.TIME_NO_SEPARATOR_PATTERN) {
            @Override
            public Integer getYear(String value) { return null; }
            @Override
            public Integer getMonth(String value) { return null; }
            @Override
            public Integer getDayOfMonth(String value) { return null; }
        },

        DATE_FORMAT("yyyy-MM-dd", CheckDateTimeValidator.DATE_PATTERN) {
            @Override
            public Integer getYear(String value) {
                return Integer.valueOf(value.split("-")[0]);
            }
            @Override
            public Integer getMonth(String value) {
                return Integer.valueOf(value.split("-")[1]);
            }
            @Override
            public Integer getDayOfMonth(String value) {
                return Integer.valueOf(value.split("-")[2]);
            }
        },

        DATE_NO_SEPARATOR_FORMAT("yyyyMMdd", CheckDateTimeValidator.DATE_NO_SEPARATOR_PATTERN) {
            @Override
            public Integer getYear(String value) {
                return Integer.valueOf(value.substring(0, 4));
            }
            @Override
            public Integer getMonth(String value) {
                return Integer.valueOf(value.substring(4, 6));
            }
            @Override
            public Integer getDayOfMonth(String value) {
                return Integer.valueOf(value.substring(6, 8));
            }
        },

        DATE_TIME_FORMAT("yyyy-MM-dd HH:mm:ss", CheckDateTimeValidator.DATE_TIME_PATTERN) {
            @Override
            public Integer getYear(String value) {
                return Integer.valueOf(value.split(" ")[0].split("-")[0]);
            }
            @Override
            public Integer getMonth(String value) {
                return Integer.valueOf(value.split(" ")[0].split("-")[1]);
            }
            @Override
            public Integer getDayOfMonth(String value) {
                return Integer.valueOf(value.split(" ")[0].split("-")[2]);
            }
        },

        DATE_TIME_NO_SEPARATOR_FORMAT("yyyyMMddHHmmss", CheckDateTimeValidator.DATE_TIME_NO_SEPARATOR_PATTERN) {
            @Override
            public Integer getYear(String value) {
                return Integer.valueOf(value.substring(0, 4));
            }
            @Override
            public Integer getMonth(String value) {
                return Integer.valueOf(value.substring(4, 6));
            }
            @Override
            public Integer getDayOfMonth(String value) {
                return Integer.valueOf(value.substring(6, 8));
            }
        }

        ;

        /** 转换日期的格式 */
        private String formatStr;

        /** 与格式匹配的正则表达式 */
        private String regx;

        Format(String formatStr, String regx) {
            this.formatStr = formatStr;
            this.regx = regx;
        }

        /**
         * @author Jomkie
         * @since 2021-04-26 16:15:53
         * @param value 预转化的字符串
         * 获取年
         */
        public abstract Integer getYear(String value);

        /**
         * @author Jomkie
         * @since 2021-04-26 16:16:26
         * @param value 预转化的字符串
         * 获取月
         */
        public abstract Integer getMonth(String value);

        /**
         * @author Jomkie
         * @since 2021-04-26 16:16:26
         * @param value 预转化的字符串
         * 获取天数
         */
        public abstract Integer getDayOfMonth(String value);
    }

}
