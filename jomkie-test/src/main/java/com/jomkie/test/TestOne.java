package com.jomkie.test;

import com.jomkie.model.JoUser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestOne {

    public static void main(String[] args) {

        List<JoUser> list = IntStream.range(0, 21)
                .mapToObj(i ->
                    new JoUser(Long.valueOf(i), "李寻欢" + i, i % 5, "明朝" + i)
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

}
