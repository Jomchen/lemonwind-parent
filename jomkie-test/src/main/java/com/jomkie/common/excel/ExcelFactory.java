package com.jomkie.common.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Jomkie
 * @since 2021-06-08 14:43:12
 * Excel 工厂
 */
public class ExcelFactory {

    private static Logger log = LoggerFactory.getLogger(ExcelFactory.class);

    /* Start --------------------------------
    Note that sheet name is Excel must not exceed 31 characters
    and must not contain any of the any of the following characters:
    0x0000
    0x0003
    colon (:)
    backslash (\)
    asterisk (*)
    question mark (?)
    forward slash (/)
    opening square bracket ([)
    closing square bracket (])
    -------------------------------- End */

    public static Workbook createHSSFWorkbook() {
        /* xls */
        return new HSSFWorkbook();
    }

    public static Workbook createXSSFWorkbook() {
        /* xlsx */
        return new XSSFWorkbook();
    }

    public static Workbook createSXSSFWorkbook() {
        /* xlsx */
        return new SXSSFWorkbook();
    }

    /**
     * 将工作薄输出到流中
     * @author Jomkie
     * @since 2021-06-08 14:48:4
     * @param workbook 工作薄
     * @param outputStream 输出流
     */
    public static void write(Workbook workbook, OutputStream outputStream) {
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("流写入失败", e);
        }

        try {
            outputStream.flush();
        } catch (IOException e) {
            log.error("流刷新失败", e);
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            log.error("关闭流失败", e);
        }
    }

}
