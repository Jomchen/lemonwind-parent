package com.jomkie.test;

import com.jomkie.model.JoUser;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestOne {

    public static void main(String[] args) {
        test01();
    }

    public void test00() {
        List<JoUser> list = IntStream.range(0, 21)
                .mapToObj(i ->
                        new JoUser(Long.valueOf(i), "李寻欢" + i, i % 5, (short)1, "明朝" + i)
                ).collect(Collectors.toList());

        Map<Integer, JoUser> map = list.stream().collect(Collectors.toMap(JoUser::getAge, v -> v, (x, y) -> x));
        Map<Integer, Integer> map2 = list.stream().collect(Collectors.toMap(JoUser::getAge, JoUser::getAge, Integer::sum));
        Map<Integer, JoUser> map3 = list.stream().collect(
                Collectors.groupingBy(
                        JoUser::getAge,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(JoUser::getAge)),
                                Optional::get
                        )
                )
        );

        Map<Integer, Set<String>> map4 = list.stream().collect(
                Collectors.groupingBy(
                        JoUser::getAge,
                        Collectors.mapping(u -> u.getAge() < 10 ? "small" : "big", Collectors.toSet())
                )
        );

        list.stream().collect(
                Collectors.groupingBy(
                        JoUser::getAge,
                        Collectors.mapping(u -> u.getAge() < 10 ? "small" : "big", Collectors.toSet())
                )
        );
    }

    public static void test01() {
        LocalDate.of(2021, 6, 21);
        LocalTime.of(22, 22, 22);

        LocalDate localDate = LocalDate.parse("2021-06-21");
        LocalTime localTime = LocalTime.parse("22:22:22");
        LocalDateTime localDateTime = LocalDateTime.parse("2021-06-21T22:22:22");
        //int year = localDate.get(ChronoField.YEAR);
        localDateTime.toLocalDate();
        localDateTime.toLocalTime();
        LocalDateTime ldt = localTime.atDate(localDate);
        LocalDateTime ldt2 = localDate.atTime(localTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime ldt3 = LocalDateTime.parse("20210101222222", dtf);


        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        ZonedDateTime zonedDateTime = ldt3.atZone(zoneId);

        System.out.println(zonedDateTime.format(dtf));
        System.out.println(ldt3.format(dtf));
        LocalDateTime lll = LocalDateTime.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(lll.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
