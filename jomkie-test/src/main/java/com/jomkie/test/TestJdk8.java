package com.jomkie.test;

import com.jomkie.datastructure.model.JoUser;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class TestJdk8 {

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
        Optional<Integer> sumData = IntStream.range(0, 10).boxed().reduce(Integer::sum);
        Integer sumData2 = IntStream.range(0, 10).boxed().reduce(0, Integer::sum);
        Optional<Integer> maxData = IntStream.range(0, 10).boxed().reduce(Integer::max);
        Integer totalAge = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new com.jomkie.common.entity.bean.JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(Collectors.reducing(0, com.jomkie.common.entity.bean.JoUser::getAge, Integer::sum));

        // 年龄分组，取得组中ID最大的对象
        Map<Integer, Optional<com.jomkie.common.entity.bean.JoUser>> maxIdMap = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new com.jomkie.common.entity.bean.JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(groupingBy(com.jomkie.common.entity.bean.JoUser::getAge, maxBy(Comparator.comparing(com.jomkie.common.entity.bean.JoUser::getId))));

        // 年龄分组，取得组中ID最大的对象
        Map<Integer, com.jomkie.common.entity.bean.JoUser> maxIdMap2 = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new com.jomkie.common.entity.bean.JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(
                        groupingBy(
                                com.jomkie.common.entity.bean.JoUser::getAge,
                                collectingAndThen(maxBy(Comparator.comparing(com.jomkie.common.entity.bean.JoUser::getId)), Optional::get)
                        )
                );

        // 年龄分组，结果映射为ID并收集为集合
        Map<Integer, List<Integer>> sumIdMap = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new com.jomkie.common.entity.bean.JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(
                        groupingBy(com.jomkie.common.entity.bean.JoUser::getAge, mapping(user -> user.getId(), toList()))
                );

        // 分区，分区组的 KEY 是布尔型
        Map<Boolean, List<com.jomkie.common.entity.bean.JoUser>> partitionMap = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new com.jomkie.common.entity.bean.JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(partitioningBy(user -> user.getId() > 5, toList()));

    }

    public static void test02() {
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
