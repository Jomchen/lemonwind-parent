package com.lemonwind.test.common;

import java.io.Serializable;

/**
 * @author lemonwind
 * @since 2021-04-29 16:13:4
 * 预构建参数 的前置方法
 */
public interface PreBuildParamDto extends Serializable {

    void buildActualParam();

}
