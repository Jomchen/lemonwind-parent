package com.jomkie.common.excel;

/**
 * 导入构造d
 * @author Jomkie
 * @since 2021-06-11 9:38:16
 */
public abstract class ExcelBuilder<T, R, F> {

    /**
     * 预前检验
     * @author Jomkie
     * @since 2021-06-11 9:39:40
     */
    public abstract void preVerify(T param);

    /**
     * 逻辑处理
     * @author Jomkie
     * @since 2021-06-11 9:39:56
     */
    public abstract R handle(T param);

    /**
     * 后置处理
     * @author Jomkie
     * @since 2021-06-11 9:40:3
     */
    public abstract  F postHandle(R result);

}
