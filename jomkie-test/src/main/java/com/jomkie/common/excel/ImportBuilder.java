package com.jomkie.common.excel;

import com.jomkie.model.TestUser;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * 导入构造器
 * @author Jomkie
 * @since 2021-06-16 9:30:25
 */
public class ImportBuilder  extends ExcelBuilder<InputStream, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>, List<TestUser>> {

    @Override
    public List<TestUser> preHandle(InputStream inputStream) {
        // 这里生成 List<TestUser>
        return new LinkedList<>();
    }

    @Override
    public List<TestUser> handle(List<TestUser> param) {
        // 这里检查
        return param;
    }

    @Override
    public List<TestUser> postHandle(List<TestUser> result) {
        // 这里作后置处理
        return result;
    }

}
