package com.lemonwind.common.excel;

import com.lemonwind.common.excel.common.ExcelBuilder;
import com.lemonwind.common.excel.common.ExcelFactory;
import com.lemonwind.datastructure.model.TestUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.List;

/**
 * 导入用户测试
 * @author Jomkie
 * @since 2021-06-16 9:18:7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportTestUserHandler {

    private ExcelBuilder<Workbook, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>> excelBuilder;

    /**
     * 导入测试用户
     * @author Jomkie
     * @since 2021-06-16 9:54:14
     * @param suffix 文件后缀
     * @param inputStream 输入流
     */
    public void importTestUserList(String suffix, InputStream inputStream) {
        Workbook workbook = ExcelFactory.createWorkbookBySuffix(suffix, inputStream);
        List<TestUser> preParam = excelBuilder.preHandle(workbook);
        List<TestUser> list = excelBuilder.handle(preParam);
        excelBuilder.postHandle(list);
    }

}
