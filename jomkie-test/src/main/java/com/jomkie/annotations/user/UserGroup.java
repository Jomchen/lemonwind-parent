package com.jomkie.annotations.user;

/**
 * @author Jomkie
 * @since 2021-04-4 19:17
 * 用户验证组
 */
public interface UserGroup {

    /** 增加验证组 */
    interface UserAdd {}

    /** 更新验证组 */
    interface UserUpdate {}

    /** 删除验证组 */
    interface UserDel {}

}
