package com.lemonwind.common.excel;

import com.lemonwind.common.excel.common.ExcelBuilder;
import com.lemonwind.common.excel.common.ExcelFactory;
import com.lemonwind.datastructure.model.TestUser;
import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 导出建造器
 * @author Jomkie
 * @since 2021-06-11 9:44:12
 */
public class ExportTestUserBuilder extends ExcelBuilder<List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>> {

    @Override
    public List<TestUser> preHandle(List<TestUser> param) {
        return param;
    }

    @Override
    public List<TestUser> handle(List<TestUser> param) {
        return param;
    }

    @Override
    public List<TestUser> postHandle(List<TestUser> result) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateStr = dateTimeFormatter.format(localDateTime);
        String finalFileName = "Excel-test-" + dateStr;
        String XLS_PATH = "/home/jomkie/mydata/" + finalFileName + ".xls";
        String XLSX_PATH = "/home/jomkie/mydata/" + finalFileName + ".xlsx";
        Workbook workbook = ExcelFactory.createSXSSFWorkbook();

        /* ---------------------- 处理逻辑 ---------------------- */

        Sheet sheet = workbook.createSheet("Jomkie测试sheet");
        CreationHelper createHelper = workbook.getCreationHelper();

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        IntStream.range(0, result.size()).forEach(index -> {
            TestUser bean = result.get(index);
            Row row = sheet.createRow(index);
            Cell cell0 = row.createCell(0); // ID Long
            Cell cell1 = row.createCell(1); // name String
            Cell cell2 = row.createCell(2); // age Integer
            Cell cell3 = row.createCell(3); // email String
            Cell cell4 = row.createCell(4); // birthday Date
            cell0.setCellValue(bean.getId());
            cell1.setCellValue(bean.getName());
            cell2.setCellValue(bean.getAge());
            cell3.setCellValue(bean.getEmail());
            cell4.setCellStyle(cellStyle);
            cell4.setCellValue(bean.getBirthday());
        });

        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(XLSX_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("构建输出流失败");
            return null;
        }

        ExcelFactory.write(workbook, outputStream);
        return result;
    }

}
