package com.jomkie.common.validator;

import com.jomkie.annotations.DateTimeValid;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author Jomkie
 * @since 2021-04-26 10:21:14
 * 时间日期验证器
 */
@Slf4j
public class CheckDateTimeValidator implements ConstraintValidator<DateTimeValid, String> {

    final public static String SEPARATOR = " ";
    final public static String DATE_FORMAT = "[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
    final public static String TIME_FORMAT = "(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d";
    final public static String DATE_TIME_FORMAT = DATE_FORMAT + SEPARATOR + TIME_FORMAT;

    private DateTimeValid.Format format;

    @Override
    public void initialize(DateTimeValid constraintAnnotation) {
        this.format = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) { return true; }

        String formateStr = format.getFormatStr();
        SimpleDateFormat sdf = new SimpleDateFormat(formateStr);
        try {
            sdf.parse(value);
        } catch (ParseException e) {
            log.error("parse date or time failed.", e);
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
     * @author Jomkie
     * @since 2021-04-26 11:0:35
     * @param formatType 日期时间枚举
     * @param value 传参
     * @return 错误信息
     * 验证 大小月，二月，平年/闰年 的天数合法性
     */
    private String checkMonthAndDay(DateTimeValid.Format formatType, String value) {
        if (null == value || value.equals("")) {
            return null;
        }

        if (formatType.equals(DateTimeValid.Format.DATE_TIME_FORMAT) || formatType.equals(DateTimeValid.Format.DATE_FORMAT)) {
            // 截取日期
            String[] dateColumns = null;
            if (formatType.equals(DateTimeValid.Format.DATE_TIME_FORMAT)) {
                dateColumns = value.split(SEPARATOR)[0].split("-");
            } else if (formatType.equals(DateTimeValid.Format.DATE_FORMAT)) {
                dateColumns = value.split("-");
            }

            // 获取参数年/月/日
            Integer year = Integer.valueOf(dateColumns[0]);
            Integer month = Integer.valueOf(dateColumns[1]);
            Integer dayOfMonth = Integer.valueOf(dateColumns[2]);

            // 声明 二 月，小月
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
            } else {
                // 平年
                if (dayOfMonth == 29) {
                    log.warn("日期平年天数有误，original data is {}", value);
                    return "日期平年天数有误";
                }
            }
        }

        return null;
    }

}
