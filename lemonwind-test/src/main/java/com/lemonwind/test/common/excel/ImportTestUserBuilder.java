package com.lemonwind.test.common.excel;

import com.lemonwind.test.common.LemonException;
import com.lemonwind.test.common.excel.common.ExcelBuilder;
import com.lemonwind.test.model.TestUser;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 导入构造器
 * @author Jomkie
 * @since 2021-06-16 9:30:25
 */
public class ImportTestUserBuilder extends ExcelBuilder<Workbook, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>> {

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public List<TestUser> preHandle(Workbook workbook) {
        // 这里生成 List<TestUser>
        Sheet sheet = workbook.getSheetAt(0);
        int startRow = sheet.getFirstRowNum();
        int endRow = sheet.getLastRowNum();
        System.out.println("行开始索引：" + startRow);
        System.out.println("行结束索引：" + endRow);

        List<TestUser> testUserList = IntStream.rangeClosed(startRow + 1, endRow).boxed().map(rowIndex -> {
            System.out.println("------------------------------------------------------------------------------");
            // 检索行合法性
            Row row = sheet.getRow(rowIndex);
            if (Objects.isNull(row)) {
                throw new LemonException(generateRowError(rowIndex + 1, "不能为空"));
            }

            // 列合法性
            Cell cell0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell1 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell2 = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell3 = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell4 = row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (null == cell0) {
                throw new LemonException(generateCellError(rowIndex + 1, 1, "不能为空"));
            }
            if (null == cell1) {
                throw new LemonException(generateCellError(rowIndex + 1, 2, "不能为空"));
            }
            if (null == cell2) {
                throw new LemonException(generateCellError(rowIndex + 1, 3, "不能为空"));
            }
            if (null == cell3) {
                throw new LemonException(generateCellError(rowIndex + 1, 4, "不能为空"));
            }
            if (null == cell4) {
                throw new LemonException(generateCellError(rowIndex + 1, 5, "不能为空"));
            }

            // 封装对象
            Long id = Long.parseLong(convertToStr2(cell0));
            String name = convertToStr2(cell1);
            int age = Integer.parseInt(convertToStr2(cell2));
            String email = convertToStr2(cell3);
            Date birthday = strToDate(convertToStr2(cell4));

            TestUser bean = new TestUser(id, name, age, email, birthday);
            return bean;
        }).collect(Collectors.toList());

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("关闭流失败");
        }

        return testUserList;
    }

    @Override
    public List<TestUser> handle(List<TestUser> param) {
        // 中段处理
        System.out.println("中段处理中。。。。");
        param.stream().map(TestUser::toString).forEach(System.out::println);
        return param;
    }

    @Override
    public List<TestUser> postHandle(List<TestUser> result) {
        // 后置处理
        System.out.println("后置处理中。。。。");
        return result;
    }

    private static String convertToStr(Cell cell) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CellType cellType = cell.getCellType();

        switch (cellType) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return sdf.format(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return null;
            default:
                return null;
        }
    }

    private static String convertToStr2(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    private static Date strToDate(String dateStr) {
        Date date = null;
        try {
            date = SDF.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("转换时间失败");
        }
        return date;
    }

    private static String generateRowError(int rowNum, String msg) {
        return new StringBuilder("第").append(rowNum).append("行")
                .append(msg).toString();
    }

    private static String generateCellError(int rowNum, int cellNum, String msg) {
        return new StringBuilder("第").append(rowNum).append("行的第")
                .append(cellNum).append("列").append(msg)
                .toString();
    }

}
