package com.lemonwind.common.excel;

import com.lemonwind.common.excel.common.ExcelBuilder;
import com.lemonwind.common.excel.common.ExcelFactory;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * 测试用户模板构建者
 * @author Jomkie
 * @since 2021-06-18 09:30:46
 */
public class TemplateTestUserBuilder extends ExcelBuilder<List<String>, Workbook, Workbook, Workbook, Workbook, Workbook> {

    @Override
    public Workbook preHandle(List<String> param) {
        Workbook workbook = ExcelFactory.createXSSFWorkbook();
        return workbook;
    }

    @Override
    public Workbook handle(Workbook workbook) {
        Sheet sheet0 = workbook.createSheet("模板说明");

        Row row0 = sheet0.createRow(0);
        row0.createCell(0, CellType.STRING).setCellValue("危险编码");
        row0.createCell(1, CellType.STRING).setCellValue("单词");
        row0.createCell(2, CellType.STRING).setCellValue("意义说明");

        Row row1 = sheet0.createRow(1);
        row1.createCell(0, CellType.STRING).setCellValue("C");
        row1.createCell(1, CellType.STRING).setCellValue("Corrosivity");
        row1.createCell(2, CellType.STRING).setCellValue("腐蚀性");

        Row row2 = sheet0.createRow(2);
        row2.createCell(0, CellType.STRING).setCellValue("T");
        row2.createCell(1, CellType.STRING).setCellValue("Toxicity");
        row2.createCell(2, CellType.STRING).setCellValue("毒性");

        Row row3 = sheet0.createRow(3);
        row3.createCell(0, CellType.STRING).setCellValue("I");
        row3.createCell(1, CellType.STRING).setCellValue("Ignitability");
        row3.createCell(2, CellType.STRING).setCellValue("易燃性");

        Row row4 = sheet0.createRow(4);
        row4.createCell(0, CellType.STRING).setCellValue("R");
        row4.createCell(1, CellType.STRING).setCellValue("Reactivity");
        row4.createCell(2, CellType.STRING).setCellValue("反应性");

        Row row5 = sheet0.createRow(5);
        row5.createCell(0, CellType.STRING).setCellValue("In");
        row5.createCell(1, CellType.STRING).setCellValue("lnfectivity");
        row5.createCell(2, CellType.STRING).setCellValue("感染性");

        Row row6 = sheet0.createRow(6);
        row6.createCell(0, CellType.STRING).setCellValue("O");
        row6.createCell(1, CellType.STRING).setCellValue("Other");
        row6.createCell(2, CellType.STRING).setCellValue("其它");

        /* ------------------------------------------------------------------------------ */

        Sheet sheet1 = workbook.createSheet("数据");
        Row sheet1Row0 = sheet1.createRow(0);
        sheet1Row0.createCell(0, CellType.STRING).setCellValue("品类危废代码");
        sheet1Row0.createCell(1, CellType.STRING).setCellValue("危废类别名称");
        sheet1Row0.createCell(2, CellType.STRING).setCellValue("危废代码");
        sheet1Row0.createCell(3, CellType.STRING).setCellValue("危废特性[多个以英文,分隔]");

        return workbook;
    }

    @Override
    public Workbook postHandle(Workbook workbook) {

        return workbook;
    }

}
