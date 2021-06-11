package com.jomkie.common.excel;

import com.jomkie.model.TestUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 导入用户信息
 * @author Jomkie
 * @since 2021-06-11 9:49:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportTestUserHandler {

    private ExcelBuilder<List<TestUser>, List<TestUser>, List<TestUser>> excelBuilder;

    /**
     * 批量创建用户信息
     * @author Jomkie
     * @since 2021-06-11 9:50:38
     * @param testUsers
     */
    public void createTestUserList(List<TestUser> testUsers) {
        excelBuilder.preVerify(testUsers);
        List<TestUser> list = excelBuilder.handle(testUsers);
        excelBuilder.postHandle(list);
    }

}
