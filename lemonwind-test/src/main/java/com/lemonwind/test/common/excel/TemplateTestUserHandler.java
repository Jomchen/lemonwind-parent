package com.lemonwind.test.common.excel;

import com.lemonwind.test.common.excel.common.ExcelBuilder;
import com.lemonwind.test.common.excel.common.ExcelFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TemplateTestUserHandler {

    private ExcelBuilder<List<String>, Workbook, Workbook, Workbook, Workbook, Workbook> excelBuilder;

    /**
     * @author Jomkie
     * @since 2021-06-18 09:36:17
     * @param list
     * @return 模板
     */
    public void getImportTemplate(List<String> list) {
        Workbook workbook = excelBuilder.preHandle(list);
        Workbook workbook1 = excelBuilder.handle(workbook);
        Workbook workbook2 = excelBuilder.postHandle(workbook1);

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateStr = dateTimeFormatter.format(localDateTime);
        String XLSX_PATH = "/home/jomkie/mydata/" + dateStr + ".xlsx";
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(XLSX_PATH);
        } catch (FileNotFoundException e) {
            log.error("建造输出流失败", e);
            return;
        }

        ExcelFactory.write(workbook2, outputStream);
    }

}
