package com.lemonwind.test.run;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 测试时间的时区转换问题
 */
public class TimeZoneTest {

    public static void main(String[] args) throws ParseException {
        test00();
    }

    public static void test00() throws ParseException {
        // 设置JVM的默认时区
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
//        TimeZone.setDefault(TimeZone.getTimeZone("PST"));

        // 构建时间对象（相当于构建时间戳）
        Date bjDate = new Date();
        final String patternStr = "yyyy-MM-dd HH:mm:ss";

        // 得到各个时区
        TimeZone beijingTimeZone = TimeZone.getTimeZone("GMT+8");
        TimeZone newYorkTimeZone = TimeZone.getTimeZone("America/New_York");
        TimeZone pstTimeZone = TimeZone.getTimeZone("PST");

        // 将时间转换器设置各自的时区
        DateFormat beijingDateFormat = new SimpleDateFormat(patternStr);
        beijingDateFormat.setTimeZone(beijingTimeZone);
        DateFormat newYorkDateFormat = new SimpleDateFormat(patternStr);
        newYorkDateFormat.setTimeZone(newYorkTimeZone);
        DateFormat pstDateFormat = new SimpleDateFormat(patternStr);
        pstDateFormat.setTimeZone(pstTimeZone);

        // 格式化是将时间戳时间格式化为当前时区的时间
        String beijingString = beijingDateFormat.format(bjDate);
        String newYorkString = newYorkDateFormat.format(bjDate);
        String pstString = pstDateFormat.format(bjDate);
        System.out.println("---->北京时间：" + beijingString);
        System.out.println("---->纽约时间：" + newYorkString);
        System.out.println("---->pst时间：" + pstString);

        // 转换是将 相应时区的时间字面量 转为 当前时区的时间
        System.out.println("*****>北京字符串转时间：" + beijingDateFormat.parse(beijingString));
        System.out.println("*****>纽约字符串转时间：" + newYorkDateFormat.parse(newYorkString));
        System.out.println("*****>pst字符串转时间：" + pstDateFormat.parse(pstString));
    }


}
