package com.jomkie.common.excel;

import com.jomkie.model.TestUser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExcelTest {

    public static void main(String[] args) {

    }

    /**
     * 测试输出 excel
     * @author Jomkie
     * @since 2021-06-16 9:17:30
     */
    public static void exportTest() {
        ExportTestUserHandler exportTestUserHandler = new ExportTestUserHandler(new ExportBuilder());
        exportTestUserHandler.createTestUserList(
            IntStream.rangeClosed(0, 100).mapToObj(i ->
                new TestUser((long)i, "李寻欢" + i, i, (100 + i) + "mail@qq.com", new Date())
            ).collect(Collectors.toList())
        );
    }

    public static void importTest() {
        try {
            FileInputStream inputStream = new FileInputStream("/home/jomkie/mydata/joUser-excel.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("构建输入流失败");
            return;
        }

    }

}
