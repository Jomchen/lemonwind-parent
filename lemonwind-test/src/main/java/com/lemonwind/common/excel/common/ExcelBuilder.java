package com.lemonwind.common.excel.common;

/**
 * Excel处理
 * @author Jomkie
 * @since 2021-06-11 9:38:16
 */
public abstract class ExcelBuilder<S, T, U, V, W, X> {

    /**
     * 预前处理
     * @author Jomkie
     * @since 2021-06-11 9:39:40
     */
    public abstract T preHandle(S param);

    /**
     * 中段处理
     * @author Jomkie
     * @since 2021-06-11 9:39:56
     */
    public abstract V handle(U param);

    /**
     * 后置处理
     * @author Jomkie
     * @since 2021-06-11 9:40:3
     */
    public abstract  X postHandle(W result);

}
