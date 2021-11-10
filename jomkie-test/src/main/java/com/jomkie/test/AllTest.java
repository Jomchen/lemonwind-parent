package com.jomkie.test;

import com.jomkie.common.entity.bean.JoUser;

import java.util.*;

import static java.util.stream.Collectors.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AllTest {

    public static void main(String[] args) {
        Optional<Integer> sumData = IntStream.range(0, 10).boxed().reduce(Integer::sum);
        Integer sumData2 = IntStream.range(0, 10).boxed().reduce(0, Integer::sum);
        Optional<Integer> maxData = IntStream.range(0, 10).boxed().reduce(Integer::max);
        Integer totalAge = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(Collectors.reducing(0, JoUser::getAge, Integer::sum));

        // 年龄分组，取得组中ID最大的对象
        Map<Integer, Optional<JoUser>> maxIdMap = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(groupingBy(JoUser::getAge, maxBy(Comparator.comparing(JoUser::getId))));

        // 年龄分组，取得组中ID最大的对象
        Map<Integer, JoUser> maxIdMap2 = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(
                        groupingBy(
                                JoUser::getAge,
                                collectingAndThen(maxBy(Comparator.comparing(JoUser::getId)), Optional::get)
                        )
                );

        // 年龄分组，结果映射为ID并收集为集合
        Map<Integer, List<Integer>> sumIdMap = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(
                        groupingBy(JoUser::getAge, mapping(user -> user.getId(), toList()))
                );

        // 分区，分区组的 KEY 是布尔型
        Map<Boolean, List<JoUser>> partitionMap = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(partitioningBy(user -> user.getId() > 5, toList()));
        
    }

}
