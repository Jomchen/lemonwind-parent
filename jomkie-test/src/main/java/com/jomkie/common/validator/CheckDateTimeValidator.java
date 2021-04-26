package com.jomkie.common.validator;

import com.jomkie.annotations.DateTimeValid;
import com.jomkie.common.LemonException;
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

    DateTimeValid.Format format;

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
            log.warn("date or time parse failed.", e);
            return false;
        }

        // 验证 大小月，闰年/平年 合法性
        return checkMonthAndDay(format, value);
    }


    /**
     * @author Jomkie
     * @since 2021-04-26 11:0:35
     * @param formatType
     * @param value
     * @return 验证 月 | 日 合法性
     */
    private Boolean checkMonthAndDay(DateTimeValid.Format formatType, String value) {
        if (null == value || value.equals("")) {
            return true;
        }
        if (! formatType.equals(DateTimeValid.Format.DATE_TIME_FORMAT) || ! formatType.equals(DateTimeValid.Format.DATE_FORMAT)) {
            return true;
        }

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
        Boolean isSmallMonth = Arrays.stream(smallMonth).filter(bm -> bm.equals(month)).findAny().isPresent();
        if (month.equals(february) && dayOfMonth >= 30) {
            return false;
        }
        if (isSmallMonth && dayOfMonth >= 31) {
            return false;
        }

        // 验证 闰年 和 平年
        if( ( year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            // 闰年
            return true;
        } else {
            // 平年
            if (dayOfMonth == 29) {
                log.warn("平年日期错误 {}", value);
                return false;
            }
        }

        return true;
    }

}
