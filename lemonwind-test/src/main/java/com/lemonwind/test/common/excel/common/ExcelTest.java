package com.lemonwind.test.common.excel.common;

import com.lemonwind.common.excel.*;
import com.lemonwind.test.common.excel.*;
import com.lemonwind.test.model.TestUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExcelTest {

    public static void main(String[] args) {
        // 测试
        testTemplate();
    }

    /**
     * 测试输出 excel
     * @author Jomkie
     * @since 2021-06-16 9:17:30
     */
    public static void exportTest() {
        ExportTestUserHandler exportTestUserHandler = new ExportTestUserHandler(new ExportTestUserBuilder());
        exportTestUserHandler.createTestUserList(
            IntStream.rangeClosed(0, 100).mapToObj(i ->
                new TestUser((long)i, "李寻欢" + i, i, (100 + i) + "mail@qq.com", new Date())
            ).collect(Collectors.toList())
        );
    }

    /**
     * 测试输入 excel
     * @author Jomkie
     * @since 2021-06-16 9:54:39
     */
    public static void importTest() {
        String FILE_PATH = "/home/jomkie/mydata/joUser-excel.xlsx";
        int index = FILE_PATH.lastIndexOf(".");
        String suffix = FILE_PATH.substring(index + 1);
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("构建输入流失败");
            return;
        }

        ImportTestUserHandler importTestUserHandler = new ImportTestUserHandler(new ImportTestUserBuilder());
        importTestUserHandler.importTestUserList(suffix, inputStream);
    }

    public static void testTemplate() {
        TemplateTestUserHandler templateTestUserHandler = new TemplateTestUserHandler(new TemplateTestUserBuilder());
        templateTestUserHandler.getImportTemplate(null);
    }

}
