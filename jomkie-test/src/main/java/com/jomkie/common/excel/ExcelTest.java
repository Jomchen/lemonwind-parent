package com.jomkie.common.excel;

import com.jomkie.model.TestUser;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExcelTest {

    public static void main(String[] args) {
        List<TestUser> testUsers = IntStream.rangeClosed(0, 100).mapToObj(i ->
            new TestUser((long)i, "李寻欢" + i, i, (100 + i) + "mail@qq.com", new Date())
        ).collect(Collectors.toList());
        ImportTestUserHandler importTestUserHandler = new ImportTestUserHandler(new ImportBuilder());
        importTestUserHandler.createTestUserList(testUsers);
    }

}
