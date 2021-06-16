package com.jomkie.common.excel;

import com.jomkie.model.TestUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private ExcelBuilder<InputStream, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>> excelBuilder;

    public void importTestUserList(InputStream inputStream) {
        List<TestUser> preParam = excelBuilder.preHandle(inputStream);
        List<TestUser> list = excelBuilder.handle(preParam);
        excelBuilder.postHandle(list);
    }

}
