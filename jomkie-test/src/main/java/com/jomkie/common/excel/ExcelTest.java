package com.jomkie.common.excel;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExcelTest {

    public static void main(String[] args) throws FileNotFoundException {
        String XLS_PATH = "~/mydata/workbook.xls";
        String XLSX_PATH = "~/mydata/workbook.xlsx";
        Workbook workbook = ExcelFactory.createSXSSFWorkbook();
        OutputStream outputStream = new FileOutputStream(XLSX_PATH);


        ExcelFactory.write(workbook, outputStream);
    }

}
