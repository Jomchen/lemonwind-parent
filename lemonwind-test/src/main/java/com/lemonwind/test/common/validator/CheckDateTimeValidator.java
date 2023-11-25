package com.lemonwind.test.common.validator;

import com.lemonwind.test.annotations.DateTimeValid;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author lemonwind
 * @since 2021-04-26 10:21:14
 * 时间日期验证器
 */
@Slf4j
public class CheckDateTimeValidator implements ConstraintValidator<DateTimeValid, String> {

    final public static String SEPARATOR = " ";

    /** HH:mm:ss */
    final public static String TIME_PATTERN = "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d";
    /** HHmmss */
    final public static String TIME_NO_SEPARATOR_PATTERN = "(20|21|22|23|[0-1]\\d)[0-5]\\d[0-5]\\d";
    /** yyyy-MM-dd */
    final public static String DATE_PATTERN = "[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
    /** yyyyMMdd */
    final public static String DATE_NO_SEPARATOR_PATTERN = "[1-9]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])";
    /** yyyy-MM-dd HH:mm:ss */
    final public static String DATE_TIME_PATTERN = DATE_PATTERN + SEPARATOR + TIME_PATTERN;
    /** yyyyMMddHHmmss */
    final public static String DATE_TIME_NO_SEPARATOR_PATTERN = DATE_NO_SEPARATOR_PATTERN + TIME_NO_SEPARATOR_PATTERN;

    private DateTimeValid.Format format;

    @Override
    public void initialize(DateTimeValid constraintAnnotation) {
        this.format = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) { return true; }

        // 验证传参和枚举的正则匹配
        String formateStr = format.getFormatStr();
        String regExpress = format.getRegx();
        if ( ! value.matches(regExpress)) {
            log.error("不匹配的日期/时间");
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("不匹配的日期/时间")
                    .addConstraintViolation();
            return false;
        }

        // 验证是否可以正常转日期或时间
        try {
            new SimpleDateFormat(formateStr).parse(value);
        } catch (ParseException e) {
            log.error("无效的日期/时间", e);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("无效的日期/时间")
                    .addConstraintViolation();
            return false;
        }

        // 验证 大小月，闰年/平年 合法性
        String errorMsg = checkMonthAndDay(format, value);
        if (null != errorMsg) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMsg)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    /**
     * @author lemonwind
     * @since 2021-04-26 11:0:35
     * @param formatType 日期时间枚举
     * @param value 传参
     * @return 错误信息
     * 验证 大小月，二月，平年/闰年 的天数合法性
     */
    private String checkMonthAndDay(DateTimeValid.Format formatType, String value) {

        // 获取参数 年/月/日
        Integer year = formatType.getYear(value);
        Integer month = formatType.getMonth(value);
        Integer dayOfMonth = formatType.getDayOfMonth(value);
        if (null == year || null == month || null == dayOfMonth) { return null; }

        // 定义 二 月，小月
        Integer february = 2;
        Integer[] smallMonth = {4, 6, 9, 11};

        // 验证 二月，小月 天数合法性
        Boolean isSmallMonth = Arrays.stream(smallMonth).anyMatch(bm -> bm.equals(month));
        if (isSmallMonth && dayOfMonth >= 31) { return "小月份天数有误"; }
        if (month.equals(february) && dayOfMonth >= 30) { return "二月天数有误"; }

        // 验证 闰年 和 平年
        if( ( year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            // 闰年
            return null;
        }

        // 平年
        if (dayOfMonth == 29) {
            log.warn("日期平年天数有误，original data is {}", value);
            return "日期平年天数有误";
        }

        return null;
    }

}
